package part1.week4.priorityqueue;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * this question could be solved by two priority queue, one is maxheap, another is minheap.
 * because we need to  find the median in constant time, we need to balance the elements evenly into two pq.
 * the most important is that maxHeap.max <= minHeap.min
 * then the maxHeap max is the median.
 * the problem now is remove and insert, when insert we can insert to the max heap first,
 * then check it still meet the invariant, the size diff is <= 1, and maxHeap max <= minHeap min,
 * if not, do some balance action.
 * remove we can just pop the maxHeap max, then check invariant and do balance.
 */
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
