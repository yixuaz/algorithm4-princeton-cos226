package part2.week2.mst;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.UF;

/**
 * we can consider all of the edge which weight is strictly less than that of e, if these graph can connect
 * all of the vertex. then if we add this e, it must have a cycle.
 * so if it can not connect all of the vertex, this edge is in mst or not depend on if it could connect two point
 * which not connect before.
 * <p>
 * so achieve above, we have two solution.
 * 1. use union find to simulate the situation above.
 * 2. use dfs, start from one vertex from this edge,  then dfs, only choose the edge which weight smaller than this edge,
 * check dfs can achieve another vertex from this edge.
 * if can achieve, this edge should not in mst,or it will create a circle, and this edge is the largest weight.
 */
public class IsEdgeInMST {
    public static boolean solve(EdgeWeightedGraph graph, Edge edge) {
        return dfs(graph, edge.either(), edge.other(edge.either()), edge.weight(), new boolean[graph.V()]);
    }

    private static boolean dfs(EdgeWeightedGraph graph, int v, int tar, double weight, boolean[] seen) {
        seen[v] = true;
        for (Edge edge : graph.adj(v)) {
            int w = edge.other(v);
            if (Double.compare(edge.weight(), weight) >= 0 || seen[w]) {
                continue;
            }
            if (w == tar) return false;
            if (!dfs(graph, w, tar, weight, seen)) return false;
        }
        return true;

    }

    public static boolean solve2(EdgeWeightedGraph graph, Edge edge) {
        UF uf = new UF(graph.V());
        for (Edge e : graph.edges()) {
            if (Double.compare(e.weight(), edge.weight()) < 0) {
                uf.union(e.either(), e.other(e.either()));
            }
        }
        return !uf.connected(edge.either(), edge.other(edge.either()));
    }
}
