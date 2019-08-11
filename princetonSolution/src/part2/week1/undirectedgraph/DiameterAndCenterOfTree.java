package part2.week1.undirectedgraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class DiameterAndCenterOfTree {
    public static int diameter(List<Integer>[] graph) {
        return findDiameter(graph)[2];
    }

    private static int[] findDiameter(List<Integer>[] graph) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        int far = 0;
        boolean[] seen = new boolean[graph.length];
        seen[0] = true;
        while (!q.isEmpty()) {
            int qsize = q.size();
            for (int i = 0; i < qsize; i++) {
                int cur = q.poll();
                for (int nei : graph[cur]) {
                    if (seen[nei]) continue;
                    seen[nei] = true;
                    q.offer(nei);
                    far = nei;
                }
            }
        }
        q = new LinkedList<>();
        q.offer(far);
        seen = new boolean[graph.length];
        seen[far] = true;
        int diameter = 0, des = 0;
        while (!q.isEmpty()) {
            int qsize = q.size();
            for (int i = 0; i < qsize; i++) {
                int cur = q.poll();
                for (int nei : graph[cur]) {
                    if (seen[nei]) continue;
                    seen[nei] = true;
                    q.offer(nei);
                    des = nei;
                }
            }
            diameter++;
        }
        return new int[]{far, des, diameter - 1};
    }

    public static int center(List<Integer>[] graph) {
        int[] cur = findDiameter(graph);
        Set<Integer> begin = new HashSet<>(), end = new HashSet<>();
        if (cur[0] == cur[1]) return cur[0];
        begin.add(cur[0]);
        end.add(cur[1]);
        int cnt = 0;
        boolean[] seen = new boolean[graph.length];
        seen[cur[0]] = seen[cur[1]] = true;
        while (!begin.isEmpty() && !end.isEmpty()) {
            Set<Integer> next = new HashSet<>();
            if (cnt % 2 == 0) {
                for (int p : begin) {
                    for (int nei : graph[p]) {
                        if (end.contains(nei)) return nei;
                        if (seen[nei]) continue;
                        seen[nei] = true;
                        next.add(nei);
                    }
                }
                begin = next;
            } else {
                for (int p : end) {
                    for (int nei : graph[p]) {
                        if (begin.contains(nei)) return nei;
                        if (seen[nei]) continue;
                        seen[nei] = true;
                        next.add(nei);
                    }
                }
                end = next;
            }
            cnt++;
        }
        throw new IllegalStateException("INVALID AREA");
    }

}
