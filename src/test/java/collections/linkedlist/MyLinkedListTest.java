package collections.linkedlist;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
    static Integer[] initialItemsList = {10, 20, 30, 10, 15, 1, 2, 2, 6};

    static MyLinkedList<Integer> list;

    @BeforeEach
    void init() {
        list = new MyLinkedList<Integer>(Arrays.asList(initialItemsList));
    }

    @AfterEach
    void teardown() {
        list.clear();
    }

    @Test
    void shouldHaveZeroSize_WhenListCleared() {
        list.clear();
        assertEquals(0, list.size());
    }


    @Test
    void shouldReturnNonZeroValue_WhenListIsNotEmpty() {
        assertEquals(initialItemsList.length, list.size());
    }

    @Test
    void shouldReturnTrue_WhenListIsEmpty() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void shouldReturnFalse_WhenListIsNotEmpty() {
        assertFalse(list.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 30, 20})
    void shouldReturnTrue_WhenListContainsElement(int element) {
        assertTrue(list.contains(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {12, 300, 40})
    void shouldReturnFalse_WhenListDoesNotContainElement(int fakeElement) {
        assertFalse(list.contains(fakeElement));
    }

    @Test
    void shouldReturnTrue_WhenListContainsAllElements() {
        List<Integer> collection = Arrays.asList(initialItemsList);
        assertTrue(list.containsAll(collection));
    }

    @Test
    void shouldReturnFalse_WhenListDoesNotContainAtLeastOneElementFromCollection() {
        List<Integer> collection = List.of(20, 40, 30);
        assertFalse(list.containsAll(collection));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void shouldFindIndexOfElement_WhenElementExists(Integer element) {
        assertEquals(0, list.indexOf(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {100})
    void shouldNotFindIndexOfElement_WhenElementDoesNotExist(Integer element) {
        assertEquals(-1, list.indexOf(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void shouldFindIndexOfElementStartingEnd_WhenElementExists(Integer element) {
        assertEquals(3, list.lastIndexOf(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {100})
    void shouldNotFindIndexOfElementStartingEnd_WhenElementDoesNotExist(Integer element) {
        assertEquals(-1, list.lastIndexOf(element));
    }

    @Test
    void shouldReturnIteratorInstance() {
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator instanceof Iterator<Integer>);
    }

    @Test
    void shouldReturnIteratorWithSameElementsSequence() {
        Iterator<Integer> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(initialItemsList[i], iterator.next());
            i++;
        }
    }

    @Test
    void shouldReturnListIteratorInstance() {
        ListIterator<Integer> listIterator = list.listIterator();
        assertInstanceOf(ListIterator.class, listIterator);
    }

    @Test
    void shouldReturnListIteratorWithSameElementsSequence() {
        ListIterator<Integer> listIterator = list.listIterator();
        int i = 0;
        while (listIterator.hasNext()) {
            assertEquals(listIterator.next(), list.get(i));
            i++;
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void shouldReturnListIteratorPointedToSpecificElement_IndexedToSecondElement(int index) {
        ListIterator<Integer> listIterator = list.listIterator(index);

        assertEquals(listIterator.previousIndex(), index -1);
        assertEquals(listIterator.next(), list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void shouldThrowException_WhenListIteratorIndexedOutOfRange(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.listIterator(index);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldReturnSubListOfElements_StartingSecond_EndingThird() {
        int fromIndex = 1;
        int toIndex = 3;
        List<Integer> subList = list.subList(fromIndex, toIndex);
        assertEquals(2, subList.size());
        assertArrayEquals(subList.toArray(), Arrays.copyOfRange(initialItemsList, fromIndex, toIndex));
    }

    @Test
    void shouldThrowException_WhenSubListStartIndexLessThanZero() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            List<Integer> sublist = list.subList(-1, 3);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldThrowException_WhenSubListStartIndexGreaterThanEndIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            List<Integer> sublist = list.subList(2, 1);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldThrowException_WhenSubListEndIndexGreaterThanListSize() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            List<Integer> sublist = list.subList(1, 10);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldReturnArrayOfElementsWithSameSequence() {
        Object[] resultArray = list.toArray();
        assertArrayEquals(initialItemsList, resultArray);
    }

    @Test
    void shouldAddNewElementToTheEnd() {
        Integer newElement = 40;
        boolean result = list.add(newElement);
        assertTrue(result);
        assertEquals(initialItemsList.length + 1, list.size());
        assertEquals(newElement, list.getLast());
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void shouldAddNewElementAtPlaceByIndex_IndexedToFirstPosition(Integer index) {
        Integer newElement = 40;
        list.add(index, newElement);
        assertEquals(initialItemsList.length + 1, list.size());
        assertEquals(newElement, list.get(index));
    }

    @Test
    void shouldThrowException_WhenNewElementIndexedOutOfRange() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            Integer newElement = 40;
            int outOfRangeIndex = 999;

            list.add(outOfRangeIndex, newElement);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldAddCollectionToTheEnd() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        collection.add(15);
        collection.add(25);
        collection.add(35);
        boolean result = list.addAll(collection);
        assertTrue(result);
        assertEquals(initialItemsList.length + collection.size(), list.size());
        assertArrayEquals(list.subList(initialItemsList.length, initialItemsList.length + collection.size()).toArray(), collection.toArray());
    }

    @Test
    void shouldReturnFalse_WhenAddingEmptyCollection() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        boolean result = list.addAll(collection);
        assertFalse(result);
        assertEquals(initialItemsList.length, list.size());
    }

    @Test
    void shouldAddCollectionAtSpecificPosition_IndexedToFirstElement() {
        ArrayList<Integer> collection = new ArrayList<Integer>();
        collection.add(15);
        collection.add(25);
        collection.add(35);
        boolean result = list.addAll(0, collection);
        assertTrue(result);
        assertEquals(initialItemsList.length + collection.size(), list.size());
        assertArrayEquals(list.subList(0, collection.size()).toArray(), collection.toArray());
    }

    @Test
    void shouldThrowException_WhenAddingCollectionAtPositionIndexedOutOfRange() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ArrayList<Integer> collection = new ArrayList<Integer>();
            collection.add(15);
            collection.add(25);
            collection.add(35);
            int outOfRangeIndex = 999;

            list.addAll(outOfRangeIndex, collection);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldRemoveExistingElement() {
        assertTrue(list.remove(initialItemsList[0]));
        assertEquals(initialItemsList.length - 1, list.size());
    }

    @Test
    void shouldReturnFalse_WhenElementDoesNotExist() {
        Integer fakeElement = 5555;
        assertFalse(list.remove(fakeElement));
        assertEquals(initialItemsList.length, list.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void shouldRemoveElementByIndex(int index) {
        list.remove(index);
        assertEquals(initialItemsList.length - 1, list.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    void shouldReturnOldValue_WhenElementRemovedByIndex(int index) {
        Integer oldValue = list.remove(index);
        assertEquals(oldValue, initialItemsList[index]);
    }


    @ParameterizedTest
    @ValueSource(ints = {10})
    void shouldThrowException_WhenRemoveByIndexOutOfRange(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(index);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldRemoveElementsSpecifiedInCollection() {
        assertTrue(list.removeAll(Arrays.asList(initialItemsList)));
        assertEquals(0, list.size());
    }

    @Test
    void shouldReturnFalse_WhenPassedEmptyCollection() {
        ArrayList<Integer> emptyCollection = new ArrayList<>();
        assertFalse(list.removeAll(emptyCollection));
        assertEquals(initialItemsList.length, list.size());
    }

    @Test
    void shouldReturnFalse_WhenRemoveCollectionWithoutMatchElements() {
        List<Integer> collection = List.of(100, 45, 18);

        assertFalse(list.removeAll(collection));
        assertEquals(initialItemsList.length, list.size());
    }

    @Test
    void shouldReturnTrue_WhenRemoveCollectionWithAtLeastOneMatchElement() {
        List<Integer> collection = List.of(10, 20, 3);
        int countElementsToBeDeleted = (int) list.stream().filter(collection::contains).count();

        assertTrue(list.removeAll(collection));
        assertEquals(initialItemsList.length - countElementsToBeDeleted, list.size());
    }

    @Test
    void shouldRetainOnlyElementsOfSpecifiedCollection() {
        List<Integer> collection = List.of(30, 20);

        assertTrue(list.retainAll(collection));
        assertEquals(2, list.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void shouldReturnElementByIndex(int index) {
        assertEquals(initialItemsList[index], list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void shouldThrowException_WhenGettingElementByIndexOutOfRange(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(index);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void shouldSetElementByIndex(int index) {
        Integer newValue = 15;
        list.set(index, newValue);
        assertEquals(newValue, list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void shouldReturnOldValue_WhenSetElementByIndex(int index) {
        Integer newValue = 15;
        Integer oldValue = list.set(index, newValue);
        assertEquals(oldValue, initialItemsList[index]);
    }

    @ParameterizedTest
    @ValueSource(ints = {10})
    void shouldThrowException_WhenSettingElementByIndexOutOfRange(int index) {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(index, 15);
        });
        assertInstanceOf(IndexOutOfBoundsException.class, exception);
    }

    @Test
    void shouldPushNewElementToHead() {
        Integer newElement = 12;
        assertTrue(list.pushToHead(newElement));
        assertEquals(initialItemsList.length + 1, list.size());
        assertEquals(newElement, list.get(0));
    }

    @Test
    void shouldReturnFirstElement() {
        assertEquals(initialItemsList[0], list.getFirst());
    }

    @Test
    void shouldReturnNull_WhenGettingFirstElementOnEmptyList() {
        list.clear();
        assertNull(list.getFirst());
    }

    @Test
    void shouldReturnLastElement() {
        assertEquals(initialItemsList[initialItemsList.length - 1], list.getLast());
    }

    @Test
    void shouldReturnNull_WhenGettingLastElementOnEmptyList() {
        list.clear();
        assertNull(list.getLast());
    }
}