package part2.week1.directedgraph;

import commonutil.graph.GraphConverter;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import edu.princeton.cs.algs4.Topological;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HamilitonianPathInDAGTest {

    @Test
    public void randomTest() {
        int N = 300;
        Random r = new Random();
        for (int i = 5; i < N; i += r.nextInt(10)) {
            int v = r.nextInt(i) + i;
            Digraph G = DigraphGenerator.dag(v, r.nextInt(v * (v - 1) / 2));
            Assert.assertEquals(expect(G), HamilitonianPathInDAG.solve(GraphConverter.convert(G)));
        }
    }

    private List<Integer> expect(Digraph DAG) {
        Topological topological = new Topological(DAG);
        int pre = -1;
        List<Integer> res = new ArrayList<>();
        for (int cur : topological.order()) {
            if (pre != -1) {
                boolean find = false;
                for (int k : DAG.adj(pre)) {
                    if (k == cur) find = true;
                }
                if (!find) return new ArrayList<>();
            }
            res.add(cur);
            pre = cur;
        }
        return res;
    }

}