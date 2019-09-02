package part2.week2.mst;

import commonutil.QuickSelect;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.UF;
import part2.week2.mst.bottleneckmst.util.Edge;
import part2.week2.mst.bottleneckmst.util.MyEdgeWeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * from this quiz question, we allow use the data structure wrote in algs4, to save time.
 */
public class BottleneckMinimumSpanningTree {
    public List<Edge> get(EdgeWeightedGraph graph) {
        // TODO: ADD YOUR CODE HERE
        return null;
    }
    public List<Edge> get(MyEdgeWeightedGraph graph) {
        // TODO: ADD YOUR CODE HERE
        return null;
    }

    public List<Edge> getInLinearTime(EdgeWeightedGraph graph) {
        return msbt(new MyEdgeWeightedGraph(graph));
    }
    public List<Edge> getInLinearTime(MyEdgeWeightedGraph graph) {
        return msbt(graph);
    }

    private List<Edge> msbt(MyEdgeWeightedGraph myEdgeWeightedGraph) {
        // TODO: ADD YOUR CODE HERE
        return null;
    }


    private Edge findMedianInLinearTime(List<Edge> edges) {
        return QuickSelect.findMedian(edges);
    }

}
