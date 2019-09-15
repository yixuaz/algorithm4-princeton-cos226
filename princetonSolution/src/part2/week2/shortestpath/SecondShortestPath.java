package part2.week2.shortestpath;


import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.secondshortestpath.util.DijkstraSP;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * a naive wrong idea is that we  compute the shortest path distances from s to every vertex and the shortest path
 * distances from every vertex to t. and find the shortest path, we need to select a node which are not in the shortest
 * path vertex, then find the minimum path in all of these node.
 * <p>
 * the correct way is that node could be same,but the path diff is that at least one edge is not same as
 * the shortest path, so we need to select a edge which are not in the shortest path edge set.
 * then use s to this edge from() , and this edge to() to t. find the minimum path.
 */
public class SecondShortestPath {
    public static double solve(EdgeWeightedDigraph G, int s, int t, List<DirectedEdge> result) {
        DijkstraSP dijkstraSP = new DijkstraSP(G, s);
        if (!dijkstraSP.hasPathTo(t)) return -1;
        System.out.println("shortest " + dijkstraSP.distTo(t));
        System.out.println(dijkstraSP.pathTo(t));
        Set<DirectedEdge> used = new HashSet<>();
        for (DirectedEdge e : dijkstraSP.pathTo(t)) used.add(e);
        EdgeWeightedDigraph mirror = G.reverse();
        DijkstraSP mirrorDijkstraSP = new DijkstraSP(mirror, t);
        double ans = Double.POSITIVE_INFINITY;
        DirectedEdge bridge = null;
        for (DirectedEdge e : G.edges()) {
            if (used.contains(e)) continue;
            double curPath = dijkstraSP.distTo(e.from()) + mirrorDijkstraSP.distTo(e.to()) + e.weight();
            if (curPath < ans) {
                ans = curPath;
                bridge = e;
            }
        }
        // build ans path
        if (bridge == null) return -1;
        LinkedList<DirectedEdge> postPart = new LinkedList<>();
        mirrorDijkstraSP.pathTo(bridge.to()).forEach(a -> {
            postPart.addFirst(G.getOri(a));
        });
        dijkstraSP.pathTo(bridge.from()).forEach(result::add);
        result.add(bridge);
        result.addAll(postPart);
        return ans;
    }
}
