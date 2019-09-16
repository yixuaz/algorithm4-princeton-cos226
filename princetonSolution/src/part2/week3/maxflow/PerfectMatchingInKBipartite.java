package part2.week3.maxflow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * this question could be used to practice implementing fordfulkerson with int[][] graph.
 * check the max flow is equal to n, (men + women equal to 2 n)
 */
public class PerfectMatchingInKBipartite {
    public static boolean solve(int[][] relationships, int k) {
        int n = relationships.length;
        assert n % 2 == 0;
        int[][] flowGraph = new int[n + 2][n + 2]; // n is source, n + 1 is target
        for (int i = 0; i < n / 2; i++) {
            flowGraph[n][i] = 1;
        }
        for (int i = n / 2; i < n; i++) {
            flowGraph[i][n + 1] = 1;
        }
        for (int i = 0; i < n / 2; i++) {
            int cnt = 0;
            for (int j = n / 2; j < n; j++) {
                if (relationships[i][j] == 0) continue;
                flowGraph[i][j] = Integer.MAX_VALUE;
                cnt++;
            }
            assert cnt == k;
        }
        int[] pre = new int[n + 2];
        int res = fordFulkerson(flowGraph, n, n + 1, pre);
        System.out.println(res);
        return res == n / 2;
    }

    private static int fordFulkerson(int[][] flowGraph, int s, int t, int[] pre) {
        int delta = 0, tot = 0;
        while ((delta = bfs(flowGraph, s, t, pre)) != -1) {
            int p = t;
            while (p != s) {
                flowGraph[pre[p]][p] -= delta;
                flowGraph[p][pre[p]] += delta;
                p = pre[p];
            }
            tot += delta;
        }
        return tot;
    }

    private static int bfs(int[][] flowGraph, int s, int t, int[] pre) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(s);
        Arrays.fill(pre, -1);
        int[] flow = new int[pre.length];
        pre[s] = 0;
        flow[s] = Integer.MAX_VALUE;
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == t) break;
            for (int i = 0; i < pre.length; i++) {
                if (i != cur && flowGraph[cur][i] > 0 && pre[i] == -1) {
                    pre[i] = cur;
                    flow[i] = Math.min(flow[cur], flowGraph[cur][i]);
                    q.offer(i);
                }
            }
        }
        if (pre[t] == -1)
            return -1;
        return flow[t];
    }
}
