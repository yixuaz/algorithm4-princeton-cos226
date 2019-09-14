package part2.week1.directedgraph;

import commonutil.graph.GraphConverter;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import edu.princeton.cs.algs4.TransitiveClosure;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ReachableVertexTest {
    @Test
    public void testSolveDAG() {
        int N = 200;
        Random r = new Random();
        for (int i = 5; i < N; i += r.nextInt(5)) {
            Digraph dag = DigraphGenerator.dag(i, r.nextInt(i * (i - 1) / 2));
            TransitiveClosure tc = new TransitiveClosure(dag);
            int test = ReachableVertex.solveDAG(GraphConverter.convert(dag));
            if (test == -1) {
                Assert.assertTrue(checkNoThatPoint(tc, i));
            } else {
                Assert.assertTrue(checkThatPoint(tc, test, i));
            }
        }
    }

    @Test
    public void testSolveDigraph() {
        int N = 200;
        Random r = new Random();
        for (int i = 5; i < N; i += r.nextInt(15)) {
            Digraph digraph = DigraphGenerator.simple(i, r.nextInt(i * (i - 1)));
            TransitiveClosure tc = new TransitiveClosure(digraph);
            int test = ReachableVertex.solveDigraph(GraphConverter.convert(digraph));
            if (test == -1) {
                Assert.assertTrue(checkNoThatPoint(tc, i));
            } else {
                Assert.assertTrue(checkThatPoint(tc, test, i));
            }

        }

    }

    private boolean checkThatPoint(TransitiveClosure tc, int test, int n) {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (tc.reachable(i, test)) cnt++;
        }
        return cnt == n;
    }

    private boolean checkNoThatPoint(TransitiveClosure tc, int n) {
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (tc.reachable(j, i)) cnt++;
            }
            if (cnt == n) return false;
        }
        return true;
    }


}