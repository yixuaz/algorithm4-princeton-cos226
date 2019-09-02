package part2.week6.reduction;

import commonutil.Shuffler;
import edu.princeton.cs.algs4.ThreeSum;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ReductionTest {

    @Test
    public void basicTest3sum() {
        assertTrue(Reduction.threeSum(new int[]{2, 1, -3}));
        assertFalse(Reduction.threeSum(new int[]{2, 1, -2, -2}));
        assertTrue(Reduction.threeSum(new int[]{-7, 2, 1, -3, 6}));
        assertFalse(Reduction.threeSum(new int[]{-9, 2, 11, -3, 5, 88}));
    }

    @Test
    public void randomTest3sum() {
        int N = 5;
        for (int i = 0; i < 700; i++) {
            int m = i % 100;
            if (m == 99) N <<= 1;
            int[] input = Shuffler.getRandomArray(N, -m * m, m * m);
            assertEquals(Arrays.toString(input), ThreeSum.count(input) != 0, Reduction.threeSum(input));
        }
    }

    @Test
    public void basicTest3Linear() {
        assertTrue(Reduction.threeLinear(new int[]{16, 8, 3}));
        assertFalse(Reduction.threeLinear(new int[]{2, 1, -2, -2}));
        assertTrue(Reduction.threeLinear(new int[]{-7, 16, 8, 3, 6}));
        assertTrue(Reduction.threeLinear(new int[]{-9, 2, 11, -3, 5, 87}));
        assertFalse(Reduction.threeLinear(new int[]{4, 2, -4, 4, -4}));
        assertFalse(Reduction.threeLinear(new int[]{1,-1}));
    }

    @Test
    public void randomTest3Linear() {
        int N = 5;
        for (int i = 0; i < 1000; i++) {
            int m = i % 100;
            if (m == 99) N <<= 1;
            int[] input = Shuffler.getRandomArray(N, -m * m, m * m);
            assertEquals(Arrays.toString(input), threeLinear(input), Reduction.threeLinear(input));
        }
    }

    public static boolean threeLinear(int[] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                for (int k = 0; k < A.length; k++) {
                    if (A[i] + A[j] == 8 * A[k]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}