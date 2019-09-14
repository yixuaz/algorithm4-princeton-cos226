package part2.week1.project.better;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class SAP {
    private final int vertexCnts; // number of vertices in this digraph
    private final int[] adj; // convert digraph to int[]
    private final int[] vQueue, wQueue; // bidirectionalBFS queue for v and w
    private final int[] vNodeToSteps, wNodeToSteps; //  save calulated step for nde
    private int vQueueSize = 0, wQueueSize = 0;
    private int lastV = 0, lastW = 0;
    private int lastAnc = 0, lastLen = 0;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        vertexCnts = G.V();
        vQueue = new int[vertexCnts];
        wQueue = new int[vertexCnts];
        vNodeToSteps = new int[vertexCnts];
        wNodeToSteps = new int[vertexCnts];

        // first V + 1 position, save the adj start position and end position
        // eg. vertex 0's outdegree edge is saved in, adj[0]~adj[1]
        adj = new int[vertexCnts + G.E() + 2];
        adj[0] = vertexCnts + 1;
        int idx = vertexCnts + 1;
        for (int i = 0; i < vertexCnts; i++) {
            vNodeToSteps[i] = -1;
            wNodeToSteps[i] = -1;
            adj[i + 1] = adj[i] + G.outdegree(i);
            for (int v : G.adj(i))
                adj[idx++] = v;
        }
    }

    private void bidirectionalBFS() {
        // begin & end for current iteration
        int vQueueBeginIdx = 0;
        int wQueueBeginIdx = 0;
        int vQueueEndIdx = vQueueSize;
        int wQueueEndIdx = wQueueSize;

        int step = 1;
        int lenAns = vertexCnts;
        int ancAns = 0;
        int totalStep;
        while (vQueueBeginIdx < vQueueEndIdx || wQueueBeginIdx < wQueueEndIdx) {
            for (int i = vQueueBeginIdx; i < vQueueEndIdx && step < lenAns; i++) {
                int curValInVQueue = vQueue[i], adjBegin = adj[curValInVQueue], adjEnd = adj[curValInVQueue + 1];
                for (int j = adjBegin; j < adjEnd && step < lenAns; j++) {
                    int neighbor = adj[j];
                    if (vNodeToSteps[neighbor] != -1) // already visited
                        continue;
                    vQueue[vQueueSize++] = neighbor;
                    vNodeToSteps[neighbor] = step;
                    if (wNodeToSteps[neighbor] != -1) {
                        totalStep = step + wNodeToSteps[neighbor];
                        if (lenAns > totalStep) {
                            lenAns = totalStep;
                            ancAns = neighbor;
                        }
                    }
                }
            }
            for (int i = wQueueBeginIdx; i < wQueueEndIdx && step < lenAns; i++) {
                int curValInWQueue = wQueue[i], adjBegin = adj[curValInWQueue], adjEnd = adj[curValInWQueue + 1];
                for (int j = adjBegin; j < adjEnd && step < lenAns; j++) {
                    int neighbor = adj[j];
                    if (wNodeToSteps[neighbor] != -1) // already visited
                        continue;
                    wQueue[wQueueSize++] = neighbor;
                    wNodeToSteps[neighbor] = step;
                    if (vNodeToSteps[neighbor] != -1) {
                        totalStep = step + vNodeToSteps[neighbor];
                        if (lenAns > totalStep) {
                            lenAns = totalStep;
                            ancAns = neighbor;
                        }
                    }
                }
            }
            vQueueBeginIdx = vQueueEndIdx;
            vQueueEndIdx = vQueueSize;
            wQueueBeginIdx = wQueueEndIdx;
            wQueueEndIdx = wQueueSize;
            step++;
        }
        if (lenAns < vertexCnts) {
            lastLen = lenAns;
            lastAnc = ancAns;
        } else {
            lastLen = -1;
            lastAnc = -1;
        }
    }

    private void validate(Integer v) {
        if (v == null || v < 0 || v >= vertexCnts)
            throw new IllegalArgumentException();
    }

    private void cleanNodeToStepsMap() {
        for (int i = 0; i < wQueueSize; i++) {
            wNodeToSteps[wQueue[i]] = -1;
        }
        for (int i = 0; i < vQueueSize; i++) {
            vNodeToSteps[vQueue[i]] = -1;
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v >= vertexCnts || w < 0 || w >= vertexCnts)
            throw new IllegalArgumentException();
        if (v == w)
            return 0;
        if (lastV == v && lastW == w)
            return lastLen;
        lastV = v;
        lastW = w;
        cleanNodeToStepsMap();
        setupQueueAndNodeToStepsMap(v, w);
        bidirectionalBFS();
        return lastLen;
    }

    private void setupQueueAndNodeToStepsMap(int v, int w) {
        wQueueSize = 1;
        vQueueSize = 1;
        wNodeToSteps[w] = 0;
        vNodeToSteps[v] = 0;
        vQueue[0] = v;
        wQueue[0] = w;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    // -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v >= vertexCnts || w < 0 || w >= vertexCnts)
            throw new IllegalArgumentException();
        if (v == w)
            return v;
        if (lastV == v && lastW == w)
            return lastAnc;
        lastV = v;
        lastW = w;
        cleanNodeToStepsMap();
        setupQueueAndNodeToStepsMap(v, w);
        bidirectionalBFS();
        return lastAnc;
    }

    // length of shortest ancestral path
    // between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vi, Iterable<Integer> wi) {
        if (vi == null || wi == null)
            throw new IllegalArgumentException();
        int v = System.identityHashCode(vi), w = System.identityHashCode(wi);
        if (lastV == v && lastW == w)
            return lastLen;
        cleanNodeToStepsMap();
        if (setupQueueAndNodeToStepsMap(vi, wi)) {
            bidirectionalBFS();
        }
        lastV = v;
        lastW = w;
        return lastLen;
    }

    // a common ancestor that participates in shortest ancestral path;
    // -1 if no such path
    public int ancestor(Iterable<Integer> vi, Iterable<Integer> wi) {
        if (vi == null || wi == null)
            throw new IllegalArgumentException();
        int v = System.identityHashCode(vi), w = System.identityHashCode(wi);
        if (lastV == v && lastW == w)
            return lastAnc;
        cleanNodeToStepsMap();
        if (setupQueueAndNodeToStepsMap(vi, wi)) {
            bidirectionalBFS();
        }
        lastV = v;
        lastW = w;
        return lastAnc;
    }

    // if already find ans, return false;
    private boolean setupQueueAndNodeToStepsMap(Iterable<Integer> vi, Iterable<Integer> wi) {
        wQueueSize = 0;
        vQueueSize = 0;
        for (Integer v0 : vi) {
            validate(v0);
            vNodeToSteps[v0] = 0;
            vQueue[vQueueSize++] = v0;
        }
        for (Integer w0 : wi) {
            validate(w0);
            if (vNodeToSteps[w0] == 0) {
                lastLen = 0;
                lastAnc = w0;
                return false; // find ans, then no need do BFS
            }
            wNodeToSteps[w0] = 0;
            wQueue[wQueueSize++] = w0;
        }
        return true;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        SAP sap = new SAP(new Digraph(new In("digraph1.txt")));
        StdOut.println(sap.length(3, 11)); // 4
        StdOut.println(sap.ancestor(3, 11)); // 1
        ArrayList<Integer> v = new ArrayList<Integer>();
        ArrayList<Integer> w = new ArrayList<Integer>();
        v.add(3);
        v.add(7);
        v.add(8);
        w.add(2);
        w.add(11);
        StdOut.println(sap.length(v, w)); // 3
        StdOut.println(sap.ancestor(v, w)); // 0
        sap = new SAP(new Digraph(new In("digraph2.txt")));
        StdOut.println(sap.length(1, 5)); // 2
        StdOut.println(sap.ancestor(1, 5)); // 0
    }
}
