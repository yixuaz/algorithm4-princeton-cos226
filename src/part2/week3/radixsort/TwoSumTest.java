package part2.week3.radixsort;

import commonutil.Shuffler;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TwoSumTest {
    @Test
    public void overflowTest() {
        boolean test = TwoSum.solve(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}, -2);
        assertEquals(false, test);
        assertEquals(true, TwoSum.solve(new int[]{-1, Integer.MIN_VALUE, Integer.MAX_VALUE}, -1));
    }

    @Test
    public void noAnsTest() {
        boolean test = TwoSum.solve(new int[]{}, -2);
        assertEquals(false, test);
        test = TwoSum.solve(new int[]{-2}, -2);
        assertEquals(false, test);
        test = TwoSum.solve(new int[]{-3, -4, -5}, -2);
        assertEquals(false, test);
    }

    @Test
    public void haveAnsTest() {
        boolean test = TwoSum.solve(new int[]{0, 0}, 0);
        assertEquals(true, test);
        test = TwoSum.solve(new int[]{1, 1, 3, 4}, 2);
        assertEquals(true, test);
        test = TwoSum.solve(new int[]{-3, -4, -5}, -8);
        assertEquals(true, test);
    }

    @Test
    public void randomTest() {
        int N = 10000;
        for (int i = 0; i < 500; i++) {
            if (i % 100 == 99) N <<= 1;
            int[] arr = Shuffler.getRandomArray(N, -N, N);
            Assert.assertEquals(expect(arr, 0), TwoSum.solve(arr, 0));
            System.out.println("pass " + N);
        }
    }

    private boolean expect(int[] arr, int tar) {
        Set<Integer> map = new HashSet<>();
        for (int i : arr) {
            if (map.contains(i)) return true;
            map.add(tar - i);
        }
        return false;
    }

}