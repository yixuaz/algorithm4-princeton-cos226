package week4.priorityqueue;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class DynamicMedian {
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    public void insert(int e) {
        if (maxHeap.isEmpty() || e <= maxHeap.peek()) {
            maxHeap.offer(e);
        } else {
            minHeap.offer(e);
        }
        balance();
    }
    private void balance() {
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        }
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public int findMedian() {
        if (maxHeap.isEmpty()) {
            throw new NoSuchElementException("no more element");
        }
        return maxHeap.peek();
    }

    public int removeMedian() {
        if (maxHeap.isEmpty()) {
            throw new NoSuchElementException("no more element");
        }
        int ret = maxHeap.poll();
        balance();
        return ret;
    }
}
