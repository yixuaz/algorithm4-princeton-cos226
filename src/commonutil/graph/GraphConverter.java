package commonutil.graph;

import edu.princeton.cs.algs4.Digraph;

import java.util.HashSet;
import java.util.Set;

public class GraphConverter {
    public static Set<Integer>[] convert(Digraph graph) {
        int n = graph.V();
        Set<Integer>[] res = new Set[n];
        for (int i = 0; i < n; i++) {
            res[i] = new HashSet<>();
            for (int k : graph.adj(i)) {
                res[i].add(k);
            }
        }
        return res;
    }
}
