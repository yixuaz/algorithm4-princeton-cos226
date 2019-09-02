package part2.week2.mst;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.UF;

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
