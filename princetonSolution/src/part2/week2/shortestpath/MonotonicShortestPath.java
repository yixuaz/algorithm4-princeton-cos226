package part2.week2.shortestpath;

import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph.Vertex;
import part2.week2.shortestpath.msp.util.MonotonicSP;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class MonotonicShortestPath implements MonotonicSP {

    public double findDecrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res) {
        return find(G, s, t, res, false);
    }

    public double findIncrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res) {
        return find(G, s, t, res, true);
    }

    public double find(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res, boolean increase) {
        List<DirectedEdge> directedEdgeList = G.edges();
        G.getVertex(s).minPathSum = 0;
        if (increase) {
            Collections.sort(directedEdgeList, (a, b) -> Double.compare(a.weight(), b.weight()));
        } else {
            Collections.sort(directedEdgeList, (a, b) -> Double.compare(b.weight(), a.weight()));
        }
        for (DirectedEdge edge : directedEdgeList) {
            Vertex from = G.getVertex(edge.from());
            if (from.minPathSum == Double.POSITIVE_INFINITY) continue;
            Vertex to = G.getVertex(edge.to());
            boolean preEdgeSame = (edge.weight() == from.getFirWeight(increase));
            double curPathSum = edge.weight() + (preEdgeSame ? from.secPathSum : from.minPathSum);
            if (to.minPathSum > curPathSum) {
                if (edge.weight() != to.getFirWeight(increase)) {
                    to.secPathSum = to.minPathSum;
                    to.secPathbackEdge = to.backEdge;
                }
                to.minPathSum = curPathSum;
                to.backEdge = edge;
                edge.pre = preEdgeSame ? from.secPathbackEdge : from.backEdge;
            }
        }
        return buildAnswerPath(G, t, res);
    }


}
