package part2.week2.shortestpath.extracredit;

import edu.princeton.cs.algs4.AcyclicLP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.util.List;

public class ParallelJobScheduling {
    public static void solve(List<Job> jobs) {
        int v = jobs.size();
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(v + 2);
        int src = v, tar = v + 1;
        for (Job job : jobs) {
            graph.addEdge(new DirectedEdge(src, job.id, 0));
            graph.addEdge(new DirectedEdge(job.id, tar, 0));
            for (int after : job.completeBefore) {
                graph.addEdge(new DirectedEdge( job.id, after, job.duration));
            }
        }
        AcyclicLP lp = new AcyclicLP(graph, src);
        for (Job job : jobs) {
            job.startTime = (int) lp.distTo(job.id);
        }
    }
}
