package week4.priorityqueue;

import java.util.NoSuchElementException;
import java.util.Random;

public class RandomPriorityQueue<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;
    public RandomPriorityQueue(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }
    public void insert(Key key) {
        if (N == pq.length - 1)
            throw new IllegalStateException("pq is full");
        pq[++N] = key;
        swim(N);
    }
    public Key delMax() {
        if (N < 1)
            throw new NoSuchElementException("pq is empty");
        swap(1,N);
        Key ret = pq[N];
        pq[N--] = null;
        sink(1);
        return ret;
    }
    public Key max() {
        if (N < 1)
            throw new NoSuchElementException("pq is empty");
        return pq[1];
    }
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            swap(k/2, k);
            k = k/2;
        }
    }
    private void sink(int k) {
        while (k * 2 <= N) {
            int larger = k * 2;
            if (larger + 1 <= N && less(larger,larger + 1))
                larger++;
            if (!less(k,larger))  break;
            swap(k,larger);
            k = larger;
        }
    }
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }
    private void swap(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public Key sample() {
        if (N < 1)
            throw new NoSuchElementException("pq is empty");
        Random r = new Random();
        int idx = 1 + r.nextInt(N);
        return pq[idx];
    }

    public Key delRandom() {
        if (N < 1)
            throw new NoSuchElementException("pq is empty");
        Random r = new Random();
        int idx = 1 + r.nextInt(N);
        swap(idx,N);
        Key ret = pq[N];
        pq[N] = null;
        if (idx != N--) {
            swim(idx);
            sink(idx);
        }
        return ret;
    }

}
