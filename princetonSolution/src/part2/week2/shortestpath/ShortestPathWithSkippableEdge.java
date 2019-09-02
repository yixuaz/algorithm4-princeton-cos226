package part2.week2.shortestpath;

import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.secondshortestpath.util.DijkstraSP;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ShortestPathWithSkippableEdge {
    public static double solve(EdgeWeightedDigraph G, int s, int t, List<DirectedEdge> result) {
        DijkstraSP dijkstraSP = new DijkstraSP(G, s);
        if (!dijkstraSP.hasPathTo(t)) return -1;
        EdgeWeightedDigraph mirror = G.reverse();
        DijkstraSP mirrorDijkstraSP = new DijkstraSP(mirror, t);
        double ans = Double.POSITIVE_INFINITY;
        DirectedEdge bridge = null;
        for (DirectedEdge e : G.edges()) {
            double curPath = dijkstraSP.distTo(e.from()) + mirrorDijkstraSP.distTo(e.to());
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
        System.out.println("remove weight on " + bridge);
        result.addAll(postPart);
        System.out.println(result);
        return ans;
    }
}
