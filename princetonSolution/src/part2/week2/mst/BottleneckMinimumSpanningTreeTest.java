package part2.week2.mst;

import commonutil.graph.WeightedGraphBuilder;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.PrimMST;
import org.junit.Assert;
import org.junit.Test;
import part2.week2.mst.bottleneckmst.util.Edge;
import part2.week2.mst.bottleneckmst.util.MyEdgeWeightedGraph;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BottleneckMinimumSpanningTreeTest {
    @Test
    public void basicTest() {
        Edge e1 = new Edge(0, 1, 7);
        Edge e2 = new Edge(1, 2, 8);
        Edge e3 = new Edge(1, 4, 7);
        Edge e4 = new Edge(4, 6, 7);
        Edge e5 = new Edge(4, 5, 8);
        Edge e6 = new Edge(1, 3, 9);
        Edge e7 = new Edge(5, 6, 11);
        Edge e8 = new Edge(3, 4, 15);
        Edge e9 = new Edge(0, 3, 5);
        Edge e10 = new Edge(2, 4, 5);
        Edge e11 = new Edge(3, 5, 6);
        MyEdgeWeightedGraph graph = new MyEdgeWeightedGraph();
        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        graph.addEdge(e6);
        graph.addEdge(e7);
        graph.addEdge(e8);
        graph.addEdge(e9);
        graph.addEdge(e10);
        graph.addEdge(e11);
        BottleneckMinimumSpanningTree bottleneckMinimumSpanningTree = new BottleneckMinimumSpanningTree();
        List<Edge> result = bottleneckMinimumSpanningTree.getInLinearTime(graph);
        Collections.sort(result);
        System.out.println(result);
        Assert.assertEquals(0, Double.compare(7, result.get(result.size() - 1).weight()));
    }

    @Test
    public void randomTest() {
        BottleneckMinimumSpanningTree bmst = new BottleneckMinimumSpanningTree();
        int N = 10;
        for (int i = 0; i < 50; i++) {
            if (i % 10 == 5) {
                N <<= 1;
            }
            EdgeWeightedGraph graph = WeightedGraphBuilder.buildRandomConnectedGraph(N);
            PrimMST primMST = new PrimMST(graph);
            double expect = 0;
            int cnt = 0;
            for (edu.princeton.cs.algs4.Edge e : primMST.edges()) {
                expect = Math.max(e.weight(), expect);
                cnt++;
            }
            List<Edge> result = bmst.getInLinearTime(graph);
            double testWeight = 0;
            for (Edge e : result) {
                testWeight = Math.max(testWeight, e.weight());
            }
            List<Edge> result2 = bmst.get(graph);
            Assert.assertEquals(0, Double.compare(expect, result2.get(result2.size() - 1).weight()));
            Assert.assertEquals(0, Double.compare(expect, testWeight));
            Assert.assertEquals(cnt, result.size());
            Assert.assertEquals(cnt, result2.size());
        }
    }


}