package part2.week3.maxflow;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import part2.week3.maxflow.maxweightclosure.util.Vertex;

import java.util.List;
import java.util.Set;

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
