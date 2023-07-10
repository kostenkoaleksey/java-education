import collections.MyList;

import java.util.ArrayList;
import java.lang.Integer;
import java.util.List;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(11);
        list.add(12);

        MyList<Integer> collection = new MyList(1);
        collection.add(2);
        collection.add(1);
        collection.add(3);
        collection.add(0, 4);
        collection.add(3, 4);

        System.out.println("First index of 4: " + collection.indexOf(4));
        System.out.println("Last index of 4: " + collection.lastIndexOf(4));
        System.out.println("Collection contains 5: " + collection.contains(5));
        System.out.println("Collection contains the list: " + collection.containsAll(list));

        collection.remove(0);
        collection.remove(Integer.valueOf(4));

        collection.addAll(list);
        System.out.println("Collection contains the list: " + collection.containsAll(list));
        collection.addAll(0, list);

        // Start manipulations with sublist
        List sublist = collection.subList(3, 6);
        sublist.add(1, 20);
        sublist.remove(0);
        sublist.addAll(0, list);
        for (Object item : sublist) {
            System.out.println("Sublist item: " + item);
        }
        // End manipulations with sublist

//        collection.retainAll(list);
//        collection.removeAll(list);

        System.out.println("Get element with index 0: " + collection.get(0));
        System.out.println("Set element with index 1: " + collection.set(1, -11));

        for (Object o : collection) {
            System.out.println(o);
        }

        // Start manipulations with ListIterator of the collection
        ListIterator listIterator = collection.listIterator(1);
        System.out.println(listIterator.previous());
        // End manipulations with ListIterator of the collection
    }
}