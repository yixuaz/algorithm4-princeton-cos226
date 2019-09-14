package part2.week1.undirectedgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * In Eulerian path, each time we visit a vertex v, we walk through two unvisited edges with one end point as v. Therefore, all middle vertices in Eulerian Path must have even degree. For Eulerian Cycle, any vertex can be middle vertex, therefore all vertices must have even degree.
 *
 * ####solution1 fleury O (E^2)
 * https://www.geeksforgeeks.org/fleurys-algorithm-for-printing-eulerian-path/
 * an edge is a bridge when we remove this edge, the dfs visited node will be less than have this edge.
 * if we have multiple choice, we should keep remove non-bridge first.
 *
 *
 * ####solution2 hierholzers O (E)
 * https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/
 */
public class EulerCycle {
    /**
     * O ((V + E))
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
        // sort graph
        for (List<Integer> i : graph) {
            Collections.sort(i);
        }
        // have cycle, then print it
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

    private static void dfs(int cur, List<Integer>[] graph, Set<Integer>[] seen, LinkedList<Integer> path) {
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
