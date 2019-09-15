package part2.week2.mst.extracredit;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EagerPrim implements MST {
    private static final Edge MIN_EDGE = new Edge(0, 0, 0D);
    private static final Edge MAX_EDGE = new Edge(0, 0, Double.POSITIVE_INFINITY);
    private final List<Edge> edges;
    private final double weight;

    public EagerPrim(EdgeWeightedGraph G) {
        edges = new ArrayList<>();
        int v = G.V();
        IndexMinPQ<Edge> minPq = new IndexMinPQ<>(v);
        double curWeight = 0;
        minPq.insert(0, MIN_EDGE);
        for (int i = 1; i < v; i++) {
            minPq.insert(i, MAX_EDGE);
        }
        boolean[] seen = new boolean[v];
        while (!minPq.isEmpty()) {
            Edge curMin = minPq.minKey();
            int curV = minPq.delMin();
            seen[curV] = true;
            if (curMin != MIN_EDGE) {
                edges.add(curMin);
                curWeight += curMin.weight();
            }
            scan(minPq, G, seen, curV);
        }
        weight = curWeight;
    }

    private void scan(IndexMinPQ<Edge> minPq, EdgeWeightedGraph G, boolean[] seen, int v) {
        for (Edge e : G.adj(v)) {
            int other = e.other(v);
            if (seen[other]) {
                continue;
            }
            if (e.compareTo(minPq.keyOf(other)) < 0) {
                minPq.decreaseKey(other, e);
            }
        }
    }

    @Override
    public Iterable<Edge> edges() {
        return Collections.unmodifiableList(edges);
    }

    @Override
    public double weight() {
        return weight;
    }
}
