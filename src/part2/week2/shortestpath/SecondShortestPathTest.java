package part2.week2.shortestpath;

import org.junit.Assert;
import org.junit.Test;
import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SecondShortestPathTest {
    @Test
    public void basicTest() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(4);
        G.addEdge(new DirectedEdge(0, 1, 1));
        G.addEdge(new DirectedEdge(1, 3, 5));
        G.addEdge(new DirectedEdge(0, 2, 2));
        G.addEdge(new DirectedEdge(2, 3, 3));
        List<DirectedEdge> path = new ArrayList<>();
        Assert.assertEquals(6, (int) SecondShortestPath.solve(G, 0, 3, path));
        System.out.println(path);
    }

    @Test
    public void randomTest() {
        int N = 5;
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            if (i % 100 == 0) N++;
            int V = 2 + r.nextInt(N), E = r.nextInt(V * (V - 1));
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(V, E);
            List<DirectedEdge> path = new ArrayList<>();
            double secondShort = SecondShortestPath.solve(G, 0, V - 1, path);
            System.out.println("my second short : " + secondShort);
            System.out.println(path);
            List<Double> allNoCyclePathVal = getAllPath(G, 0, V - 1);
            double expectSeconPathLength = -1;
            if (allNoCyclePathVal.size() > 1) expectSeconPathLength = allNoCyclePathVal.get(1);
            System.out.println("no cycle second short : " + expectSeconPathLength);
            if (expectSeconPathLength != -1 && Double.compare(expectSeconPathLength, secondShort) <= 0) {
                Assert.assertEquals(0, Double.compare(expectSeconPathLength, secondShort));
            }
            if (expectSeconPathLength != -1)
                Assert.assertTrue(checkPathIsCorrect(path, G, 0, V - 1));
            System.out.println(i + " pass");
        }
    }

    private boolean checkPathIsCorrect(List<DirectedEdge> path, EdgeWeightedDigraph g, int source, int target) {
        for (DirectedEdge e : path) {
            boolean find = false;
            for (DirectedEdge tar : g.adj(e.from())) {
                if (tar.weight() == e.weight() && tar.to() == e.to()) {
                    find = true;
                    break;
                }
            }
            if (!find) return false;
        }
        int last = path.size() - 1;
        return !path.isEmpty() && path.get(0).from() == source && path.get(last).to() == target;
    }

    private List<Double> getAllPath(EdgeWeightedDigraph g, int s, int t) {
        List<Double> res = new ArrayList<>();
        dfs(g, s, t, 0, res, new boolean[g.V()]);
        Collections.sort(res);
        System.out.println("possible len:" + res);
        return res;
    }

    private void dfs(EdgeWeightedDigraph g, int s, int t, double sum, List<Double> res,
                     boolean[] seen) {
        seen[s] = true;
        if (s == t) {
            res.add(sum);
            seen[s] = false;
            return;
        }
        for (DirectedEdge e : g.adj(s)) {
            if (seen[e.to()]) {
                continue;
            }
            dfs(g, e.to(), t, sum + e.weight(), res, seen);
        }
        seen[s] = false;
    }


}