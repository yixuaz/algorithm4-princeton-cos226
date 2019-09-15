package part2.week2.mst.extracredit;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.PrimMST;
import org.junit.Test;

import static commonutil.graph.WeightedGraphBuilder.buildRandomConnectedGraph;
import static org.junit.Assert.assertTrue;

public class EagerPrimTest {
    private static final double FLOATING_POINT_EPSILON = 1E-9;

    @Test
    public void randomTest() {

        for (int i = 1; i < 100; i++) {
            EdgeWeightedGraph graph = buildRandomConnectedGraph(i);
            EagerPrim test = new EagerPrim(graph);
            PrimMST expect = new PrimMST(graph);
            assertTrue(info(test, expect), Math.abs(test.weight() - expect.weight()) < FLOATING_POINT_EPSILON);
            System.out.println("pass " + i);
        }

    }

    private String info(EagerPrim test, PrimMST expect) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("test: " + test.weight() + "\n");
        for (Edge e : test.edges()) stringBuilder.append(e).append(",");
        stringBuilder.append("\nexpect: " + expect.weight() + "\n");
        for (Edge e : expect.edges()) stringBuilder.append(e).append(",");
        return stringBuilder.toString();
    }
}