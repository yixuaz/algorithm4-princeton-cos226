package part1.week2.stackqueue;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * the naive though for this problem, we keep two stack, one is original, another is keep the current max.
 * <p>
 * we can do better, because there are a lot of data redundancy in second stack.
 * we can only record the one which make the max changed idx.
 * then if we pop to that idx, we need to pop from max stack.
 * for example,
 * stack : 1,1,2,2,3
 * max val: 1,2,3
 * max idx, 0,2,4
 *
 * @param <E>
 */
public class StackWithMax<E extends Comparable<E>> {
    private int size;
    private Stack<E> ori, maxVal;
    private Stack<Integer> maxIdx;

    public StackWithMax() {
        this.size = 0;
        this.ori = new Stack<>();
        this.maxVal = new Stack<>();
        this.maxIdx = new Stack<>();
    }

    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        E ret = ori.pop();
        if (ori.size() == maxIdx.peek()) {
            maxVal.pop();
            maxIdx.pop();
        }
        return ret;
    }

    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        return ori.peek();
    }

    public void push(E e) {
        if (maxVal.isEmpty() || e.compareTo(maxVal.peek()) > 0) {
            maxVal.push(e);
            maxIdx.push(ori.size());
        }
        ori.push(e);
    }

    public E max() {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        return maxVal.peek();
    }

    public int size() {
        return ori.size();
    }

    public boolean isEmpty() {
        return ori.isEmpty();
    }

}
