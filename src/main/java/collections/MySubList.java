package collections;

import java.util.*;

/**
 * Implementation of the sub list over the List iterator.
 */
public class MySubList<E> implements List<E> {
    private List<E> root;
    private int offset;
    private int size;

    /**
     * Constructor.
     *
     * @param list      The root list which will be operated on.
     * @param fromIndex The start index of sub list in the root one.
     * @param toIndex   The end index of sublist in the root one.
     */
    public MySubList(List<E> list, int fromIndex, int toIndex) {
        this.root = list;
        this.offset = fromIndex;
        this.size = toIndex - fromIndex;
    }

    /**
     * Returns the size of the sublist.
     *
     * @return sub list size.
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the list is empty.
     *
     * @return {@code true} when there are no elements in the list.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks whether given element exists in the list.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} when given element is in the list.
     */
    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    /**
     * Checks whether all elements of the given collection are in the list.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} when every element of the given collection is in the list.
     */
    public boolean containsAll(Collection c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the iterator over the list.
     *
     * @return {@code Iterator}
     */
    public Iterator iterator() {
        return new MyIterator(toArray());
    }

    /**
     * Returns the array of elements in the list.
     *
     * @return
     */
    public Object[] toArray() {
        return Arrays.copyOfRange(root.toArray(), offset, offset + size);
    }

    /**
     * Returns given array with current list's elements.
     *
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return The list's elements of given array type.
     */
    public <T> T[] toArray(T[] a) {
        Object[] elements = toArray();
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, offset, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * Appends a new element into the list.
     * <p>
     * The element will be added to the end of the list.
     *
     * @param o element whose presence in this collection is to be ensured
     * @return {@code true}
     */
    public boolean add(E o) {
        root.add(offset + size, o);
        increaseSize();
        return true;
    }

    /**
     * Appends an element into the list with given index.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException When index is out of the list.
     */
    public void add(int index, E element) {
        Objects.checkIndex(index, size);
        root.add(offset + index, element);
        increaseSize();
    }

    /**
     * Appends a collection into the list.
     * <p>
     * The collection will be added to the end of the list.
     *
     * @param c collection containing elements to be added to this collection
     * @return {@code true} when the collection added successfully.
     */
    public boolean addAll(Collection c) {
        boolean isElementsAdded = root.addAll(size + offset, c);
        increaseSize(c.size());
        return isElementsAdded;
    }

    /**
     * Appends a collection into the list by given index.
     * <p>
     * The collection will be added starting given index.
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return {@code true} when collection is successfully added.
     * @throws IndexOutOfBoundsException When index is out of the list.
     */
    public boolean addAll(int index, Collection c) {
        Objects.checkIndex(index, size);
        boolean isElementsAdded = root.addAll(index + offset, c);
        increaseSize(c.size());
        return isElementsAdded;
    }

    /**
     * Removes element of collection which is equal to given one.
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} when corresponding object is removed out of the list.
     */
    public boolean remove(Object o) {
        int indexToRemove = indexOf(o);
        boolean hasElement = indexToRemove > -1;
        if (hasElement) {
            remove(indexToRemove);
        }
        return hasElement;
    }

    /**
     * Removes en element of the list by given index.
     *
     * @param index the index of the element to be removed
     * @return {@code Object} the element that has been removed.
     * @throws IndexOutOfBoundsException When index is out of the list.
     */
    public E remove(int index) {
        Objects.checkIndex(index, size);
        E oldValue = root.remove(offset + index);
        decreaseSize();
        return oldValue;
    }

    /**
     * Removes the list elements which are in the given collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} when there was removed at least one element.
     */
    public boolean removeAll(Collection c) {
        boolean results = false;
        for (Object o : c) {
            results = remove(o) || results;
        }
        return results;
    }

    /**
     * Remove the list elements that are not presented in the given collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} when there was deleted at least one element.
     */
    public boolean retainAll(Collection c) {
        boolean results = false;
        Object[] localElements = toArray();
        for (int i = 0; i < localElements.length; i++) {
            if (!c.contains(localElements[i])) {
                results = remove(localElements[i]) || results;
            }
        }
        return results;
    }

    /**
     * Remove the list elements.
     * <p>
     * Makes the list empty.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            set(i, null);
        }
        size = 0;
    }

    /**
     * Retrieve the list element by given index.
     *
     * @param index index of the element to return
     * @return {@code Object} An element found by given index.
     * @throws IndexOutOfBoundsException when index is out of the list.
     */
    public E get(int index) {
        Objects.checkIndex(index, size);
        return root.get(index + offset);
    }

    /**
     * Replaces the list element with given one by given index.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return {@code Object} previous element.
     * @throws IndexOutOfBoundsException When index is out of the list.
     */
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        return root.set(index + offset, element);
    }

    /**
     * Returns an index of the list's element which equals given object.
     * <p>
     * Search starting the beginning of the list.
     *
     * @param o element to search for
     * @return {@code -1} when given object was not found in the list.
     */
    public int indexOf(Object o) {
        if (!isEmpty()) {
            Object[] elements = root.toArray();
            for (int i = offset; i < offset + size; i++) {
                if (o.equals(elements[i])) {
                    return i - offset;
                }
            }
        }
        return -1;
    }

    /**
     * Returns an index of the list's element which equals given object.
     * <p>
     * Search starting the end of the list.
     *
     * @param o element to search for
     * @return {@code -1} when given object was not found in the list.
     */
    public int lastIndexOf(Object o) {
        if (!isEmpty()) {
            Object[] elements = root.toArray();
            for (int i = offset + size - 1; i >= offset; i--) {
                if (o.equals(elements[i])) {
                    return i - offset;
                }
            }
        }
        return -1;
    }

    /**
     * Returns a list iterator.
     *
     * @return list iterator.
     */
    public ListIterator<E> listIterator() {
        return new MyListIterator<E>((E[]) toArray());
    }

    /**
     * Returns a list iterator pointed to the element with given index.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return list iterator pointed to corresponding index.
     */
    public ListIterator<E> listIterator(int index) {
        Objects.checkIndex(index, size);
        return new MyListIterator<E>((E[]) toArray(), index);
    }

    /**
     * Returns the sub list with elements in range of given indexes.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return Sub list.
     */
    public List<E> subList(int fromIndex, int toIndex) {
        return new MySubList<E>(this, fromIndex, toIndex);
    }

    /**
     * Increases the size by 1.
     */
    private void increaseSize() {
        size += 1;
    }

    /**
     * Increases the size by given value.
     *
     * @param value The value for increasing the array size.
     */
    private void increaseSize(int value) {
        size += value;
    }

    /**
     * Decreases the size by 1.
     */
    private void decreaseSize() {
        size -= 1;
    }
}
