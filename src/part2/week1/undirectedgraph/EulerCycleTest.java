package part2.week1.undirectedgraph;

import commonutil.graph.GraphBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EulerCycleTest {
    @Test
    public void basicTest1() {
        List<Integer>[] gra = new List[3];
        for (int i = 0; i < 3; i++) {
            gra[i] = new ArrayList<>();
        }
        for (int i = 0; i < 3; i++) {
            gra[i].add((i + 1) % 3);
            gra[(i + 1) % 3].add(i);
        }
        Assert.assertEquals("[0, 1, 2, 0]", EulerCycle.solveUndirectGraph(gra).toString());
    }

    @Test
    public void basicTest2() {
        List<Integer>[] gra = new List[2];
        for (int i = 0; i < 2; i++) {
            gra[i] = new ArrayList<>();
        }
        gra[0].add(1);
        gra[1].add(0);

        Assert.assertEquals("[]", EulerCycle.solveUndirectGraph(gra).toString());
    }

    @Test
    public void basicTest3() {
        List<Integer>[] gra = new List[0];
        Assert.assertEquals("[]", EulerCycle.solveUndirectGraph(gra).toString());
    }

    @Test
    public void randomTest() {
        long timecost1 = 0, timecost2 = 0;
        for (int i = 5; i < 1000; i += 50) {
            List<Integer>[] graph = GraphBuilder.buildEulerCycle(i);
            for (List<Integer> j : graph) {
                Collections.sort(j);
            }
            long st = System.nanoTime();
            List<Integer> expect = fleury(graph);
            timecost1 += System.nanoTime() - st;
            st = System.nanoTime();
            List<Integer> test = EulerCycle.solveUndirectGraph(graph);
            timecost2 += System.nanoTime() - st;
            Assert.assertEquals(expect, test);
        }
        Assert.assertTrue(timecost1 + "," + timecost2, timecost1 > 90 * timecost2);
    }


    // O ((V + E)^2) algorithm
    private List<Integer> fleury(List<Integer>[] inGraph) {
        List<Integer> res = new ArrayList<>();
        for (List<Integer> edge : inGraph) {
            if (edge.size() % 2 == 1) {
                return res;
            }
        }
        int n = inGraph.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>(inGraph[i]);
        }
        Set<Integer>[] seen = new Set[n];
        for (int i = 0; i < n; i++) {
            seen[i] = new HashSet<>();
        }
        LinkedList<Integer> path = new LinkedList<>();
        path.add(0);
        dfs(0, graph, seen, path);
        return path;
    }

    private void dfs(int cur, List<Integer>[] graph, Set<Integer>[] seen, LinkedList<Integer> path) {
        for (int nei : graph[cur]) {
            if (seen[cur].contains(nei)) continue;
            if (isBridge(graph, seen, cur, nei)) {
                continue;
            }
            path.add(nei);
            seen[cur].add(nei);
            seen[nei].add(cur);
            dfs(nei, graph, seen, path);
        }
    }

    private boolean isBridge(List<Integer>[] graph, Set<Integer>[] seen, int cur, int nei) {
        if (graph[cur].size() - seen[cur].size() == 1) return false;
        int count1 = dfsCount(graph, seen, cur, new boolean[graph.length]);
        seen[cur].add(nei);
        seen[nei].add(cur);
        int count2 = dfsCount(graph, seen, cur, new boolean[graph.length]);
        seen[cur].remove(nei);
        seen[nei].remove(cur);
        return count1 > count2;
    }

    private int dfsCount(List<Integer>[] graph, Set<Integer>[] seen, int cur, boolean[] vertexVisted) {
        vertexVisted[cur] = true;
        int count = 1;
        for (int nei : graph[cur]) {
            if (seen[cur].contains(nei)) continue;
            if (vertexVisted[nei]) continue;
            count += dfsCount(graph, seen, nei, vertexVisted);
        }
        return count;
    }


}