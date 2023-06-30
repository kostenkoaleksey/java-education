package collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyIterator implements Iterator {
    protected int current;
    protected Object[] elements;

    public MyIterator(Object[] elements) {
        this.elements = elements;
    }

    @Override
    public boolean hasNext() {
        return current < elements.length;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Object currentElement = elements[current];
        current += 1;

        return currentElement;
    }
}
