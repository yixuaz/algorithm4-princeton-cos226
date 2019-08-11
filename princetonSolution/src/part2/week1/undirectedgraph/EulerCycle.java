package part2.week1.undirectedgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EulerCycle {
    /**
     *  O ((V + E))
     * if there is no euler cycle, return null, if have, return path.
     * path is 0 -> 1 -> 2 -> 0, list should be [0, 1, 2, 0]
     * answer can be mulitiple, return the itinerary that has the smallest lexical order when read as a single string.
     * For example, the itinerary [1, 2] has a smaller lexical order than [1, 3].
     */
    public static List<Integer> solveUndirectGraph(List<Integer>[] graph) {
        List<Integer> res = new ArrayList<>();
        for (List<Integer> edge : graph) {
            if (edge.size() % 2 == 1) {
                return res;
            }
        }
        //sort graph
//        for (List<Integer> i : graph) {
//            Collections.sort(i);
//        }
        //have cycle, then print it
        int n = graph.length;
        if (n == 0) return res;
        Set<Integer>[] seen = new Set[n];
        for (int i = 0; i < n; i++) {
            seen[i] = new HashSet<>();
        }
        LinkedList<Integer> path = new LinkedList<>();
        dfs(0, graph, seen, path);
        return path;
    }

    private static void dfs(int cur,List<Integer>[] graph, Set<Integer>[] seen, LinkedList<Integer> path) {
        for (int nei : graph[cur]) {
            if (seen[cur].contains(nei)) {
                continue;
            }
            seen[cur].add(nei);
            seen[nei].add(cur);
            dfs(nei, graph, seen, path);
        }
        path.addFirst(cur);
    }
}
