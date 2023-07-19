package collections.linkedlist;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
    static Integer[] items = {10, 20, 30, 10};

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
    void clearTheList() {
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check the list size is equal 0.")
    void sizeIsNull() {
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Check the list size is equal 3.")
    void sizeNotNull() {
        assertEquals(items.length, list.size());
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

    @ParameterizedTest
    @ValueSource(ints = {10, 30, 20})
    void containsElement(int element) {
        assertTrue(list.contains(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 300, 40})
    void doesNotContainElement(int fakeElement) {
        assertFalse(list.contains(fakeElement));
    }

    @Test
    void containsAllElements() {
        List<Integer> collection = Arrays.asList(items);
        assertTrue(list.containsAll(collection));
    }

    @Test
    void doesNotContainAllElements() {
        List<Integer> collection = List.of(20, 40, 30);
        assertFalse(list.containsAll(collection));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void indexOfExistingElementStartingHead(Integer element) {
        assertEquals(0, list.indexOf(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {100})
    void indexOfNonExistingElementStartingHead(Integer element) {
        assertEquals(-1, list.indexOf(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void indexOfExistingElementStartingTail(Integer element) {
        assertEquals(3, list.lastIndexOf(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {100})
    void indexOfNonExistingElementStartingTail(Integer element) {
        assertEquals(-1, list.lastIndexOf(element));
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
    void listIteratorInstance() {
        ListIterator<Integer> listIterator = list.listIterator();
        assertInstanceOf(ListIterator.class, listIterator);
    }

    @Test
    void listIteratorSameSequence() {
        ListIterator<Integer> listIterator = list.listIterator();
        int i = 0;
        while (listIterator.hasNext()) {
            assertEquals(listIterator.next(), list.get(i));
            i++;
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void listIteratorStartingIndex(int index) {
        ListIterator<Integer> listIterator = list.listIterator(index);
        while (listIterator.hasNext()) {
            assertEquals(listIterator.next(), list.get(index));
            index++;
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void listIteratorStartingOutOfRangeIndex(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ListIterator<Integer> listIterator = list.listIterator(index);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void subListExitingIndexes() {
        int fromIndex = 1;
        int toIndex = 3;
        List<Integer> subList = list.subList(fromIndex, toIndex);
        assertEquals(2, subList.size());
        assertArrayEquals(subList.toArray(), Arrays.copyOfRange(items, fromIndex, toIndex));
    }

    @Test
    void subListStartingIndexLessThenZero() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            List<Integer> sublist = list.subList(-1, 3);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void subListStartingIndexGreaterThenEnding() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            List<Integer> sublist = list.subList(2, 1);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void subListEndingIndexGreaterThenListSize() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            List<Integer> sublist = list.subList(1, 10);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void toArraySameAsInitialOne() {
        Object[] resultArray = list.toArray();
        assertArrayEquals(items, resultArray);
    }
    @Test
    void toArrayProperSequenceAsInitialOne() {
        Object[] resultArray = list.toArray();
        assertArrayEquals(resultArray, items);
    }

    @Test
    void toArrayNewArrayAllocated() {
        Object[] resultArray = list.toArray();
        assertNotSame(resultArray, items);
    }

    @Test
    void addNull() {
        boolean result = list.add(null);
        assertTrue(result);
        assertEquals(items.length + 1, list.size());
        assertNull(list.getLast());
    }

    @Test
    void addElement() {
        Integer newElement = 40;
        boolean result = list.add(newElement);
        assertTrue(result);
        assertEquals(items.length + 1, list.size());
        assertEquals(newElement, list.getLast());
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void addElementAtPlaceByIndex(Integer index) {
        Integer newElement = 40;
        list.add(index,newElement);
        assertEquals(newElement, list.get(index));
        assertArrayEquals(list.subList(index + 1, list.size()).toArray(), items);
    }

    @Test
    void addElementOutOfRangeIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            Integer newElement = 40;

            list.add(items.length + 1, newElement);
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
        assertEquals(items.length + collection.size(), list.size());
        assertArrayEquals(list.subList(items.length, items.length + collection.size()).toArray(), collection.toArray());
    }

    @Test
    void addEmptyCollection() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        boolean result = list.addAll(collection);
        assertFalse(result);
        assertEquals(items.length, list.size());
    }

    @Test
    void addCollectionAtPositionWithZeroIndex() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        collection.add(15);
        collection.add(25);
        collection.add(35);
        boolean result = list.addAll(0, collection);
        assertTrue(result);
        assertEquals(items.length + collection.size(), list.size());
        assertArrayEquals(list.subList(0, collection.size()).toArray(), collection.toArray());
    }
    @Test
    void addCollectionAtPositionWithOutOfRangeIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ArrayList<Integer> collection = new ArrayList<Integer>();
            collection.add(15);
            collection.add(25);
            collection.add(35);

            list.addAll(items.length + 1, collection);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void removeExistingElement() {
        assertTrue(list.remove(items[0]));
        assertEquals(items.length - 1, list.size());
    }

    @Test
    void removeNotExistingElement() {
        Integer fakeElement = 5;
        assertFalse(list.remove(fakeElement));
        assertEquals(items.length, list.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void removeElementByIndex(int index) {
        Integer oldValue = list.remove(index);
        assertEquals(items.length - 1, list.size());
        assertEquals(oldValue, items[index]);
    }
    @ParameterizedTest
    @ValueSource(ints = {10})
    void removeElementByOutOfRangeIndex(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(index);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void removeCollection() {
        assertTrue(list.removeAll(Arrays.asList(items)));
        assertEquals(0, list.size());
    }

    @Test
    void removeEmptyCollection() {
        ArrayList<Integer> emptyCollection = new ArrayList<>();
        assertFalse(list.removeAll(emptyCollection));
        assertEquals(items.length, list.size());
    }

    @Test
    void removeCollectionWithoutMatchElements() {
        List<Integer> collection = List.of(1, 2, 3);

        assertFalse(list.removeAll(collection));
        assertEquals(items.length, list.size());
    }

    @Test
    void removeCollectionWithOneMatchElement() {
        List<Integer> collection = List.of(1, 20, 3);

        assertTrue(list.removeAll(collection));
        assertEquals(items.length - 1, list.size());
    }

    @Test
    void retainElementsFromCollection() {
        List<Integer> collection = List.of(30, 20);

        assertTrue(list.retainAll(collection));
        assertEquals(2, list.size());
    }

    @Test
    void retainElementsFromTheSameCollection() {
        List<Integer> collection = List.of(items);

        assertFalse(list.retainAll(collection));
        assertEquals(items.length, list.size());
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

    @Test
    void pushToHead() {
        Integer newElement = 12;
        assertTrue(list.pushToHead(newElement));
        assertEquals(items.length + 1, list.size());
        assertEquals(newElement, list.get(0));
    }

    @Test
    void getFirstElement() {
        assertEquals(items[0], list.getFirst());
    }

    @Test
    void getFirstElementNull() {
        list.clear();
        assertNull(list.getFirst());
    }

    @Test
    void getLastElement() {
        assertEquals(items[items.length - 1], list.getLast());
    }

    @Test
    void getLastElementNull() {
        list.clear();
        assertNull(list.getLast());
    }
}