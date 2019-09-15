package part2.week2.shortestpath.extracredit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ArbitrageDetection {
    public static boolean solve(double[][] exchangeRates) {
        int v = exchangeRates.length;
        if (v <= 1) return false;
        double[][] graph = new double[v][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++)
                graph[i][j] = -Math.log(exchangeRates[i][j]);
        }
        double[] distTo = new double[v];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] onQueue = new boolean[v];
        int[] updateCnt = new int[v];
        queue.offer(0);
        onQueue[0] = true;
        updateCnt[0] = 1;
        while (!queue.isEmpty()) {
            int idx = queue.poll();
            onQueue[idx] = false;
            if (relaxReturnFoundNegCycle(graph, idx, distTo, queue, onQueue, updateCnt))
                return true;
        }
        return false;
    }

    private static boolean relaxReturnFoundNegCycle(
            double[][] graph, int idx, double[] distTo,
            Queue<Integer> queue, boolean[] onQueue, int[] updateCnt) {
        for (int i = 0; i < graph.length; i++) {
            double weight = graph[idx][i];
            if (distTo[i] > distTo[idx] + weight) {
                distTo[i] = distTo[idx] + weight;
                if (!onQueue[i]) {
                    queue.offer(i);
                    onQueue[i] = true;
                    if (++updateCnt[i] == graph.length)
                        return true;
                }
            }
        }
        return false;
    }
}
