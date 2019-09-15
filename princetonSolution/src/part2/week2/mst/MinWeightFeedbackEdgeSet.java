package part2.week2.mst;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMaxPQ;
import edu.princeton.cs.algs4.PrimMST;

import java.util.HashSet;
import java.util.Set;

/**
 * first, we need to consider, this graph may not connected directly,
 * so we need run completely PRIM to handle mst forest.
 * then we need to use IndexMaxPQ to produce max spanning tree, because we need feedback edge set with minimum weight.
 * after get max spanning tree, all of the edges which not in it should be in the result.
 */
public class MinWeightFeedbackEdgeSet {
    public static Set<Edge> solve(EdgeWeightedGraph graph) {
        int V = graph.V();
        IndexMaxPQ<Edge> pq = new IndexMaxPQ<>(V);
        Set<Edge> mst = new HashSet<>();
        boolean[] seen = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (seen[i]) continue;
            seen[i] = true;
            scan(i, graph, pq, seen);
            while (!pq.isEmpty()) {
                mst.add(pq.maxKey());
                scan(pq.delMax(), graph, pq, seen);
            }
        }
        Set<Edge> ans = new HashSet<>();
        for (Edge e : graph.edges()) {
            if (mst.contains(e)) continue;
            ans.add(e);
        }
        return ans;
    }

    private static void scan(int i, EdgeWeightedGraph graph, IndexMaxPQ<Edge> pq, boolean[] seen) {
        for (Edge e : graph.adj(i)) {
            int other = e.other(i);
            if (!seen[other]) {
                seen[other] = true;
                pq.insert(other, e);
            } else if (pq.contains(other) && pq.keyOf(other).compareTo(e) < 0) {
                pq.increaseKey(other, e);
            }
        }
    }
}
