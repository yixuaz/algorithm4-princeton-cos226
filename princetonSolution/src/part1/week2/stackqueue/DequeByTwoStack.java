package part1.week2.stackqueue;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * the thought is similar with the two stack implementing queue. we keep two stack, one the open for left,
 * another is open for right. so offer first, go left stack, offer last, go right stack.
 * <p>
 * the hardest part is poll first or poll right when the left stack or right stack empty
 * we need to do a rebalance action like the Q1, we keep another auxiliary stack, to move half elements
 * of not empty stack into it then move another half to the empty stack. then we move the auxiliary stack back.
 *
 * @param <Item>
 */
public class DequeByTwoStack<Item> {
    private Stack<Item> left, right;

    // construct an empty deque
    public DequeByTwoStack() {
        left = new Stack<>();
        right = new Stack<>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return left.isEmpty() && right.isEmpty();
    }

    // return the number of items on the deque
    public int size() {
        return left.size() + right.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        left.push(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        right.push(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty()");
        }
        if (!left.isEmpty()) {
            return left.pop();
        }
        balance(left, right);
        return left.pop();
    }

    public Item peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty()");
        }
        if (!left.isEmpty()) {
            return left.peek();
        }
        balance(left, right);
        return left.peek();
    }


    private void balance(Stack<Item> empty, Stack<Item> something) {
        Stack<Item> tmp = new Stack<>();
        int size = something.size();
        for (int i = 0; i < size / 2; i++) {
            tmp.push(something.pop());
        }
        while (!something.isEmpty()) {
            empty.push(something.pop());
        }
        while (!tmp.isEmpty()) {
            something.push(tmp.pop());
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty()");
        }
        if (!right.isEmpty()) {
            return right.pop();
        }
        balance(right, left);
        return right.pop();
    }

    public Item peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty()");
        }
        if (!right.isEmpty()) {
            return right.peek();
        }
        balance(right, left);
        return right.peek();
    }

}
