package part2.week1.directedgraph;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ShortestDirectedCycle {

    public static int solve(List<Integer>[] diGraph) {
        int min = Integer.MAX_VALUE, n = diGraph.length;
        for (int i = 0; i < n; i++) {
            Queue<Integer> q = new LinkedList<>();
            q.offer(i);
            boolean[] seen = new boolean[n];
            int step = 0;
            boolean findCycle = false;
            while (!q.isEmpty()) {
                int qsize = q.size();
                for (int j = 0; j < qsize; j++) {
                    int cur = q.poll();
                    for (int nei : diGraph[cur]) {
                        if (nei == i) {
                            findCycle = true;
                            min = Math.min(min, step + 1);
                            break;
                        }
                        if (seen[nei]) continue;
                        seen[nei] = true;
                        q.offer(nei);
                    }
                    if (findCycle) break;
                }
                if (findCycle) break;
                step++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }


}
