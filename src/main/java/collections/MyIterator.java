package collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Iterator interface.
 */
public class MyIterator<E> implements Iterator<E> {
    protected int current;
    protected E[] elements;

    /**
     * Constructor.
     *
     * @param elements The list of elements for iteration.
     */
    public MyIterator(E[] elements) {
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
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        E currentElement = elements[current];
        current += 1;

        return currentElement;
    }
}
