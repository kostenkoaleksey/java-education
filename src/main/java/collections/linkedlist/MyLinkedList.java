package collections.linkedlist;

import collections.MyIterator;
import collections.MyListIterator;
import collections.MySubList;

import java.lang.reflect.Array;
import java.util.*;

final public class MyLinkedList<E> implements DoubleEndedList<E> {
    private int size;

    private Node<E> first;
    private Node<E> last;

    /**
     * Initializes the MyLinkedList class.
     */
    public MyLinkedList() {
        this.size = 0;
    }

    /**
     * Initializes the MyLinkedList class.
     *
     * @param c Initial collection for initialization the list.
     */
    public MyLinkedList(Collection<E> c) {
        this();
        addAll(c);
    }

    /**
     * Returns the size of the collection.
     *
     * @return Collection's size.
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the list is empty.
     *
     * @return {@code true} when there is not elements in the list.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether the list contains given object.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code -1} when match is not found.
     */
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * Checks whether all elements of the given collection are in the list.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} when every element of the given collection is in the list.
     */
    public boolean containsAll(Collection<?> c) {
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
    public Iterator<E> iterator() {
        return new MyIterator<E>((E[]) toArray());
    }

    /**
     * Returns the array of elements on the list in proper sequence.
     *
     * @return array of the list elements.
     */
    public Object[] toArray() {
        E[] list = (E[]) new Object[size];
        Node<E> currentNode = first;
        for (int i = 0; i < size; i++) {
            list[i] = currentNode.getValue();
            currentNode = currentNode.getNext();
        }
        return list;
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
        E[] elements = (E[]) toArray();
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * Adds new element into the end of the list.
     *
     * @param e element whose presence in this collection is to be ensured
     * @return {@code true} when the list has to be changed.
     */
    public boolean add(E e) {
        Node<E> newNode = new Node<E>(e);

        if (isEmpty()) {
            first = last = newNode;
        }

        Node<E> lastNode = last;

        newNode.setNext(first);
        newNode.setPrev(lastNode);

        lastNode.setNext(newNode);

        last = newNode;
        first.setPrev(last);
        size += 1;
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
        Node<E> currentNode = getNode(index);
        append(currentNode, element);
    }

    /**
     * Appends a collection into the list.
     *
     * @param c collection containing elements to be added to this collection
     * @return {@code true} when the collection added successfully.
     */
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }

        for (E element : c) {
            add(element);
        }

        return true;
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
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }

        Node<E> currentNode = getNode(index);
        for (E element : c) {
            append(currentNode, element);
        }

        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this list.
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} when corresponding object is removed out of the list.
     */
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }

        Node<E> currentNode = first;
        int i = 0;
        while (currentNode != null && size > i) {
            i++;
            if (o.equals(currentNode.getValue())) {
                removeNode(currentNode);
                return true;
            }
            currentNode = currentNode.getNext();
        }

        return false;
    }

    /**
     * Removes en element of the list by given index.
     *
     * @param index the index of the element to be removed
     * @return {@code Object} the element that has been removed.
     * @throws IndexOutOfBoundsException When index is out of the list.
     */
    public E remove(int index) {
        return removeNode(getNode(index));
    }

    /**
     * Removes the list elements which are in the given collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} when there was removed at least one element.
     */
    public boolean removeAll(Collection<?> c) {
        boolean results = false;

        if (c.isEmpty()) {
            return results;
        }

        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            E current = iterator.next();
            if (c.contains(current)) {
                remove(current);
                results = true;
            }
        }

        return results;
    }

    /**
     * Remove the list elements that are not presented in the given collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} when there was deleted at least one element.
     */
    public boolean retainAll(Collection<?> c) {
        boolean results = false;
        E[] localElements = (E[]) toArray();
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
        while (first != null) {
            removeNode(first);
        }
    }

    /**
     * Returns the list element by given index.
     *
     * @param index index of the element to return
     * @return {@code Object} An element found by given index.
     * @throws IndexOutOfBoundsException when index is out of the list.
     */
    public E get(int index) {
        return getNode(index).getValue();
    }

    /**
     * Replaces the list element with given one by given index.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified node
     * @return {@code Object} previous element.
     * @throws IndexOutOfBoundsException When index is out of the list.
     */
    public E set(int index, E element) {
        Node<E> node = getNode(index);
        E oldValue = node.getValue();

        node.setValue(element);
        return oldValue;
    }

    /**
     * Returns the index of element in the list.
     *
     * @param o element to search for
     * @return {@code -1} if the element was not found in the list.
     */
    public int indexOf(Object o) {
        if (!isEmpty()) {
            Node<E> currentNode = first;
            for (int i = 0; i < size; i++) {
                if (o.equals(currentNode.getValue())) {
                    return i;
                }
                currentNode = currentNode.getNext();
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
            Node<E> currentNode = last;
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(currentNode.getValue())) {
                    return i;
                }
                currentNode = currentNode.getPrev();
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
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new MySubList<E>(this, fromIndex, toIndex);
    }

    /**
     * Returns the node element by given index.
     *
     * @param index Index of the element in the list.
     * @return the node of the element.
     */
    private Node<E> getNode(int index) {
        Objects.checkIndex(index, size);

        Node<E> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.getNext();
        }

        return currentNode;
    }

    /**
     * @param node
     * @return
     */
    private E removeNode(Node<E> node) {
        E oldValue = node.getValue();

        if (size == 1) {
            first = last = null;
            size = 0;
            return oldValue;
        }

        Node<E> prevNode = node.getPrev();
        Node<E> nextNode = node.getNext();

        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        if (first.equals(node)) {
            first = nextNode;
        } else if (last.equals(node)) {
            last = prevNode;
        }
        size -= 1;

        return oldValue;
    }

    /**
     * Appends new element into the list after given one.
     *
     * @param node    The node after which new element will be added.
     * @param element The element which should be added after given node.
     * @return the new node.
     */
    private Node<E> append(Node<E> node, E element) {
        Node<E> prevNode = node.getPrev();

        Node<E> newNode = new Node<E>(element);
        newNode.setPrev(prevNode);
        newNode.setNext(node);

        node.setPrev(newNode);
        prevNode.setNext(newNode);
        if (first.equals(node)) {
            first = newNode;
        }
        size += 1;

        return newNode;
    }

    /**
     * Adds new element into the head of the list.
     *
     * @param element The element to insert into the list.
     * @return {@code true} when elements has been added successfully.
     */
    public boolean pushToHead(E element) {
        Node<E> newNode = new Node<>(element);
        newNode.setPrev(last);
        newNode.setNext(first);
        first.setPrev(newNode);
        first = newNode;
        size += 1;
        return true;
    }

    /**
     * Returns the first list's element.
     *
     * @return {@code null} when list is empty.
     */
    public E getFirst() {
        return isEmpty() ? null : first.getValue();
    }

    /**
     * Returns the last list's element.
     *
     * @return {@code null} when list is empty.
     */
    public E getLast() {
        return isEmpty() ? null : last.getValue();
    }
}
