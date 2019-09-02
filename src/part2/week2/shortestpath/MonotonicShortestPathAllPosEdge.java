package part2.week2.shortestpath;

import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph.Vertex;
import part2.week2.shortestpath.msp.util.MonotonicSP;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class MonotonicShortestPathAllPosEdge implements MonotonicSP {

    private static class PQEntry {
        private double pathSum;
        private DirectedEdge inEdge;

        public PQEntry(double pathSum, DirectedEdge inEdge) {
            this.pathSum = pathSum;
            this.inEdge = inEdge;
        }

        public PQEntry(DirectedEdge inEdge) {
            this.inEdge = inEdge;
            pathSum = inEdge.weight();
        }
    }


    public double findDecrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res) {
        // TODO: ADD YOUR CODE HERE
        return 0;
    }

    public double findIncrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res) {
        // TODO: ADD YOUR CODE HERE
        return 0;
    }

}
