package part1.week3.mergesort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class CountInversionTest {
    private CountInversion countInversion;

    @Before
    public void setup() {
        countInversion = new CountInversion();
    }

    @Test
    public void basicTest1() {
        int[] arr = {3, 2, 1};
        Assert.assertEquals(3, countInversion.solve(arr));
    }

    @Test
    public void basicTest2() {
        int[] arr = {1, 2, 3};
        Assert.assertEquals(0, countInversion.solve(arr));
    }

    @Test
    public void basicTest3() {
        int[] arr = {1, 3, 2, 3, 1};
        Assert.assertEquals(4, countInversion.solve(arr));
    }

    @Test(timeout = 2000)
    public void randomTest() {
        Random r = new Random();
        for (int j = 1; j <= 10; j++) {
            int N = 3000 * j;
            int[] arr = new int[N];
            for (int i = 0; i < arr.length; i++)
                arr[i] = ((-N / 2) + r.nextInt(N)) / (j * j * j);
            int expect = reversePairs(arr);
            Assert.assertEquals(expect, countInversion.solve(arr));
        }
    }

    private int reversePairs(int[] nums) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i])
                    count++;
            }
        }
        return count;
    }

}