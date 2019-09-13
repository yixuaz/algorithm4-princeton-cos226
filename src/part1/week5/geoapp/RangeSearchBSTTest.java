package part1.week5.geoapp;

import commonutil.Shuffler;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RangeSearchBSTTest {
    private Random random = new Random();

    @Test
    public void testSizeBasic() {
        RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
        Assert.assertEquals(0, test.size(Integer.MIN_VALUE, Integer.MAX_VALUE));
        test.put(1, 1);
        Assert.assertEquals(1, test.size(Integer.MIN_VALUE, Integer.MAX_VALUE));
        Assert.assertEquals(1, test.size(Integer.MIN_VALUE, 1));
        Assert.assertEquals(1, test.size(1, Integer.MAX_VALUE));
        Assert.assertEquals(1, test.size(1, 1));
        Assert.assertEquals(0, test.size(0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput1() {
        RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
        test.size(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput2() {
        RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
        test.size(null, Integer.MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput3() {
        RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
        test.size(0, null);

    }

    @Test
    public void testSizeRandom() {
        int N = 10;
        for (int i = 0; i < 10; i++, N <<= 1) {
            RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
            int[] testData = Shuffler.getRandomArray(N, -N, N);
            for (int data : testData) test.put(data, data);
            Arrays.sort(testData);
            for (int j = 0; j < 100; j++) {
                int a = -N + random.nextInt(2 * N), b = -N + random.nextInt(2 * N);
                int hi = Math.max(a, b), lo = Math.min(a, b);
                Assert.assertEquals(expectSize(testData, lo, hi), test.size(lo, hi));
            }
        }
    }

    private int expectSize(int[] testData, int lo, int hi) {
        int cnt = 0;
        for (int i = 0; i < testData.length; i++) {
            if (i > 0 && testData[i] == testData[i - 1]) continue;
            if (testData[i] >= lo && testData[i] <= hi) {
                cnt++;
            }
        }
        return cnt;
    }

    @Test
    public void testSearchBasic() {
        RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
        Assert.assertTrue(test.search(Integer.MIN_VALUE, Integer.MAX_VALUE).isEmpty());
        test.put(1, 1);
        Assert.assertEquals(1, test.search(Integer.MIN_VALUE, Integer.MAX_VALUE).size());
        Assert.assertEquals(1, test.search(Integer.MIN_VALUE, 1).size());
        Assert.assertEquals(1, test.search(1, Integer.MAX_VALUE).size());
        Assert.assertEquals(1, test.search(1, 1).size());
        Assert.assertEquals(0, test.search(0, 0).size());
    }

    @Test
    public void testSearchRandom() {
        int N = 10;
        for (int i = 0; i < 10; i++, N <<= 1) {
            RangeSearchBST<Integer, Integer> test = new RangeSearchBST<>();
            int[] testData = Shuffler.getRandomArray(N, -N, N);
            for (int data : testData) test.put(data, data);
            Arrays.sort(testData);
            for (int j = 0; j < 100; j++) {
                int a = -N + random.nextInt(2 * N), b = -N + random.nextInt(2 * N);
                int hi = Math.max(a, b), lo = Math.min(a, b);
                Assert.assertEquals(expectSearch(testData, lo, hi), test.search(lo, hi));
            }
        }
    }

    private List<Integer> expectSearch(int[] testData, int lo, int hi) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < testData.length; i++) {
            if (i > 0 && testData[i] == testData[i - 1]) continue;
            if (testData[i] >= lo && testData[i] <= hi) {
                result.add(testData[i]);
            }
        }
        return result;
    }
}