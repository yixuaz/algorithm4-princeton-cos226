package commonutil.graph;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.GraphGenerator;
import edu.princeton.cs.algs4.UF;

import java.util.Random;

public class WeightedGraphBuilder {

    public static EdgeWeightedGraph buildRandomConnectedGraph(int N) {
        Random r = new Random();
        while (true) {
            int v = 2 + r.nextInt(N), e = Math.max(v - 1, r.nextInt(v * (v - 1) / 2));
            Graph raw = GraphGenerator.simple(v, e);
            EdgeWeightedGraph ret = new EdgeWeightedGraph(v);
            UF uf = new UF(raw.V());
            for (int i = 0; i < v; i++) {
                for (int j : raw.adj(i)) {
                    double weight = N * Math.random();
                    ret.addEdge(new edu.princeton.cs.algs4.Edge(i, j, weight));
                    uf.union(i, j);
                }
            }
            if (uf.count() == 1) {
                return ret;
            }
        }
    }
    public static EdgeWeightedGraph buildRandomGraph(int N) {
        Random r = new Random();
        int v = 2 + r.nextInt(N), e = 1 + r.nextInt(v * (v - 1) / 2);
        Graph raw = GraphGenerator.simple(v, e);
        EdgeWeightedGraph ret = new EdgeWeightedGraph(v);
        for (int i = 0; i < v; i++) {
            for (int j : raw.adj(i)) {
                double weight = N * Math.random();
                ret.addEdge(new Edge(i, j, weight));
            }
        }
        return ret;
    }
}
