package collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Iterator interface.
 */
public class MyIterator implements Iterator {
    protected int current;
    protected Object[] elements;

    /**
     * Constructor.
     *
     * @param elements The list of elements for iteration.
     */
    public MyIterator(Object[] elements) {
        this.elements = elements;
    }

    /**
     * Checks whether the iterator has next element.
     *
     * @return {@code true} when there is next element.
     */
    public boolean hasNext() {
        return current < elements.length;
    }

    /**
     * Returns current element of the iterator.
     * <p>
     * Switches pointer to the next element.
     *
     * @return current element of iterator.
     */
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Object currentElement = elements[current];
        current += 1;

        return currentElement;
    }
}
