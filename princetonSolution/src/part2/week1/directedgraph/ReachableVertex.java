package part2.week1.directedgraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * DAG: we just check the node of outdegree is 0 is only one.
 * Digraph: build strong component, then use strong component DAG then run above function.
 */
public class ReachableVertex {
    public static int solveDAG(Set<Integer>[] dag) {
        int ans = -1, n = dag.length;
        for (int i = 0; i < n; i++) {
            if (dag[i].isEmpty()) {
                if (ans != -1) return -1;
                ans = i;
            }
        }
        return ans;
    }

    public static int solveDigraph(Set<Integer>[] digraph) {
        int n = digraph.length;
        Set<Integer>[] rev = reverse(digraph);
        LinkedList<Integer> postOrder = new LinkedList<>();
        boolean[] seen = new boolean[digraph.length];
        for (int i = 0; i < n; i++) {
            if (seen[i]) continue;
            dfs(i, rev, postOrder, seen);
        }
        Set<Integer>[] outdegree = new Set[n];
        for (int i = 0; i < n; i++) outdegree[i] = new HashSet<>();
        int[] seen2 = new int[digraph.length];
        Arrays.fill(seen2, -1);
        for (int i : postOrder) {
            if (seen2[i] != -1) continue;
            dfs2(i, i, digraph, outdegree, seen2);
        }
        Set<Integer> used = new HashSet<>();
        for (int i : seen2) used.add(i);
        return solveDAG(outdegree, used);
    }

    private static int solveDAG(Set<Integer>[] dag, Set<Integer> used) {
        int ans = -1, n = dag.length;
        for (int i = 0; i < n; i++) {
            if (used.contains(i) && dag[i].isEmpty()) {
                if (ans != -1) return -1;
                ans = i;
            }
        }
        return ans;
    }

    private static void dfs2(int cur, int idx, Set<Integer>[] gra, Set<Integer>[] outdegree, int[] seen) {
        seen[cur] = idx;
        for (int nei : gra[cur]) {
            if (seen[nei] == idx) continue;
            if (seen[nei] != -1) {
                outdegree[idx].add(seen[nei]);
                continue;
            }
            dfs2(nei, idx, gra, outdegree, seen);
        }
    }

    private static void dfs(int cur, Set<Integer>[] rev, LinkedList<Integer> postOrder, boolean[] seen) {
        seen[cur] = true;
        for (int nei : rev[cur]) {
            if (seen[nei]) continue;
            dfs(nei, rev, postOrder, seen);
        }
        postOrder.addFirst(cur);
    }

    private static Set<Integer>[] reverse(Set<Integer>[] digraph) {
        int n = digraph.length;
        Set<Integer>[] rev = new Set[n];
        for (int i = 0; i < n; i++) {
            rev[i] = new HashSet<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j : digraph[i]) {
                rev[j].add(i);
            }
        }
        return rev;
    }
}
