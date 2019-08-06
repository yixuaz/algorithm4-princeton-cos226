package week5.geoapp.segmenttree;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class SegmentTreeTest {

    @Test
    public void testRandomQueryAndSet() {
        Random r = new Random();
        int N = 500;
        Integer[] in = new Integer[N];
        for (int i = 0; i < N; i++) in[i] = r.nextInt(N) * 2 - N;
        SegmentTree<Integer> sumTree = new SegmentTree<>(in, (a, b) -> a + b, null, null);
        checkSum(in, sumTree);
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < N / 2; i++) {
                int k = r.nextInt(N);
                int val = r.nextInt(N) * 2 - N;
                in[k] = val;
                sumTree.set(k, val);
            }
            checkSum(in, sumTree);
        }
    }

    @Test
    public void testRandomQueryAndRangeSet() {
        Random r = new Random();
        int N = 500;
        Integer[] in = new Integer[N];
        for (int i = 0; i < N; i++) in[i] = r.nextInt(N) * 2 - N;
        SegmentTree<Integer> sumTree = new SegmentTree<>(in, (a, b) -> a + b, (a, b) -> b,
                (ori, extra, st, ed) -> (ed - st + 1) * extra);

        checkSum(in, sumTree);
        for (int j = 0; j < 5; j++) {
            int k1 = r.nextInt(N), k2 = r.nextInt(N);
            int st = Math.min(k1, k2), ed = Math.max(k1, k2);
            int val = r.nextInt(N) * 2 - N;
            for (int k = st; k <= ed; k++)
                in[k] = val;
            sumTree.updateRange(st, ed, val);
            checkSum(in, sumTree);
        }
    }

    @Test
    public void testRandomQueryAndRangeAddDelta() {
        Random r = new Random();
        int N = 500;
        Integer[] in = new Integer[N];
        for (int i = 0; i < N; i++) in[i] = r.nextInt(N) * 2 - N;
        SegmentTree<Integer> sumTree = new SegmentTree<>(in, (a, b) -> a + b, (a, b) -> {
            if (a == null) a = 0;
            return a + b;
        }, (ori, extra, st, ed) -> ori + (ed - st + 1) * extra);
        checkSum(in, sumTree);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 3; i++) {
                int k1 = r.nextInt(N), k2 = r.nextInt(N);
                int st = Math.min(k1, k2), ed = Math.max(k1, k2);
                int val = r.nextInt(N) * 2 - N;
                for (int k = st; k <= ed; k++)
                    in[k] += val;
                sumTree.updateRange(st, ed, val);
            }
            checkSum(in, sumTree);
        }
    }

    private void checkSum(Integer[] in, SegmentTree<Integer> tree) {
        for (int i = 0; i < in.length; i++) {
            int sum = 0;
            for (int j = i; j < in.length; j++) {
                sum += in[j];
                Assert.assertEquals(i +"," + j, sum, tree.query(i, j).intValue());
            }
        }
        System.out.println("pass");
    }

    @Test
    public void testRandomQueryAndSet2() {
        Random r = new Random();
        int N = 500;
        Integer[] in = new Integer[N];
        for (int i = 0; i < N; i++) in[i] = r.nextInt(N) * 2 - N;
        SegmentTree<Integer> maxTree = new SegmentTree<>(in, (a, b) -> Math.max(a, b), null, null);
        checkMax(in, maxTree);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < N / 5; i++) {
                int k = r.nextInt(N);
                int val = r.nextInt(N) * 2 - N;
                in[k] = val;
                maxTree.set(k, val);
            }
            checkMax(in, maxTree);
        }
    }

    private void checkMax(Integer[] in, SegmentTree<Integer> tree) {
        for (int i = 0; i < in.length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < in.length; j++) {
                max = Math.max(max, in[j]);
                Assert.assertEquals(i +"," + j, max, tree.query(i, j).intValue());
            }
        }
        System.out.println("pass");
    }

    @Test
    public void testRandomQueryAndRangeSet2() {
        Random r = new Random();
        int N = 500;
        Integer[] in = new Integer[N];
        for (int i = 0; i < N; i++) in[i] = r.nextInt(N) * 2 - N;
        SegmentTree<Integer> maxTree = new SegmentTree<>(in, (a, b) -> Math.max(a, b), (a, b) -> b,
                (ori, extra, st, ed) -> extra);

        checkMax(in, maxTree);
        for (int j = 0; j < 5; j++) {
            int k1 = r.nextInt(N), k2 = r.nextInt(N);
            int st = Math.min(k1, k2), ed = Math.max(k1, k2);
            int val = r.nextInt(N) * 2 - N;
            for (int k = st; k <= ed; k++)
                in[k] = val;
            maxTree.updateRange(st, ed, val);
            checkMax(in, maxTree);
        }
    }

    @Test
    public void testRandomQueryAndRangeAddDelta2() {
        Random r = new Random();
        int N = 500;
        Integer[] in = new Integer[N];
        for (int i = 0; i < N; i++) in[i] = r.nextInt(N) * 2 - N;
        SegmentTree<Integer> maxTree = new SegmentTree<>(in, (a, b) -> Math.max(a, b), (a, b) -> {
            if (a == null) a = 0;
            return a + b;
        }, (ori, extra, st, ed) -> ori + extra);
        checkMax(in, maxTree);
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 3; i++) {
                int k1 = r.nextInt(N), k2 = r.nextInt(N);
                int st = Math.min(k1, k2), ed = Math.max(k1, k2);
                int val = r.nextInt(N) * 2 - N;
                for (int k = st; k <= ed; k++)
                    in[k] += val;
                maxTree.updateRange(st, ed, val);
            }
            checkMax(in, maxTree);
        }
    }

}