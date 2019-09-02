package part2.week1.directedgraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShortestDirectedCycleTest {
    @Test
    public void randomTest() {
        int N = 300;
        Random r = new Random();
        for (int i = 5; i < N; i += r.nextInt(100)) {
            int v = r.nextInt(i) + i;
            Digraph G = DigraphGenerator.simple(v, r.nextInt(v * (v - 1)));
            List<Integer>[] graph = new List[v];
            for (int j = 0; j < v; j++) {
                graph[j] = new ArrayList<>();
                for (int k : G.adj(j)) {
                    graph[j].add(k);
                }
            }
            int test = ShortestDirectedCycle.solve(graph);
            commonutil.graph.ShortestDirectedCycle expect = new commonutil.graph.ShortestDirectedCycle(G);
            if (!expect.hasCycle()) {
                Assert.assertEquals(0, test);
            } else {
                Assert.assertEquals(expect.length(),test);
            }

        }

    }


}