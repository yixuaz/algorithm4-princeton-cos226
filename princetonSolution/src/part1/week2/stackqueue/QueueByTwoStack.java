package part1.week2.stackqueue;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * the stack is lifo, queue is fifo. when we want to out,
 * we need to reverse elements in stack. so that's the usage of second stack.
 *
 * @param <Item>
 */
public class QueueByTwoStack<Item> {
    private Stack<Item> in, out;

    public QueueByTwoStack() {
        in = new Stack<>();
        out = new Stack<>();
    }

    public int size() {
        return in.size() + out.size();
    }

    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }

    public void enqueue(Item i) {
        in.push(i);
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        if (!out.isEmpty()) {
            return out.pop();
        }
        while (!in.isEmpty()) {
            out.push(in.pop());
        }
        return out.pop();
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        if (!out.isEmpty()) {
            return out.peek();
        }
        while (!in.isEmpty()) {
            out.push(in.pop());
        }
        return out.peek();
    }
}
