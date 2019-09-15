package part2.week2.shortestpath.msp.util;

import java.util.LinkedList;

public interface MonotonicSP {
    /**
     * out put the miniumn path sum, the path should be saved in res list
     *
     * @param G   graph
     * @param s   source node
     * @param t   target node
     * @param res path list
     * @return the miniumn path sum
     */
    double findDecrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res);

    double findIncrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res);

    default double buildAnswerPath(EdgeWeightedDigraph G, int t, LinkedList<DirectedEdge> res) {
        double sum = 0;
        DirectedEdge e = G.getVertex(t).backEdge;
        if (e == null) return Double.POSITIVE_INFINITY;
        while (e != null) {
            res.addFirst(e);
            sum += e.weight();
            e = e.pre;
        }
        return sum;
    }
}
