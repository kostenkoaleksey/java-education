package collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyListIterator extends MyIterator implements ListIterator {
    public MyListIterator(Object[] elements) {
        super(elements);
        current = 0;
    }

    public MyListIterator(Object[] elements, int index) {
        super(elements);
        current = index;
    }

    @Override
    public boolean hasPrevious() {
        return current > 0;
    }

    @Override
    public int previousIndex() {
        return current == 0 ? -1 : current - 1;
    }

    @Override
    public Object previous() {
        int index = current - 1;
        if (index < 0) {
            throw new NoSuchElementException();
        }

        if (index > elements.length) {
            throw new NoSuchElementException();
        }

        current = index;
        return elements[current];
    }

    @Override
    public int nextIndex() {
        return Math.min(elements.length, current + 1);
    }

    @Override
    public void remove() {

    }

    @Override
    public void set(Object o) {

    }

    @Override
    public void add(Object o) {

    }
}
