package part2.week2.shortestpath.msp.util;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;                // number of vertices in this digraph
    private int E;                      // number of edges in this digraph
    private List<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
    private Vertex[] vertices;
    private int[] indegree;             // indegree[v] = indegree of vertex v

    private Map<DirectedEdge, DirectedEdge> rev2Ori = new HashMap<>();

    public EdgeWeightedDigraph reverse() {
        EdgeWeightedDigraph reverse = new EdgeWeightedDigraph(V);
        for (DirectedEdge edge : edges()) {
            DirectedEdge rev = new DirectedEdge(edge.to(),edge.from(),edge.weight());
            reverse.addEdge(rev);
            rev2Ori.put(rev, edge);
        }
        return reverse;
    }
    public DirectedEdge getOri(DirectedEdge rev) {
        if (!rev2Ori.containsKey(rev)) throw new IllegalArgumentException("Rev not found");
        return rev2Ori.get(rev);
    }

    public class Vertex {
        private int idx;
        public int pos = 0;
        public DirectedEdge backEdge;
        public DirectedEdge secPathbackEdge;
        public double minPathSum = Double.POSITIVE_INFINITY;
        public double secPathSum = Double.POSITIVE_INFINITY;
        public Vertex(int idx) {
            this.idx = idx;
        }
        public List<DirectedEdge> outEdge() {
            return adj[idx];
        }

        public double getWeight(boolean increase) {
            if (backEdge == null) return increase ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            return backEdge.weight();
        }
        public double getSecWeight(boolean increase) {
            if (secPathbackEdge == null) return increase ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            return secPathbackEdge.weight();
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "pos=" + pos +
                    ", backEdge=" + backEdge +
                    '}';
        }
    }
    /**
     * Initializes an empty edge-weighted digraph with {@code V} vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */

    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = (List<DirectedEdge>[]) new List[V];
        vertices = new Vertex[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
            vertices[v] = new Vertex(v);
        }
    }

    /**
     * Initializes a random edge-weighted digraph with {@code V} vertices and <em>E</em> edges.
     *
     * @param  V the number of vertices
     * @param  E the number of edges
     * @throws IllegalArgumentException if {@code V < 0}
     * @throws IllegalArgumentException if {@code E < 0}
     */
    public EdgeWeightedDigraph(int V, int E, boolean allowNegative) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            while (w == v) {
                w = StdRandom.uniform(V);
            }
            double weight = (allowNegative ? -50 : 0) + StdRandom.uniform(100);
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }
    public EdgeWeightedDigraph(int V, int E) {
        this(V, E, false);
    }

    /**
     * Initializes an edge-weighted digraph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param  in the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            validateVertex(v);
            validateVertex(w);
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    /**
     * Initializes a new edge-weighted digraph that is a deep copy of {@code G}.
     *
     * @param  G the edge-weighted digraph to copy
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++)
            this.indegree[v] = G.indegree(v);
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
            for (DirectedEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                adj[v].add(e);
            }
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between {@code 0}
     *         and {@code V-1}
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        indegree[w]++;
        E++;
    }


    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public List<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    public Vertex getVertex(int i) {
        return vertices[i];
    }
    public void sortIncrease() {
        for (int i = 0; i < V; i++)
            Collections.sort(adj[i], (a,b)->Double.compare(a.weight(), b.weight()));
    }
    public void sortDecrease() {
        for (int i = 0; i < V; i++)
            Collections.sort(adj[i], (a,b)->Double.compare(b.weight(), a.weight()));
    }




    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * {@code for (DirectedEdge e : G.edges())}.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public List<DirectedEdge> edges() {
        List<DirectedEdge> list = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Unit tests the {@code EdgeWeightedDigraph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        edu.princeton.cs.algs4.EdgeWeightedDigraph G = new edu.princeton.cs.algs4.EdgeWeightedDigraph(in);
        StdOut.println(G);
    }

}
