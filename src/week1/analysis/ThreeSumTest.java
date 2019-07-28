package week1.analysis;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ThreeSumTest {
    @Test
    public void basicTest1() {
        int[] in = {0,0,0,0};
        Assert.assertEquals(ThreeSum.solve(in),4);
    }
    @Test
    public void basicTest2() {
        int[] in = {1,2,3,4,5,6};
        Assert.assertEquals(ThreeSum.solve(in),0);
    }
    @Test
    public void basicTest3() {
        int[] in = {-1,1};
        Assert.assertEquals(ThreeSum.solve(in),0);
        int[] in2 = {};
        Assert.assertEquals(ThreeSum.solve(in2),0);
    }
    @Test
    public void basicTest4() {
        int[] in = {-1,-1,1,1,0};
        Assert.assertEquals(ThreeSum.solve(in),4);
    }
    @Test(timeout = 1000)
    public void randomTest() {
        Random r = new Random();
        for (int idx = 0; idx < 10; idx++) {
            int[] in = new int[700];
            int base = 200 / ((1 + idx) * (1 + idx));
            for (int i = 0; i < in.length; i++) {
                in[i] = -base + r.nextInt(2 * base);
            }
            Assert.assertEquals(expect(in), ThreeSum.solve(in));
        }
    }
    private int expect(int[] nums) {
        int n = nums.length, cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0)
                        cnt++;
                }
            }
        }
        return cnt;
    }

}