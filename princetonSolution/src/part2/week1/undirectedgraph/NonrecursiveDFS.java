package part2.week1.undirectedgraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * we could use stack to change recursion dfs to iteration dfs.
 */
public class NonrecursiveDFS {
    public static List<Integer> dfs(List<Integer>[] graph) {
        Deque<Integer> st = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();
        boolean[] seen = new boolean[graph.length];
        st.push(0);
        while (!st.isEmpty()) {
            int cur = st.pop();
            if (seen[cur]) continue;
            seen[cur] = true;
            res.add(cur);
            List<Integer> nei = graph[cur];
            for (int i = nei.size() - 1; i >= 0; i--) {
                st.push(nei.get(i));
            }
        }
        return res;
    }
}
