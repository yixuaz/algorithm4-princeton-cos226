package part2.week3.maxflow;

import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.List;

/**
 * this problem could be solved by two way.
 * 1. binary search
 * design a linear-time subroutine that takes a real-number T and determines if there is a path from s to t
 * of bottleneck capacity greater than or equal to T.
 * because the bottleneck is in the range of min edge and max edge. so we could use binary search on it.
 * the check method spend O(E) time, we just iterate from s go every possible point if there is
 * an edge meet the weight, if we found we could enter destination, we know this weight is valid,
 * and we can try bigger weight. ( we want to find the max bottleneck)
 * <p>
 * this solution could be seen in solution project.
 * <p>
 * 2. Dijkstra
 * the relax operation is to fatter the path as much as possible. because we need to find the fattest path.
 * every time we pop the Max Position, to run dijktra algorithm.
 * <p>
 * this solution could be seen in test
 */
public class FattestPath {

    public static int solve(EdgeWeightedDigraph G, List<DirectedEdge> path, int s, int t) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (DirectedEdge e : G.edges()) {
            min = Math.min(e.intWeight(), min);
            max = Math.max(e.intWeight(), max);
        }
        int st = min, ed = max, V = G.V();
        while (st <= ed) {
            int mid = st + (ed - st) / 2;
            if (connected(G, s, t, mid, new ArrayList<>(), new boolean[V])) {
                st = mid + 1;
            } else {
                ed = mid - 1;
            }
        }
        if (st - 1 < min) return Integer.MIN_VALUE;
        connected(G, s, t, st - 1, path, new boolean[V]);
        return st - 1;
    }

    private static boolean connected(EdgeWeightedDigraph G, int s, int t, int minAllowWeight, List<DirectedEdge> path,
                                     boolean[] seen) {
        if (s == t) {
            return true;
        }
        seen[s] = true;
        for (DirectedEdge e : G.adj(s)) {
            if (seen[e.to()]) continue;
            if (e.intWeight() < minAllowWeight) continue;
            seen[e.to()] = true;
            path.add(e);
            if (connected(G, e.to(), t, minAllowWeight, path, seen)) return true;
            path.remove(path.size() - 1);
        }
        return false;
    }
}
