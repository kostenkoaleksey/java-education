package collections.linkedlist;

import java.util.List;

public interface DoubleEndedList<E> extends List<E> {
    /**
     * Adds an element into the beginning of the list.
     *
     * @param element The element to insert into the list.
     * @return {@code true} when the element has been added successfully.
     */
    boolean pushToHead(E element);

    /**
     * Returns the element from the beginning of the list.
     *
     * @return
     */
    E getFirst();

    /**
     * Returns the element from the end of the list.
     *
     * @return
     */
    E getLast();
}
