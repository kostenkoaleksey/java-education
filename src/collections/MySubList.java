package collections;

import java.util.*;

public class MySubList implements List {
    private List root;
    private int offset;
    private int size;

    public MySubList(List list, int fromIndex, int toIndex) {
        this.root = list;
        this.offset = fromIndex;
        this.size = toIndex - fromIndex;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator(toArray());
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOfRange(root.toArray(), offset, offset + size);
    }

    @Override
    public Object[] toArray(Object[] a) {
        Object[] elements = toArray();
        if (a.length < size) {
            return (Object[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, offset, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(Object o) {
        root.add(offset + size, o);
        increaseSize();
        return true;
    }

    @Override
    public void add(int index, Object element) {
        Objects.checkIndex(index, size);
        root.add(offset + index, element);
        increaseSize();
    }

    @Override
    public boolean addAll(Collection c) {
        boolean isElementsAdded = root.addAll(size + offset, c);
        increaseSize(c.size());
        return isElementsAdded;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        Objects.checkIndex(index, size);
        boolean isElementsAdded = root.addAll(index + offset, c);
        increaseSize(c.size());
        return isElementsAdded;
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
        Objects.checkIndex(index, size);
        Object oldValue = root.remove(offset + index);
        decreaseSize();
        return oldValue;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean results = false;
        for(Object o: c) {
            results = remove(o) || results;
        }
        return results;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean results = false;
        Object[] localElements = toArray();
        for (int i = 0; i < localElements.length; i++){
            if (!c.contains(localElements[i])) {
                results = remove(localElements[i]) || results;
            }
        }
        return results;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            set(i, null);
        }
        size = 0;
    }

    @Override
    public Object get(int index) {
        Objects.checkIndex(index, size);
        return root.get(index + offset);
    }

    @Override
    public Object set(int index, Object element) {
        Objects.checkIndex(index, size);
        return root.set(index + offset, element);
    }

    @Override
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

    @Override
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

    @Override
    public ListIterator listIterator() {
        return new MyListIterator(toArray());
    }

    @Override
    public ListIterator listIterator(int index) {
        Objects.checkIndex(index, size);
        return new MyListIterator(toArray(), index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return new MySubList(this, fromIndex, toIndex);
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
