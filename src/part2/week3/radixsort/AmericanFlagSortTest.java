package part2.week3.radixsort;

import commonutil.Shuffler;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class AmericanFlagSortTest {
    @Test(timeout = 1000)
    public void randomTest() {
        int N = 1000;
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            if (i % 9 == 0) N <<= 1;
            int R = i + 2;
            int[] set = new int[R];
            int[] arr = Shuffler.getRandomArray(N,0, R - 1);
            for (int j : arr) set[j]++;
            AmericanFlagSort.solve(arr, R);
            set[arr[0]]--;
            for (int j = 1; j < arr.length; j++) {
                Assert.assertTrue(arr[j - 1] <= arr[j]);
                set[arr[j]]--;
            }
            for (int j = 0; j < R; j++) {
                Assert.assertEquals(0, set[j]);
            }
        }
    }

}