package week2.stackqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DequeByTwoStackTest {
    @Test
    public void basicTest1() {
        DequeByTwoStack<Integer> deque = new DequeByTwoStack();
        Assert.assertEquals(true,deque.isEmpty());
        Assert.assertEquals(0,deque.size());
        deque.addFirst(5);
        Assert.assertEquals(false,deque.isEmpty());
        Assert.assertEquals(1,deque.size());
        Assert.assertEquals(5,deque.peekFirst().intValue());
        deque.addFirst(6);
        Assert.assertEquals(6,deque.peekFirst().intValue());
        Assert.assertEquals(2,deque.size());
        Assert.assertEquals(5,deque.peekLast().intValue());
        Assert.assertEquals(5,deque.removeLast().intValue());
        Assert.assertEquals(6,deque.peekLast().intValue());
        Assert.assertEquals(false,deque.isEmpty());
        Assert.assertEquals(1,deque.size());
        Assert.assertEquals(6,deque.removeFirst().intValue());
    }

    @Test
    public void basicTest2() {
        DequeByTwoStack<String> deque = new DequeByTwoStack();
        for (int i = 0; i < 10; i++) deque.addFirst(i+"");
        for (int i = 0; i < 5; i++) Assert.assertEquals(i+"",deque.removeLast());
        for (int i = 0; i < 5; i++) deque.addFirst(i+"");
        for (int i = 0; i < 10; i++) Assert.assertEquals(((i+5) % 10) + "",deque.removeLast());
    }

    @Test(timeout = 120)
    public void randomTest() {
        Deque<Integer> expect = new ArrayDeque<>();
        DequeByTwoStack<Integer> test = new DequeByTwoStack();
        int N = 100000;
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.isEmpty(),test.isEmpty());
            Assert.assertEquals(expect.size(),test.size());
            if (test.isEmpty()) {
                int val = r.nextInt(N);
                if (r.nextInt(2) < 1) {
                    test.addFirst(val);
                    expect.addFirst(val);
                } else {
                    test.addLast(val);
                    expect.addLast(val);
                }
            } else {
                Assert.assertEquals(expect.peekFirst(),test.peekFirst());
                Assert.assertEquals(expect.peekLast(),test.peekLast());
                int pos = r.nextInt(4);
                if (pos < 1) {
                    Assert.assertEquals(expect.removeFirst(),test.removeFirst());
                } else if (pos < 2) {
                    Assert.assertEquals(expect.removeLast(),test.removeLast());
                } else if (pos < 3) {
                    int val = r.nextInt(N);
                    test.addFirst(val);
                    expect.addFirst(val);
                } else {
                    int val = r.nextInt(N);
                    test.addLast(val);
                    expect.addLast(val);
                }
            }
        }
    }
}