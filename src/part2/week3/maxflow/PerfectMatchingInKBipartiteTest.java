package part2.week3.maxflow;

import org.junit.Assert;
import org.junit.Test;

public class PerfectMatchingInKBipartiteTest {

    @Test
    public void basicTest() {
        for (int n = 4; n <= 16; n += 2) {
            for (int k = 1; k <= n / 2; k++) {
                int[][] relationship = new int[n][n];
                if (!fullfill(relationship, k, n)) continue;
                Assert.assertEquals(true, PerfectMatchingInKBipartite.solve(relationship, k));
                System.out.println("pass " + n + "," + k);
            }
        }
    }

    private boolean fullfill(int[][] relationship, int k, int n) {
        return dfs(relationship, k, n, 0, 0, n / 2, new int[n]);
    }

    private boolean dfs(int[][] relationship, int k, int n, int curIdx, int curK, int lastPt, int[] knowHer) {
        if (curIdx == n / 2) return true;
        if (curK == k) {
            if (dfs(relationship, k, n, curIdx + 1, 0, n / 2, knowHer)) return true;
        } else {
            for (int i = lastPt; i < n; i++) {
                if (knowHer[i] == k) continue;
                knowHer[i]++;
                relationship[curIdx][i]++;
                if (dfs(relationship, k, n, curIdx, curK + 1, i + 1, knowHer)) return true;
                knowHer[i]--;
                relationship[curIdx][i]--;
            }
        }
        return false;
    }

}