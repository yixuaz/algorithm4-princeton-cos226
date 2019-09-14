package part2.week1.undirectedgraph;

import commonutil.Shuffler;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class DiameterAndCenterOfTreeTest {
    private int build(List<Integer>[] graph) {
        int n = graph.length;
        int[] map = new int[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            map[i] = i;
        }
        Shuffler.shuffle(map);

        for (int i = 0; i < n - 1; i++) {
            graph[map[i]].add(map[i + 1]);
            graph[map[i + 1]].add(map[i]);
        }
        return n - 1;
    }

    @Test
    public void basicTest() {
        for (int i = 1; i < 500; i++) {
            List<Integer>[] graph = new List[i];
            int expect = build(graph);
            Assert.assertEquals(expect, DiameterAndCenterOfTree.diameter(graph));
            Assert.assertEquals((expect + 1) / 2, dis(graph, DiameterAndCenterOfTree.center(graph)));
        }
    }

    private void build2(List<Integer>[] graph) {
        int n = graph.length;
        int[] map = new int[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            map[i] = i;
        }
        Shuffler.shuffle(map);
        int tar = map[0];
        Queue<Integer> q = new LinkedList<>();
        q.offer(tar);
        int used = 1, con = (int) Math.sqrt(n);
        Random r = new Random();
        while (used < n) {
            int i = 0, qsize = q.size();
            for (; i < qsize; i++) {
                int canUse = Math.min(n - used, 1 + r.nextInt(con));
                if (canUse == 0) break;
                int cur = q.poll();
                for (int j = 0; j < canUse; j++) {
                    graph[cur].add(map[used]);
                    graph[map[used]].add(cur);
                    q.offer(map[used++]);
                }
            }
        }
    }

    @Test
    public void randomTest2() {
        for (int i = 1; i < 3000; i += 3) {
            List<Integer>[] graph = new List[i];
            build2(graph);
            Assert.assertEquals((DiameterAndCenterOfTree.diameter(graph) + 1) / 2, dis(graph, DiameterAndCenterOfTree.center(graph)));
        }
    }

    private int dis(List<Integer>[] graph, int center) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(center);
        int dis = 0;
        boolean[] seen = new boolean[graph.length];
        seen[center] = true;
        while (!q.isEmpty()) {
            int qsize = q.size();
            for (int i = 0; i < qsize; i++) {
                int cur = q.poll();
                for (int nei : graph[cur]) {
                    if (seen[nei]) continue;
                    seen[nei] = true;
                    q.offer(nei);
                }
            }
            dis++;
        }
        return dis - 1;
    }


}