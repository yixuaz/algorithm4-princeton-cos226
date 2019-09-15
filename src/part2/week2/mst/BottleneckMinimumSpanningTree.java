package part2.week2.mst;

import commonutil.QuickSelect;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import part2.week2.mst.bottleneckmst.util.Edge;
import part2.week2.mst.bottleneckmst.util.MyEdgeWeightedGraph;

import java.util.List;

/**
 * Bottleneck minimum spanning tree. Given a connected edge-weighted graph, design an efficient algorithm to
 * find a minimum bottleneck spanning tree.
 * The bottleneck capacity of a spanning tree is the weights of its largest edge.
 * A minimum bottleneck spanning tree is a spanning tree of minimum bottleneck capacity.
 * <p>
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
