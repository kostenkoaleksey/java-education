package collections.linkedlist;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
    static Integer[] items = {10, 20, 30};

    static MyLinkedList<Integer> list;

    @BeforeEach
    void init() {
        list = new MyLinkedList<Integer>(Arrays.asList(items));
    }

    @AfterEach
    void teardown() {
        list.clear();
    }

    @Test
    @DisplayName("Check the list size is equal 0.")
    void sizeIsNull() {
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check the list size is equal 3.")
    void sizeIsTwo() {
        assertEquals(3, list.size());
    }

    @Test
    void isEmpty() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void isNotEmpty() {
        assertFalse(list.isEmpty());
    }

    @Test
    void containsElement() {
        assertTrue(list.contains(items[0]));
    }

    @Test
    void doesNotContainElement() {
        Integer fakeElement = 40;
        assertFalse(list.contains(fakeElement));
    }

    @Test
    void containsAllElements() {
        List<Integer> collection = Arrays.asList(items);
        assertTrue(list.containsAll(collection));
    }

    @Test
    void doesNotContainAllElements() {
        List<Integer> collection = Arrays.asList(items);
        collection.set(0, 15);
        assertFalse(list.containsAll(collection));
    }

    @Test
    void iteratorInstance() {
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator instanceof Iterator<Integer>);
    }

    @Test
    void iteratorSameSequence() {
        Iterator<Integer> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(items[i], iterator.next());
            i++;
        }
    }

    @Test
    void toArray() {
        Object[] resultArray = list.toArray();
        assertArrayEquals(items, resultArray);
        assertNotSame(items, resultArray);
    }

//    @Test
//    void toArrayOfGiventype() {}

    @Test
    void addNull() {
        boolean result = list.add(null);
        assertTrue(result);
        assertEquals(4, list.size());
        assertNull(list.getLast());
    }

    @Test
    void addElement() {
        Integer newElement = 40;
        boolean result = list.add(newElement);
        assertTrue(result);
        assertEquals(4, list.size());
        assertEquals(40, list.getLast());
    }

    @Test
    void addElementOutOfRangeIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            Integer newElement = 40;

            list.add(4, newElement);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void addCollectionToTheEnd() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        collection.add(15);
        collection.add(25);
        collection.add(35);
        boolean result = list.addAll(collection);
        assertTrue(result);
        assertEquals(6, list.size());
        assertTrue(list.containsAll(collection));
    }

    @Test
    void addEmptyCollection() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        boolean result = list.addAll(collection);
        assertFalse(result);
        assertEquals(3, list.size());
    }

    @Test
    void addCollectionAtPositionWithZeroIndex() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        collection.add(15);
        collection.add(25);
        collection.add(35);
        boolean result = list.addAll(0, collection);
        assertTrue(result);
        assertEquals(6, list.size());
        assertEquals(collection.get(0), list.get(0));
        assertEquals(collection.get(1), list.get(1));
        assertEquals(collection.get(2), list.get(2));
    }
    @Test
    void addCollectionAtPositionWithOutOfRangeIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ArrayList<Integer> collection = new ArrayList<Integer>();
            collection.add(15);
            collection.add(25);
            collection.add(35);

            list.addAll(4, collection);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void getElementByIndex(int index) {
        assertEquals(items[index], list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void getElementByIndexOutOfRange(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(index);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void setElementByIndex(int index) {
        Integer newValue = 15;
        Integer oldValue = list.set(index, newValue);
        assertEquals(oldValue, items[index]);
        assertEquals(newValue, list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void setElementByIndexOutOfRange(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(index, 15);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }
}