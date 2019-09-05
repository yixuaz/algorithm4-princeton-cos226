package part2.week2.shortestpath.extracredit;

import commonutil.Shuffler;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class CampusBikesIITest {
    @Test
    public void basicTest() {
        CampusBikesII campusBikesII = new CampusBikesII();
        Assert.assertEquals(6, campusBikesII.assignBikes(
                new int[][]{{0, 0}, {2, 1}}, new int[][]{{1, 2}, {3, 3}}));
        Assert.assertEquals(4, campusBikesII.assignBikes(
                new int[][]{{0, 0}, {1, 1}, {2, 0}}, new int[][]{{1, 0}, {2, 2}, {2, 1}}));
    }

    @Test
    public void randomTest() {
        CampusBikesII test = new CampusBikesII();
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int workers = 1 + r.nextInt(12);
            int bikes = workers + r.nextInt(13 - workers);
            int[] workerX = Shuffler.getRandomArray(workers, 0, 1000);
            int[] workerY = Shuffler.getRandomArray(workers, 0, 1000);
            int[] bikeX = Shuffler.getRandomArray(bikes, 0, 1000);
            int[] bikeY = Shuffler.getRandomArray(bikes, 0, 1000);
            int[][] inputWorkers = convert(workerX, workerY);
            int[][] inputBikes = convert(bikeX, bikeY);
            Assert.assertEquals(expect(inputWorkers, inputBikes),
                    test.assignBikes(inputWorkers, inputBikes));
        }
    }

    private int expect(int[][] workers, int[][] bikes) {
        return dfs(workers, bikes,0, 0, new int[1 << bikes.length]);
    }

    private int dfs(int[][] workers, int[][] bikes, int workerIdx, int used, int[] dp) {
        if (workerIdx == workers.length) return 0;
        if (dp[used] > 0) return dp[used];
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < bikes.length; i++) {
            if ((used & (1 << i)) != 0) continue;
            int dis = Math.abs(workers[workerIdx][0] - bikes[i][0]) + Math.abs(workers[workerIdx][1] - bikes[i][1]);
            result = Math.min(result, dis + dfs(workers, bikes, workerIdx + 1, used | (1 << i), dp));
        }
        dp[used] = result;
        return result;
    }

    private int[][] convert(int[] workerX, int[] workerY) {
        int[][] res = new int[workerX.length][2];
        for (int i = 0; i < workerX.length; i++) {
            res[i][0] = workerX[i];
            res[i][1] = workerY[i];
        }
        return res;
    }

}