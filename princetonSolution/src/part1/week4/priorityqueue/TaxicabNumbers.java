package part1.week4.priorityqueue;

import java.util.*;

import static commonutil.PrimitiveArrayConverter.convertToLongArray;

/**
 * version 1 could be prepare all the a^3 + b^3 pair result, then sort them.
 * we can know the result are same is neighbor, if there are more than 1 number in the list, this is taxicab number
 * <p>
 * version 2 , we could use priority queue. the record 1 ~ n-1's pair, pair contains two  number,
 * one is a and another is b, pair 1's first number always is 1,
 * second number can be combined with any number large than 1.
 * same as others pair k.
 * so now we pop the min, check it is same as previous,
 * if it is, we find a tax number.
 * if not, we increase second number in pair.
 */
public class TaxicabNumbers {
    public static long[] findAllTaxicabNum(int n) {
        List<Long> candidates = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                candidates.add((long) i * i * i + (long) j * j * j);
            }
        }
        Collections.sort(candidates);
        List<Long> result = new ArrayList<>();
        for (int i = 1; i < candidates.size(); i++) {
            int j = i;
            while (i < candidates.size() && candidates.get(i).longValue() == candidates.get(i - 1)) {
                i++;
            }
            if (j != i) {
                result.add(candidates.get(j));
            }
        }
        return convertToLongArray(result);
    }


    private static class Pair {
        private final long fir;
        private final long sec;
        private final long val;

        public Pair(long fir, long sec) {
            this.fir = fir;
            this.sec = sec;
            this.val = fir * fir * fir + sec * sec * sec;
        }
    }

    public static long[] findAllTaxicabNumLessSpace(int n) {
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            if (a.val == b.val) return 0;
            return a.val < b.val ? -1 : 1;
        });
        for (int i = 1; i < n; i++)
            pq.offer(new Pair(i, i));
        long prev = 0;
        int sum = 0;
        List<Long> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            if (cur.val == prev) {
                sum++;
                if (sum == 2) result.add(prev);
            } else {
                prev = cur.val;
                sum = 1;
            }
            if (cur.sec < n - 1) {
                pq.offer(new Pair(cur.fir, cur.sec + 1));
            }
        }
        return convertToLongArray(result);
    }

}
