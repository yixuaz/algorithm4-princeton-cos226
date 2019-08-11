package part2.week1.project.better;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SAP {
    private static final byte IS_LEN = 0;
    private static final byte IS_ANC = 1;

    private final int vertexCnt;
    private final int maxEdges;
    private final int[] adj;
    private final int[] vq, wq; // bfs queue for v and w
    private int vqSt, wqSt = 0;
    private int vqEd, wqEd = 0;
    private final Map<Integer, Integer> v2step, w2step; //  save calulated step for nde
    private final Map<Integer, Integer> lenCache, ancCache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        vertexCnt = G.V();
        vq = new int[vertexCnt];
        wq = new int[vertexCnt];
        v2step = new HashMap<>();
        w2step = new HashMap<>();
        lenCache = new HashMap<>();
        ancCache = new HashMap<>();
        int k = 0;
        for (int i = 0; i < vertexCnt; i++) {
            k = Math.max(G.outdegree(i), k);
        }
        maxEdges = k + 1;
        adj = new int[vertexCnt * maxEdges];
        for (int i = 0; i < vertexCnt; i++) {
            int idx = i * maxEdges;
            for (int v : G.adj(i))
                adj[idx++] = v;
            adj[idx] = -1;
        }
    }

    private int bfs(int pair, byte enm) {
        // begin & end for current iteration
        int vb = vqSt;
        int wb = wqSt;
        int ve = vqEd;
        int we = wqEd;

        int step = 1;
        int lenAns = vertexCnt;
        int ancAns = 0;
        int totalStep;
        while (vb != ve || wb != we) {
            if (step >= lenAns) break;
            for (int i = vb; i < ve; i++) {
                int j = vq[i] * maxEdges;
                for (int vh = adj[j]; vh != -1; vh = adj[++j]) {
                    if (v2step.containsKey(vh)) // already visited
                        continue;
                    vq[vqEd++] = vh;
                    v2step.put(vh, step);
                    totalStep = step + w2step.getOrDefault(vh, vertexCnt);
                    if (lenAns > totalStep) {
                        lenAns = totalStep;
                        ancAns = vh;
                    }
                }
            }
            for (int i = wb; i < we; i++) {
                int j = wq[i] * maxEdges;
                for (int wh = adj[j]; wh != -1; wh = adj[++j]) {
                    if (w2step.containsKey(wh)) // already visited
                        continue;
                    wq[wqEd++] = wh;
                    w2step.put(wh, step);
                    totalStep = step + v2step.getOrDefault(wh, vertexCnt);
                    if (lenAns > totalStep) {
                        lenAns = totalStep;
                        ancAns = wh;
                    }
                }
            }
            vb = ve;
            ve = vqEd;
            wb = we;
            we = wqEd;
            step++;
        }
        boolean hasAns = lenAns < vertexCnt;
        if (pair != 0) {
            lenCache.put(pair, hasAns ? lenAns : -1);
            ancCache.put(pair, hasAns ? ancAns : -1);
        }
        return hasAns ? (enm == IS_LEN ? lenAns : ancAns) : -1;
    }

    private void validate(Integer v) {
        if (v == null || v < 0 || v >= vertexCnt)
            throw new IllegalArgumentException();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v >= vertexCnt || w < 0 || w >= vertexCnt)
            throw new IllegalArgumentException();
        if (v == w)
            return 0;
        wqSt = 0;
        vqSt = 0;
        wqEd = 1;
        vqEd = 1;
        w2step.clear();
        v2step.clear();
        w2step.put(w, 0);
        v2step.put(v, 0);
        vq[0] = v;
        wq[0] = w;
        return bfs(0, IS_LEN);
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v >= vertexCnt || w < 0 || w >= vertexCnt)
            throw new IllegalArgumentException();
        if (v == w)
            return v;
        wqSt = 0;
        vqSt = 0;
        wqEd = 1;
        vqEd = 1;
        w2step.clear();
        v2step.clear();
        w2step.put(w, 0);
        v2step.put(v, 0);
        vq[0] = v;
        wq[0] = w;
        return bfs(0, IS_ANC);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vi, Iterable<Integer> wi) {
        if (vi == null || wi == null)
            throw new IllegalArgumentException();
        int v = System.identityHashCode(vi), w = System.identityHashCode(wi);
        int pair = v ^ w;
        if (lenCache.containsKey(pair))
            return lenCache.get(pair);
        wqSt = 0;
        vqSt = 0;
        wqEd = 0;
        vqEd = 0;
        w2step.clear();
        v2step.clear();
        for (Integer v0 : vi) {
            validate(v0);
            v2step.put(v0, 0);
            vq[vqEd++] = v0;
        }
        for (Integer w0 : wi) {
            validate(w0);
            if (v2step.containsKey(w0)) return 0;
            w2step.put(w0, 0);
            wq[wqEd++] = w0;
        }
        return bfs(pair, IS_LEN);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vi, Iterable<Integer> wi) {
        if (vi == null || wi == null)
            throw new IllegalArgumentException();
        int v = System.identityHashCode(vi), w = System.identityHashCode(wi);
        int pair = v ^ w;
        if (ancCache.containsKey(pair))
            return ancCache.get(pair);
        wqSt = 0;
        vqSt = 0;
        wqEd = 0;
        vqEd = 0;
        w2step.clear();
        v2step.clear();
        for (Integer v0 : vi) {
            validate(v0);
            v2step.put(v0, 0);
            vq[vqEd++] = v0;
        }
        for (Integer w0 : wi) {
            validate(w0);
            if (v2step.containsKey(w0)) return w0;
            w2step.put(w0, 0);
            wq[wqEd++] = w0;
        }
        return bfs(pair, IS_ANC);
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
