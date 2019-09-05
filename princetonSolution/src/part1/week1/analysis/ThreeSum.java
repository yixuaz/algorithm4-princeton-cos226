package part1.week1.analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Solution:
 * as we known, if we solve the 2-sum when the input is sorted. we can use two point which one is
 * from the beginning, and another is from the ending. check the sum of the two pointer is bigger or
 * smaller than the target to decide move which pointer. which time complexity is O (n)
 * the 3 sum is same, we can use one point from 0 - n, then make this problem in each for loop as a two sum problem.
 */
public class ThreeSum {
    public static int solve(int[] nums) {
        int cnt = 0, n = nums.length;
        Arrays.sort(nums);
        if (n == 0 || 3 * nums[0] > 0 || 3 * nums[n - 1] < 0) return cnt;
        for (int i = 0; i < n - 2; i++) {
            if (2 * nums[i + 1] + nums[i] > 0) return cnt;
            if (2 * nums[n - 1] + nums[i] < 0) continue;
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int need = -nums[i] - nums[j];
                cnt += map.getOrDefault(need, 0);
                map.put(nums[j], map.getOrDefault(nums[j], 0) + 1);
            }
        }
        return cnt;
    }
}
