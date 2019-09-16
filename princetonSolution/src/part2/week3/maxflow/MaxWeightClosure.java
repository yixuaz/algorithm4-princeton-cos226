package part2.week3.maxflow;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import part2.week3.maxflow.maxweightclosure.util.Vertex;

import java.util.List;
import java.util.Set;

/**
 * if we use some point, we have to contain all the point this vertex point out. we need to check it is deserved.
 * so we only fetch the point which bring its neighbor, the weight is still positive component. to achieve that ,
 * we could build a graph, if we found negative paths is larger or equal to the positive paths,
 * we just discard them. in max flow, it is a full path by the positive capacity, if positive is larger than negative,
 * it is a full path by negative capacity. so we need to sum all the positive path then minus the max flow,
 * it is the answer.
 * we add a source to link all the positive vertex, and assign a target be linked by all the negative vertex;
 * assign edge (v, w) a weight of infinity if there is an edge from v to w in the original digraph.
 */
public class MaxWeightClosure {
    public static int solve(List<Vertex> vertices, Set<Vertex> ans) {
        int n = vertices.size();
        FlowNetwork flowNetwork = new FlowNetwork(n + 2);
        int total = 0;
        for (Vertex v : vertices) {
            if (v.weight() > 0) {
                total += v.weight();
                flowNetwork.addEdge(new FlowEdge(n, v.idx(), v.weight()));
            } else {
                flowNetwork.addEdge(new FlowEdge(v.idx(), n + 1, -v.weight()));
            }
            for (Vertex nei : v.adj()) {
                flowNetwork.addEdge(new FlowEdge(v.idx(), nei.idx(), Double.POSITIVE_INFINITY));
            }
        }
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, n, n + 1);
        for (Vertex v : vertices) {
            if (fordFulkerson.inCut(v.idx())) {
                ans.add(v);
            }
        }
        return total - (int) fordFulkerson.value();
    }
}
