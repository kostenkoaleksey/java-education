package collections;

import java.util.*;


public class MyList implements List {
    private Object[] elements;
    private int size;

    /**
     * Constructor of MyList.
     *
     * @param capacity Initial capacity of the list.
     */
    public MyList(int capacity) {
        this.elements = new Object[capacity];
    }

    /**
     * Constructor of MyList.
     * <p>
     * The list will be created with zero capacity.
     */
    public MyList() {
        this(0);
    }

    /**
     * Retrieves the size of the list.
     *
     * @return
     */
    public int size() {
        return prepareLength(size);
    }

    /**
     * Checks whether the list is empty.
     *
     * @return {@code true} when there are no elements in the list.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether given element exists in the list.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} when given element is in the list.
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
    public boolean containsAll(Collection c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves the iterator over the list.
     *
     * @return {@code Iterator}
     */
    public Iterator iterator() {
        return new MyIterator(elements);
    }

    /**
     * Returns the array of collection's elements.
     *
     * @return
     */
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return
     */
    public Object[] toArray(Object[] a) {
        if (a.length < size) {
            return (Object[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    /**
     * Appends a new element into the list.
     *
     * @param o element whose presence in this collection is to be ensured
     * @return {@code true}
     */
    public boolean add(Object o) {
        if (size == elements.length) {
            increaseListCapacity();
        }
        elements[size] = o;
        size += 1;
        return true;
    }

    @Override
    public void add(int index, Object element) {
        Objects.checkIndex(index, size);
        if (size == elements.length) {
            increaseListCapacity();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size += 1;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c.isEmpty()) {
            return false;
        }

        if (size + c.size() >= elements.length) {
            increaseListCapacity(c.size());
        }
        System.arraycopy(c.toArray(), 0, elements, size, c.size());
        size += c.size();
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        Objects.checkIndex(index, size);
        if (c.isEmpty()) {
            return false;
        }
        if (size + c.size() >= elements.length) {
            increaseListCapacity(c.size());
        }
        int shift = size - index;
        if (shift > 0) {
            System.arraycopy(elements, index,
                    elements, index + c.size(),
                    shift);
        }
        System.arraycopy(c.toArray(), 0, elements, index, c.size());
        size += c.size();
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int indexToRemove = indexOf(o);
        boolean hasElement = indexToRemove > -1;
        if (hasElement) {
            remove(indexToRemove);
        }
        return hasElement;
    }

    @Override
    public Object remove(int index) {
        Object oldValue = elements[Objects.checkIndex(index, size)];
        int newSize = size - 1;
        if (newSize >= index) {
            System.arraycopy(elements, index + 1, elements, index, newSize - index);
            size = newSize;
        }
        elements[size] = null;

        return oldValue;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean results = false;
        for (Object o : c) {
            results = remove(o) || results;
        }
        return results;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean results = false;
        Object[] localElements = toArray();
        for (int i = 0; i < localElements.length; i++) {
            if (localElements[i] == null) {
                continue;
            }
            if (!c.contains(localElements[i])) {
                results = remove(localElements[i]) || results;
            }
        }
        return results;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public Object get(int index) {
        return elements[Objects.checkIndex(index, size)];
    }

    @Override
    public Object set(int index, Object element) {
        Object oldValue = elements[Objects.checkIndex(index, size)];
        elements[index] = element;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (!isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (!isEmpty()) {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator listIterator() {
        return new MyListIterator(elements);
    }

    @Override
    public ListIterator listIterator(int index) {
        return new MyListIterator(elements, index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return new MySubList(this, fromIndex, toIndex);
    }

    /**
     * Increases list capacity  by 1.
     */
    private void increaseListCapacity() {
        elements = Arrays.copyOf(elements, prepareLength(size + 1));
    }

    /**
     * Increases list capacity by given value.
     *
     * @param sizeToIncrease The value to increase the list capacity.
     */
    private void increaseListCapacity(int sizeToIncrease) {
        elements = Arrays.copyOf(elements, prepareLength(size + sizeToIncrease));
    }

    private int prepareLength(int length) {
        return Math.min(length, Integer.MAX_VALUE);
    }
}

