package collections.linkedlist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    @DisplayName("Check the list size.")
    void size() {
        MyLinkedList<Integer> list = new MyLinkedList<Integer>();
        assertEquals(0, list.size());
        list.add(1);
        assertEquals(1, list.size());
    }

    @Test
    void isEmpty() {
    }

    @Test
    void contains() {
    }
}