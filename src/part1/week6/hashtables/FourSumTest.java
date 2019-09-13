package part1.week6.hashtables;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static commonutil.Shuffler.getRandomArray;

public class FourSumTest {
    Random r = new Random();

    @Test
    public void correctTest() {
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            Set<Boolean> s = new HashSet<>();
            int k = 1000;
            while (s.size() < 2) {
                int N = r.nextInt(k);
                int[] a = getRandomArray(N, -k * k, k * k);
                boolean res = FourSum.solveInN2(a);
                s.add(res);
                Assert.assertEquals(expect(a), res);
                idx++;
            }
        }
        System.out.println("test " + idx);
    }

    public boolean expect(int[] nums) {
        int target = 0;
        Arrays.sort(nums);
        int l = nums.length;
        if (l < 4 || 4 * nums[0] > target) return false;
        for (int i = 0; i < l - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            if (3 * nums[i + 1] > target - nums[i]) return false;
            for (int j = i + 1; j < l - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int need = target - nums[i] - nums[j];
                if (2 * nums[j + 1] > need) break;
                if (2 * nums[l - 1] < need) continue;
                int s = j + 1, e = l - 1;
                while (s < e) {
                    int tar = nums[s] + nums[e];
                    if (tar < need) {
                        s++;
                    } else if (tar > need) {
                        e--;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Test
    public void performanceTest() {
        int N = 1000;
        int[] a = getRandomArray(N, 1, N * N);
        long st = System.nanoTime();
        Assert.assertEquals(false, FourSum.solveInN2(a));
        Assert.assertTrue(System.nanoTime() - st < 250l * 1_000_000);
        N <<= 1;
        a = getRandomArray(N, 1, N * N);
        st = System.nanoTime();
        Assert.assertEquals(false, FourSum.solveInN2(a));
        Assert.assertTrue(System.nanoTime() - st < 1000l * 1_000_000);
    }

}