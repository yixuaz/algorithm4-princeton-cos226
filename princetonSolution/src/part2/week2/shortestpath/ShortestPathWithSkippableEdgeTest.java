package part2.week2.shortestpath;

import org.junit.Assert;
import org.junit.Test;
import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.secondshortestpath.util.DijkstraSP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class ShortestPathWithSkippableEdgeTest {

    @Test
    public void randomTest() {
        Random r = new Random();
        int N = 10;
        for (int i = 0; i < 300; i++) {
            if (i % 10 == 0) N++;
            int V = 2 + r.nextInt(N), E = r.nextInt(V * (V - 1));
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(V, E);
            List<DirectedEdge> path = new ArrayList<>();
            double test = ShortestPathWithSkippableEdge.solve(G,0, V - 1, path);
            double expect = bruteForce(G,0,V - 1);
            Assert.assertEquals(0, Double.compare(test,expect));
            System.out.println(i + " pass");
        }

    }

    private double bruteForce(EdgeWeightedDigraph g, int s, int t) {
        DijkstraSP havAns = new DijkstraSP(g,s);
        if (!havAns.hasPathTo(t)) return -1;
        double ans = Double.POSITIVE_INFINITY;
        Set<Integer> seen = new HashSet<>();
        for (DirectedEdge e : g.edges()) {
            if (!seen.add(e.from() * g.V() + e.to())) continue;
            double wei = e.weight();
            e.updateWeight(0);
            DijkstraSP dijkstraSP = new DijkstraSP(g,s);
            ans = Math.min(ans,dijkstraSP.distTo(t));
            e.updateWeight(wei);
        }
        return ans;
    }

}