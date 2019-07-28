package week3.quicksort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static commonutil.Shuffler.shuffle;
import static org.junit.Assert.*;

public class DecimalDominantsTest {

    @Test
    public void basicTest() {
        int[] A = {2,3,4,5,6,7,8,9,0,2};
        Assert.assertArrayEquals(new int[]{2},DecimalDominants.getEleOccurMoreThanOneOfTen(A));
    }

    @Test
    public void basicTest2() {
        int[] A = {2,3,4,5,6,7,8,9,0,1};
        Assert.assertArrayEquals(new int[]{},DecimalDominants.getEleOccurMoreThanOneOfTen(A));
    }

    @Test
    public void basicTest3() {
        int[] A = {2,2,4,5,7,7,8,9,1,1,0,-1,-2,-3,-3,-5,-6,-7,-8};
        int[] res = DecimalDominants.getEleOccurMoreThanOneOfTen(A);
        Arrays.sort(res);
        Assert.assertArrayEquals(new int[]{-3,1,2,7},res);
    }

    @Test(timeout = 200)
    public void randomTest() {
        Random r = new Random();
        for (int w = 0; w < 20; w++) {
            int N = 100000;
            int[] A = new int[N];
            for (int i = 0; i < N; i++) {
                A[i] = i;
            }
            int k = r.nextInt(10), idx = 0;
            int[] expect = new int[k];
            for (int i = 0; i < k; i++) {
                expect[i] = r.nextInt(N);
                for (int j = 0; j < N / 10 + 1; j++) {
                    A[idx++] = expect[i];
                }
            }
            shuffle(A);
            Arrays.sort(expect);
            int[] res = DecimalDominants.getEleOccurMoreThanOneOfTen(A);
            Arrays.sort(res);
            System.out.println(Arrays.toString(expect));
            Assert.assertArrayEquals(expect, res);
        }
    }
}