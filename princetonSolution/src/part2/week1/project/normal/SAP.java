package part2.week1.project.normal;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private static final int GET_MIN_LEN = 0;
    private static final int GET_SCA = 1;
    private final Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validate(v, w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        return calMinLengthAndSCA(bfsV, bfsW)[GET_MIN_LEN];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validate(v, w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        return calMinLengthAndSCA(bfsV, bfsW)[GET_SCA];
    }

    private int[] calMinLengthAndSCA(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int minLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (bfsV.distTo(i) + bfsW.distTo(i) < minLength) {
                    minLength = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        if (minLength == Integer.MAX_VALUE) {
            minLength = -1;
        }
        return new int[]{minLength, ancestor};
    }

    private void validate(int a, int b) {
        if (a < 0 || a >= digraph.V() || b < 0 || b >= digraph.V())
            throw  new IllegalArgumentException("input out of range");
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        return calMinLengthAndSCA(bfsV, bfsW)[GET_MIN_LEN];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validate(v, w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        return calMinLengthAndSCA(bfsV, bfsW)[GET_SCA];
    }

    private void validate(Iterable<Integer> v, Iterable<Integer> w) {
        String info = "input should not be null";
        if (v == null || w == null)
            throw new IllegalArgumentException(info);
        for (Integer i : v) {
            if (i == null)
                throw new IllegalArgumentException(info);
        }
        for (Integer i : w) {
            if (i == null)
                throw new IllegalArgumentException(info);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
