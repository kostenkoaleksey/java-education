package collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation the ListIterator interface.
 */
public class MyListIterator extends MyIterator implements ListIterator {
    /**
     * Constructor.
     *
     * @param elements array of elements for iteration.
     */
    public MyListIterator(Object[] elements) {
        super(elements);
        current = 0;
    }

    /**
     * Constructor.
     *
     * @param elements array of elements for iteration.
     * @param index index of element to start from.
     */
    public MyListIterator(Object[] elements, int index) {
        super(elements);
        current = index;
    }

    /**
     * Checks whether there is previous element.
     *
     * @return {@code true} when there is previous element.
     */
    public boolean hasPrevious() {
        return current > 0;
    }

    /**
     * Returns the index of the previous element.
     *
     * @return -1 when cursor is at the first position.
     */
    public int previousIndex() {
        return current == 0 ? -1 : current - 1;
    }

    /**
     * Returns previous element in iterator.
     *
     * @return previous element.
     * @throws NoSuchElementException When index is out of iterator range.
     */
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

    /**
     * Returns the next index of iterator.
     *
     * @return index of the next element.
     */
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
