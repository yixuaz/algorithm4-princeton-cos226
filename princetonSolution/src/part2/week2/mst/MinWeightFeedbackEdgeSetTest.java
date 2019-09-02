package part2.week2.mst;

import commonutil.graph.WeightedGraphBuilder;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.PrimMST;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MinWeightFeedbackEdgeSetTest {
    @Test
    public void randomTest() {
        int N = 2;
        for (int i = 0; i < 80; i++) {
            if (i % 10 == 9) {
                N <<= 1;
            }
            EdgeWeightedGraph graph = WeightedGraphBuilder.buildRandomGraph(N);
            Set<Edge> test = MinWeightFeedbackEdgeSet.solve(graph);
            EdgeWeightedGraph reverseWeight = new EdgeWeightedGraph(graph.V());
            Map<Edge, Edge> map = new HashMap<>();
            for (Edge e : graph.edges()) {
                Edge rev = new Edge(e.either(), e.other(e.either()), -e.weight());
                reverseWeight.addEdge(rev);
                map.put(rev, e);
            }
            PrimMST mst = new PrimMST(reverseWeight);
            Set<Edge> expect = new HashSet<>();
            mst.edges().forEach(expect::add);
            for (Map.Entry<Edge, Edge> entry : map.entrySet()) {
                Assert.assertEquals(!expect.contains(entry.getKey()), test.contains(entry.getValue()));
            }
        }
    }

}