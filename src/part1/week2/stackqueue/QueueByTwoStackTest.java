package part1.week2.stackqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

public class QueueByTwoStackTest {
    @Test
    public void basicTest1() {
        QueueByTwoStack<Integer> queue = new QueueByTwoStack();
        Assert.assertEquals(true,queue.isEmpty());
        Assert.assertEquals(0,queue.size());
        queue.enqueue(5);
        Assert.assertEquals(false,queue.isEmpty());
        Assert.assertEquals(1,queue.size());
        Assert.assertEquals(5,queue.peek().intValue());
        queue.enqueue(6);
        Assert.assertEquals(5,queue.peek().intValue());
        Assert.assertEquals(2,queue.size());
        Assert.assertEquals(5,queue.dequeue().intValue());
        Assert.assertEquals(6,queue.peek().intValue());
        Assert.assertEquals(false,queue.isEmpty());
        Assert.assertEquals(1,queue.size());
        Assert.assertEquals(6,queue.dequeue().intValue());
    }

    @Test
    public void basicTest2() {
        QueueByTwoStack<String> queue = new QueueByTwoStack();
        for (int i = 0; i < 10; i++) queue.enqueue(i+"");
        for (int i = 0; i < 5; i++) Assert.assertEquals(i+"",queue.dequeue());
        for (int i = 0; i < 5; i++) queue.enqueue(i+"");
        for (int i = 0; i < 10; i++) Assert.assertEquals(((i+5) % 10) + "",queue.dequeue());
    }

    @Test(expected = NoSuchElementException.class)
    public void basicTest3() {
        QueueByTwoStack<String> queue = new QueueByTwoStack();
        queue.peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void basicTest5() {
        QueueByTwoStack<String> queue = new QueueByTwoStack();
        queue.dequeue();
    }

    @Test(timeout = 120)
    public void randomTest() {
        Queue<Integer> expect = new LinkedList<>();
        QueueByTwoStack<Integer> test = new QueueByTwoStack();
        int N = 100000;
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(expect.isEmpty(),test.isEmpty());
            Assert.assertEquals(expect.size(),test.size());
            if (test.isEmpty()) {
                int val = r.nextInt(N);
                test.enqueue(val);
                expect.offer(val);
            } else {
                Assert.assertEquals(expect.peek(),test.peek());
                if (r.nextInt(2) < 1) {
                    Assert.assertEquals(expect.poll(),test.dequeue());
                } else {
                    int val = r.nextInt(N);
                    test.enqueue(val);
                    expect.offer(val);
                }
            }
        }
    }

}