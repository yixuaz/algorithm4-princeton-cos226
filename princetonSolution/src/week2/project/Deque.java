package week2.project;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static class Node<Item> {
        Item val;
        Node<Item> prev;
        Node<Item> next;

        public Node(Item val) {
            this.val = val;
        }

        public Node(Item val, Node<Item> prev, Node<Item> next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    private final Node<Item> headDummy;
    private final Node<Item> tailDummy;
    private int size;

    // construct an empty deque
    public Deque() {
        headDummy = new Node<>(null);
        tailDummy = new Node<>(null);
        headDummy.next = tailDummy;
        tailDummy.prev = headDummy;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item to be added should not be null");
        Node<Item> cur = new Node<>(item);
        cur.next = headDummy.next;
        headDummy.next.prev = cur;
        cur.prev = headDummy;
        headDummy.next = cur;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("item to be added should not be null");
        Node<Item> cur = new Node<>(item);
        cur.next = tailDummy;
        cur.prev = tailDummy.prev;
        tailDummy.prev.next = cur;
        tailDummy.prev = cur;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Node<Item> tbd = headDummy.next;
        headDummy.next = tbd.next;
        tbd.next.prev = headDummy;
        tbd.next = null;
        tbd.prev = null;
        size--;
        return tbd.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Node<Item> tbd = tailDummy.prev;
        tailDummy.prev = tbd.prev;
        tbd.prev.next = tailDummy;
        tbd.next = null;
        tbd.prev = null;
        size--;
        return tbd.val;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> point = headDummy;

            @Override
            public boolean hasNext() {
                return point.next != tailDummy;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("iterator finish");
                }
                point = point.next;
                return point.val;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        // test deque base function work
        Deque<Integer> deque = new Deque<>();
        System.out.println("deque is Empty true " + deque.isEmpty());
        deque.addFirst(3);
        System.out.println("deque is not Empty true " + !deque.isEmpty());
        System.out.println("deque is first element 3 " + deque.removeFirst());
        System.out.println("deque size 0 " + deque.size());
        deque.addFirst(4);
        deque.addLast(5);
        System.out.println("deque last pop 5 4 " + deque.removeLast() + " " + deque.removeLast());
        deque.addLast(6);
        deque.addLast(7);
        System.out.println("deque last pop 6 7 " + deque.removeFirst() + " " + deque.removeFirst());

        // test deque iterator function
        deque.addLast(8);
        deque.addLast(9);
        deque.addLast(6);
        deque.addLast(7);
        System.out.println("8 9 6 7 ");
        for (int i : deque) {
            System.out.print(i + " ");
        }
        System.out.println();
        // test different type
        Deque<String> strDq = new Deque<>();
        String expected = "abc";
        strDq.addFirst(expected);
        System.out.println(expected + " " + strDq.removeLast());
    }

}
