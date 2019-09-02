package part1.week1.analysis;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SearchBitonicArrayTest {
    @Test
    public void basicTest1() {
        int[] in = {1,3,2};
        for (int i : in) {
            Assert.assertTrue(SearchBitonicArray.solve3lgn(in, i) > 0);
            Assert.assertTrue(SearchBitonicArray.solve2lgn(in, i) > 0);
        }
        Assert.assertTrue(SearchBitonicArray.solve3lgn(in,4) < 0);
        Assert.assertTrue(SearchBitonicArray.solve3lgn(in,0) < 0);
        Assert.assertTrue(SearchBitonicArray.solve2lgn(in,4) < 0);
        Assert.assertTrue(SearchBitonicArray.solve2lgn(in,0) < 0);
    }
    @Test
    public void basicTest2() {
        int[] in = {1,2,3,4,5,6,4,3,2};
        for (int i : in) {
            Assert.assertTrue(SearchBitonicArray.solve3lgn(in, i) > 0);
            Assert.assertTrue(SearchBitonicArray.solve2lgn(in, i) > 0);
        }
        for (int i = 7; i < 12; i++) {
            Assert.assertTrue(SearchBitonicArray.solve3lgn(in, i) < 0);
            Assert.assertTrue(SearchBitonicArray.solve2lgn(in, i) < 0);
            Assert.assertTrue(SearchBitonicArray.solve3lgn(in, -i) < 0);
            Assert.assertTrue(SearchBitonicArray.solve2lgn(in, -i) < 0);
        }
    }
    @Test
    public void randomTest3() {
        int[] arr = new int[100000];
        Random r = new Random();
        Set<Integer> set = new HashSet<>();
        int peak = r.nextInt(100000);
        set.add(0);
        for (int i = 1; i < peak; i++) {
            arr[i] = arr[i - 1] + 1 + r.nextInt(10);
            set.add(arr[i]);
        }
        for (int i = peak; i < arr.length; i++) {
            int base = Math.max(1, arr[i - 1] / (arr.length - i));
            arr[i] = arr[i - 1] - 1 - r.nextInt(base);
            set.add(arr[i]);
        }
        int cnt2 = 0, cnt3 = 0;
        for (int i = 0; i < 1000000; i++) {
            int fir = SearchBitonicArray.solve3lgn(arr, i);
            int sec = SearchBitonicArray.solve2lgn(arr, i);
            Assert.assertTrue(fir > 0 == set.contains(i));
            Assert.assertTrue(sec > 0 == set.contains(i));
            cnt2 += sec;
            cnt3 += fir;
        }
        Assert.assertTrue(Math.abs(1.5 - (double)cnt3/cnt2) < 0.15);
    }

}