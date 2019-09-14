package part2.week1.undirectedgraph;

import java.util.List;

/**
 * Euler cycle. An Euler cycle in a graph is a cycle (not necessarily simple)
 * that uses every edge in the graph exactly one.
 *
 * Show that a connected graph has an Euler cycle if and only if every vertex has even degree.
 * Design a linear-time algorithm to determine whether a graph has an Euler cycle, and if so, find one.
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
        // TODO: ADD YOUR CODE HERE
        return null;
    }
}
