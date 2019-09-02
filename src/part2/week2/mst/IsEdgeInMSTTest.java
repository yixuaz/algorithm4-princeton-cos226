package part2.week2.mst;

import commonutil.graph.WeightedGraphBuilder;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.PrimMST;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class IsEdgeInMSTTest {
    @Test
    public void randomTest() {
        int N = 5;
        for (int i = 0; i < 11; i++) {
            N <<= (i % 2);
            EdgeWeightedGraph graph = WeightedGraphBuilder.buildRandomConnectedGraph(N);
            Set<Edge> inMst = isExpect(graph);
            for (Edge e : graph.edges()) {
                Assert.assertEquals(inMst.contains(e), IsEdgeInMST.solve(graph, e));
                Assert.assertEquals(inMst.contains(e), IsEdgeInMST.solve2(graph, e));
            }
        }
    }

    private Set<Edge> isExpect(EdgeWeightedGraph graph) {
        PrimMST mst = new PrimMST(graph);
        Set<Edge> ret = new HashSet<>();
        mst.edges().forEach(ret::add);
        return ret;
    }

}