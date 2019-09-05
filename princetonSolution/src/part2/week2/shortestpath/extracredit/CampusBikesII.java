package part2.week2.shortestpath.extracredit;

import edu.princeton.cs.algs4.BellmanFordSP;
import edu.princeton.cs.algs4.StopwatchCPU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.com/problems/campus-bikes-ii/
 * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
 *
 * We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their assigned bike is minimized.
 *
 * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
 *
 * Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.
 *
 * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
 * Output: 6
 * Explanation:
 * We assign bike 0 to worker 0, bike 1 to worker 1. The Manhattan distance of both assignments is 3, so the output is 6.
 *
 * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
 * Output: 4
 * Explanation:
 * We first assign bike 0 to worker 0, then assign bike 1 to worker 1 or worker 2, bike 2 to worker 2 or worker 1. Both assignments lead to sum of the Manhattan distances as 4.
 */
public class CampusBikesII {
    // this is a min cost max flow problem
    private class Edge {
        private int from, to, residual, cost;
        public Edge(int from, int to, int residual, int cost) {
            this.from = from;
            this.to = to;
            this.residual = residual;
            this.cost = cost;
        }
    }
    private List<Edge> edges = new ArrayList<>();
    private List<Integer>[] graph;
    private int src, tar, maxFlow, minCost;
    private boolean[] onQueue;
    private int[] distTo, flow, edgeTo;
    private boolean shortestPathFasterAlgorithm() {
        // queue improvement bellman ford
        Arrays.fill(distTo, Integer.MAX_VALUE);
        distTo[src] = 0;
        edgeTo[src] = 0;
        flow[src] = Integer.MAX_VALUE;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(src);
        onQueue[src] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            onQueue[v] = false;
            relax(v, queue);
        }
        if (distTo[tar] == Integer.MAX_VALUE) return false;

        // find augmenting path, reverse update residual
        maxFlow += flow[tar];
        minCost += distTo[tar] * flow[tar];
        int nowAt = tar;
        while (nowAt != src) {
            edges.get(edgeTo[nowAt]).residual -= flow[tar];
            edges.get(edgeTo[nowAt] ^ 1).residual += flow[tar];
            nowAt = edges.get(edgeTo[nowAt]).from;
        }
        return true;
    }

    private void relax(int v, Queue<Integer> queue) {
        for (int edgeIdx : graph[v]) {
            Edge e = edges.get(edgeIdx);
            int w = e.to;
            if (e.residual > 0 && distTo[w] > distTo[v] + e.cost) {
                distTo[w] = distTo[v] + e.cost;
                edgeTo[w] = edgeIdx;
                flow[w] = Math.min(flow[v], e.residual);
                if (!onQueue[w]) {
                    queue.offer(w);
                    onQueue[w] = true;
                }
            }
        }
    }

    private void addEdge(Edge forward) {
        graph[forward.from].add(edges.size());
        edges.add(forward);
        graph[forward.to].add(edges.size());
        edges.add(new Edge(forward.to, forward.from, 0, -forward.cost));
    }

    public int assignBikes(int[][] workers, int[][] bikes) {
        int m = workers.length, n = bikes.length, maxN = m + n + 2;
        init(maxN);
        for (int i = 0; i < m; i++) {
            int[] worker = workers[i];
            for (int j = 0; j < n; j++) {
                int[] bike = bikes[j];
                int cost = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
                addEdge(new Edge(i, m + j, 1, cost));
            }
        }
        for (int i = 0; i < m; i++) {
            addEdge(new Edge(src, i, 1, 0));
        }
        for (int i = 0; i < n; i++) {
            addEdge(new Edge(m + i, tar, 1, 0));
        }
        while (shortestPathFasterAlgorithm()) ;
        return minCost;
    }
    private void init(int maxN) {
        distTo = new int[maxN];
        flow = new int[maxN];
        edgeTo = new int[maxN];
        onQueue = new boolean[maxN];
        graph = new List[maxN];
        for (int i = 0; i < maxN; i++)
            graph[i] = new ArrayList<>();
        maxFlow = 0;
        minCost = 0;
        src = maxN - 2;
        tar = maxN - 1;
    }

}
