package part2.week1.undirectedgraph;

import commonutil.graph.GraphBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NonrecursiveDFSTest {
    private final Random r = new Random();

    @Test
    public void randomTest() {
        int N = 2000;
        for (int i = 10; i <= N; i *= 2) {
            List<Integer>[] graph = GraphBuilder.buildRandomUndirectGraph(i, true);
            Assert.assertEquals(expect(graph), NonrecursiveDFS.dfs(graph));
        }
    }

    private List<Integer> expect(List<Integer>[] graph) {
        List<Integer> res = new ArrayList<>();
        dfs(res, 0, graph, new boolean[graph.length]);
        return res;
    }

    private void dfs(List<Integer> res, int i, List<Integer>[] graph, boolean[] seen) {
        if (seen[i]) return;
        res.add(i);
        seen[i] = true;
        for (int nei : graph[i]) {
            dfs(res, nei, graph, seen);
        }
    }


}