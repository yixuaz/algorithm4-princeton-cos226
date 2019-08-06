package week5.bst;

import edu.princeton.cs.algs4.RedBlackBST;

import java.util.NoSuchElementException;
import java.util.TreeMap;

public class GeneralizedQueue<T> {
    private RedBlackBST<Integer,T> rbt;
    private int idx;
    public GeneralizedQueue() {
        rbt = new RedBlackBST<>();
        idx = 0;
    }
    public void append(T item) {
        rbt.put(idx++, item);
    }

    public T removeFront() {
        if (rbt.isEmpty())
            throw new NoSuchElementException("queue is empty");
        T ret =  rbt.get(rbt.min());
        rbt.deleteMin();
        return ret;
    }

    public T get(int i) {
        return rbt.get(rbt.select(i));
    }

    public T remove(int i) {
        int key = rbt.select(i);
        T ret = rbt.get(key);
        rbt.delete(key);
        return ret;
    }
}
