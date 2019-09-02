package part2.week3.maxflow;

import edu.princeton.cs.algs4.IndexMaxPQ;
import org.junit.Assert;
import org.junit.Test;
import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class FattestPathTest {
    @Test
    public void basicTest() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(4);
        G.addEdge(new DirectedEdge(0,1,350));
        G.addEdge(new DirectedEdge(0,2,350));
        G.addEdge(new DirectedEdge(1,3,200));
        G.addEdge(new DirectedEdge(2,3,250));
        List<DirectedEdge> path = new ArrayList<>();
        Assert.assertEquals(250, FattestPath.solve(G, path,0 ,3));
        System.out.println(path);
    }
    @Test
    public void basicTestNoPath() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(4);
        G.addEdge(new DirectedEdge(0,1,350));
        G.addEdge(new DirectedEdge(0,2,350));
        List<DirectedEdge> path = new ArrayList<>();
        Assert.assertEquals(Integer.MIN_VALUE, FattestPath.solve(G, path,0 ,3));
        System.out.println(path);
    }
    @Test
    public void randomTest() {
        Random r = new Random();
        int forLoops = 50, graphSize = 10;
        for (int i = 0; i < forLoops; i++) {
            if (i % 10 == 9) graphSize <<= 1;
            // random generate graph
            int V = 2 + r.nextInt(graphSize), E = r.nextInt(V * (V - 1));
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(V, E, true);
            double[] distTo = new double[G.V()];
            DirectedEdge[] edgeTo = new DirectedEdge[G.V()];
            solveByDijkstra(G, 0, distTo, edgeTo);
            for (int j = 1; j < V; j++) {
                int expect = distTo[j] == Double.NEGATIVE_INFINITY ? Integer.MIN_VALUE : (int)distTo[j];
                List<DirectedEdge> test = new ArrayList<>();
                assertEquals(expect, FattestPath.solve(G, test,0 ,j));
                if (expect != Integer.MIN_VALUE) {
                    System.out.println(test);
                    System.out.println(pathTo(j, edgeTo));
                }
                System.out.println("pass " + expect);
            }
        }

    }

    public void solveByDijkstra(EdgeWeightedDigraph G, int s, double[] distTo, DirectedEdge[] edgeTo) {

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.NEGATIVE_INFINITY;
        distTo[s] = Double.POSITIVE_INFINITY;
        // relax vertices in order of distance from s
        IndexMaxPQ<Double> pq = new IndexMaxPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMax();
            for (DirectedEdge e : G.adj(v))
                relax(e, pq, distTo, edgeTo);
        }
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e, IndexMaxPQ<Double> pq, double[] distTo, DirectedEdge[] edgeTo) {
        int v = e.from(), w = e.to();
        if (distTo[w] < Math.min(distTo[v], e.weight())) {
            distTo[w] = Math.min(distTo[v], e.weight());
            edgeTo[w] = e;
            if (pq.contains(w)) pq.increaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    private List<DirectedEdge> pathTo(int v, DirectedEdge[] edgeTo) {
        LinkedList<DirectedEdge> path = new LinkedList<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.addFirst(e);
        }
        return path;
    }

}