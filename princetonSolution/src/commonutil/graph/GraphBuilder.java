package commonutil.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GraphBuilder {
    public static List<Integer>[] buildRandomDirectGraph(int n) {
        Random r = new Random();
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            int edges = r.nextInt(n);
            List<Integer> cur = graph[i];
            for (int j = 0; j < edges; j++) {
                if (j >= i && j + 1 == n) {
                    continue;
                }
                cur.add(j >= i ? j + 1 : j);
            }
            int k = edges;
            for (int j = edges; j < n; j++) {
                int pos = r.nextInt(++k);
                if (pos < edges) {
                    if (j >= i && j + 1 == n) {
                        continue;
                    }
                    cur.set(pos, j >= i ? j + 1 : j);
                }
            }
        }
        return graph;
    }

    public static List<Integer>[] buildRandomUndirectGraph(int n, boolean sparse) {
        Random r = new Random();
        Set<Integer>[] graph = new Set[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new HashSet<>();
        }
        for (int i = 0; i < n; i++) {
            int edges = r.nextInt(n);
            List<Integer> cur = new ArrayList<>();
            for (int j = 0; j < edges; j++) {
                if (j >= i && j + 1 == n) {
                    continue;
                }
                cur.add(j >= i ? j + 1 : j);
            }
            int k = edges;
            for (int j = edges; j < n; j++) {
                int pos = r.nextInt(++k);
                if (pos < edges) {
                    if (j >= i && j + 1 == n) {
                        continue;
                    }
                    cur.set(pos, j >= i ? j + 1 : j);
                }
            }
            for (int nei : cur) {
                if (!sparse || r.nextBoolean()) {
                    graph[i].add(nei);
                    graph[nei].add(i);
                }
            }
        }
        List<Integer>[] ret = new List[n];
        for (int i = 0; i < n; i++) ret[i] = new ArrayList<>(graph[i]);
        return ret;
    }
    @Test
    public void testRandomDerict() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            List<Integer>[] res = buildRandomDirectGraph(7);
            StringBuilder stringBuilder = new StringBuilder();
            for (List<Integer> nei : res) {
                System.out.print(nei + ", ");
                stringBuilder.append(nei);
            }
            System.out.println();
            Assert.assertTrue(set.add(stringBuilder.toString()));
        }
    }

    @Test
    public void testRandomUnderict() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            List<Integer>[] res = buildRandomUndirectGraph(7, true);
            StringBuilder stringBuilder = new StringBuilder();
            for (List<Integer> nei : res) {
                System.out.print(nei + ", ");
                stringBuilder.append(nei);
            }
            System.out.println();
            Assert.assertTrue(set.add(stringBuilder.toString()));
        }
    }

    public static List<Integer>[] buildEulerCycle(int n) {
        Random r = new Random();
        Set<Integer>[] graph = new Set[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new HashSet<>();
        }
        for (int i = 0; i < n; i++) {
            int k = r.nextInt(n);
            int j = r.nextInt(n);
            if (k != j) {
                graph[k].add(j);
                graph[j].add(k);
            }
        }

        for (int i = 0; i < n - 1; i++) {
            int maxTimes = n - i - 1;
            while (graph[i].size() % 2 != 0 && maxTimes >= 0) {
                int k = i + 1 + r.nextInt(n - i - 1);
                graph[k].add(i);
                graph[i].add(k);
                maxTimes--;
            }
        }
        List<Integer>[] ret = new List[n];
        for (int i = 0; i < n; i++) ret[i] = new ArrayList<>(graph[i]);
        return ret;
    }

    private boolean isConnected(List<Integer>[] graph) {
        Set<Integer> seen = new HashSet<>();
        dfs(0, graph, seen);
        return seen.size() == graph.length;
    }

    private void dfs(int i, List<Integer>[] graph, Set<Integer> seen) {
        seen.add(i);
        for (int nei : graph[i]) {
            if (seen.contains(nei)) continue;
            dfs(nei, graph, seen);
        }
    }
}
