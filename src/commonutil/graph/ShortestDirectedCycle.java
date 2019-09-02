package commonutil.graph;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  Compilation:  javac ShortestDirectedCycle.java
 *  Execution:    java DirectedCycle < input.txt
 *  Dependencies: Digraph.java BreadthFirstDirectedPaths.java Stack.java StdOut.java In.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *                https://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
 *
 *  Finds a shortest directed cycle in a digraph.
 *  Runs in O(V * (E + V)) time.
 *
 *  % java ShortestDirectedCycle tinyDG.txt
 *  Shortest directed cycle: 2 3 2
 *
 *  %  java ShortestDirectedCycle tinyDAG.txt
 *  No directed cycle
 *
 ******************************************************************************/

public class ShortestDirectedCycle {
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)
    private int length;

    public ShortestDirectedCycle(Digraph G) {
        Digraph R = G.reverse();
        length = G.V() + 1;
        for (int v = 0; v < G.V(); v++) {
            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(R, v);
            for (int w : G.adj(v)) {
                if (bfs.hasPathTo(w) && (bfs.distTo(w) + 1) < length) {
                    length = bfs.distTo(w) + 1;
                    cycle = new Stack<Integer>();
                    for (int x : bfs.pathTo(w))
                        cycle.push(x);
                    cycle.push(v);
                }
            }
        }
    }


    public boolean hasCycle()        { return cycle != null;   }
    public Iterable<Integer> cycle() { return cycle;           }
    public int length()              { return length;          }

    public static void main(String[] args) {
        Digraph G;
        if (args.length == 1) {
            In in = new In(args[0]);
            G = new Digraph(in);
        }
        else {
            int V = Integer.parseInt(args[0]);
            int E = Integer.parseInt(args[1]);
            G = DigraphGenerator.simple(V, E);
        }

        ShortestDirectedCycle finder = new ShortestDirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Shortest directed cycle: ");
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

        else {
            StdOut.println("No directed cycle");
        }
    }

}
