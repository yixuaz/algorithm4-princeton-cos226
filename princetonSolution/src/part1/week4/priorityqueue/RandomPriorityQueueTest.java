package part1.week4.priorityqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class RandomPriorityQueueTest {
    @Test
    public void basicTest() {
        RandomPriorityQueue<Integer> rpq = new RandomPriorityQueue<>(100);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < 100; i++) {
            rpq.insert(i);
            pq.offer(i);
        }
        while (!rpq.isEmpty()) {
            Assert.assertEquals(pq.peek(), rpq.max());
            int removed = rpq.delRandom();
            pq.remove(removed);
        }
    }

    @Test
    public void testRandom() {
        int N = 1000;
        RandomPriorityQueue<Integer> rpq = new RandomPriorityQueue<>(N);
        for (int i = 0; i < N; i++) {
            rpq.insert(i);
        }
        int[] sample = new int[N];
        for (int i = 0; i < N * N; i++) {
            sample[rpq.sample()]++;
        }
        for (int i = 0; i < N; i++) {
            Assert.assertTrue(Math.abs(N - sample[i]) <= 150);
        }
    }

    @Test
    public void testRandom2() {
        int N = 100000;
        RandomPriorityQueue<Integer> rpq = new RandomPriorityQueue<>(N);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            if (rpq.isEmpty() || r.nextInt(2) == 0) {
                int val = r.nextInt(N);
                rpq.insert(val);
                pq.offer(val);
            } else {
                Assert.assertEquals(rpq.max(), pq.peek());
                if (r.nextInt(2) == 0) {
                    Assert.assertEquals(rpq.delMax(), pq.poll());
                } else {
                    int removed = rpq.delRandom();
                    Assert.assertTrue(pq.remove(removed));
                }
            }
        }
    }
}