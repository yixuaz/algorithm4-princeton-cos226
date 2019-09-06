package part1.week2.project;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] data;
    private int size;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        resize(10);
        capacity = 10;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("enqueue with null");
        }
        if (size == capacity) {
            resize(capacity << 1);
        }
        data[size++] = item;
    }

    private void resize(int newCapacity) {
        assert size < newCapacity;
        this.capacity = newCapacity;
        Item[] tmp = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            tmp[i] = data[i];
        }
        data = tmp;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        int toBeRemove = StdRandom.uniform(size);
        Item ret = data[toBeRemove];
        data[toBeRemove] = null;
        swap(toBeRemove, size - 1);
        size--;
        if (size < capacity / 4) resize(capacity >> 1);
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        int ret = StdRandom.uniform(size);
        return data[ret];
    }

    private void swap(int idx, int idx2) {
        Item tmp = data[idx2];
        data[idx2] = data[idx];
        data[idx] = tmp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return (Iterator<Item>) new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int idx;
        private final Item[] itData;

        public RandomizedQueueIterator() {
            idx = size - 1;
            itData = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                itData[i] = data[i];
            }
            StdRandom.shuffle(itData);
        }

        @Override
        public boolean hasNext() {
            return idx >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("have no more element");
            }
            return itData[idx--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<>();
        StdOut.println("rq is empty true " + randomQueue.isEmpty());
        randomQueue.enqueue(3);
        StdOut.println("rq is empty false " + randomQueue.isEmpty());
        StdOut.println("rq size 1 " + randomQueue.size());
        StdOut.println("rq sample val 3 " + randomQueue.sample());
        StdOut.println("rq sample val 3 " + randomQueue.sample());
        randomQueue.enqueue(5);
        StdOut.println("rq sample val " + randomQueue.dequeue());
        StdOut.println("rq sample val " + randomQueue.dequeue());
        randomQueue.enqueue(9993);
        randomQueue.enqueue(9993);
        randomQueue.enqueue(9993);
        randomQueue.enqueue(3);
        randomQueue.enqueue(5);
        randomQueue.enqueue(7);
        randomQueue.enqueue(8);
        randomQueue.enqueue(9);
        for (int i : randomQueue)
            StdOut.print(i + " ");
        StdOut.println();
        for (int i : randomQueue)
            StdOut.print(i + " ");
        StdOut.println();
    }

}