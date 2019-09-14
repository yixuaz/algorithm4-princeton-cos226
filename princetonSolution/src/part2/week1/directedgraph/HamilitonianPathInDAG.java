package part2.week1.directedgraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * if this DAG has a path which is one vertex linked by another one vertex, then it is a Hamiltonian path; other wise there are no hamiltonian path.
 * then we can use topological sort, and check it meet the condition above.
 */
public class HamilitonianPathInDAG {
    public static List<Integer> solve(Set<Integer>[] DAG) {
        LinkedList<Integer> res = new LinkedList<>();
        int n = DAG.length;
        boolean[] seen = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (seen[i]) continue;
            dfs(DAG, i, seen, res);
        }
        int pre = -1;
        for (int k : res) {
            if (pre != -1 && !DAG[pre].contains(k)) {
                return new ArrayList<>();
            }
            pre = k;
        }
        return res;
    }

    private static void dfs(Set<Integer>[] dag, int i, boolean[] seen, LinkedList<Integer> res) {
        seen[i] = true;
        for (int nei : dag[i]) {
            if (seen[nei]) continue;
            dfs(dag, nei, seen, res);
        }
        res.addFirst(i);
    }
}
