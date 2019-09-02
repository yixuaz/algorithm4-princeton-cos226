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
        return find(G, s, t, res, false);
    }

    public double findIncrease(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res) {
        return find(G, s, t, res, true);
    }

    private double find(EdgeWeightedDigraph G, int s, int t, LinkedList<DirectedEdge> res, boolean increase) {
        PriorityQueue<PQEntry> pq = new PriorityQueue<PQEntry>((a, b) -> Double.compare(a.pathSum, b.pathSum));
        if (increase) G.sortDecrease();
        else G.sortIncrease();
        for (DirectedEdge e : G.adj(s)) {
            pq.offer(new PQEntry(e));
            G.getVertex(s).pos++;
        }
        while (!pq.isEmpty()) {
            PQEntry top = pq.poll();
            int vidx = top.inEdge.to();
            Vertex v = G.getVertex(vidx);
            int isMeetOrder = increase ? 1 : -1;
            while (v.pos < v.outEdge().size() &&
                    Double.compare(v.outEdge().get(v.pos).weight(), top.inEdge.weight()) == isMeetOrder) {
                DirectedEdge e = v.outEdge().get(v.pos);
                e.pre = top.inEdge;
                pq.offer(new PQEntry(top.pathSum + e.weight(), e));
                v.pos++;
            }
            if (v.backEdge == null)
                v.backEdge = top.inEdge;
        }
        return buildAnswerPath(G, t, res);
    }

}
