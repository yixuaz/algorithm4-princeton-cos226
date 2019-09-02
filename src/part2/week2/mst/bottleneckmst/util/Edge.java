package part2.week2.mst.bottleneckmst.util;

import edu.princeton.cs.algs4.StdOut;

public class Edge implements Comparable<Edge> {
    protected int v;
    protected int w;
    private final double weight;
    protected final int originV;
    protected final int originW;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double weight) {
        if (v < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (w < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.originV = v;
        this.originW = w;
        this.weight = weight;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return this.v;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if (vertex == this.v) {
            return this.w;
        } else if (vertex == this.w) {
            return this.v;
        } else {
            throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    public int w() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int v() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        if (Double.compare(this.weight, that.weight) == 0) {
            if (Integer.compare(this.v, that.v) == 0) {
                return Integer.compare(this.w, that.w);
            }
            return Integer.compare(this.v, that.v);
        }
        return Double.compare(this.weight, that.weight);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", originV, originW, weight);
    }

    /**
     * Unit tests the {@code Edge} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        edu.princeton.cs.algs4.Edge e = new edu.princeton.cs.algs4.Edge(12, 34, 5.67);
        StdOut.println(e);
    }
}
