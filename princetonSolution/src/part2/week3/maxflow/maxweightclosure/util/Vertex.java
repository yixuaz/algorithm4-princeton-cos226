package part2.week3.maxflow.maxweightclosure.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
    private int idx;
    private int weight;
    private final Set<Vertex> adjs;
    public Vertex(int idx, int weight) {
        this.idx = idx;
        this.weight = weight;
        adjs = new HashSet<>();
    }
    public void addEdge(Vertex to) {
        adjs.add(to);
    }
    public Set<Vertex> adj() {
        return Collections.unmodifiableSet(adjs);
    }
    public int weight() {
        return weight;
    }
    public int idx() {
        return idx;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "idx=" + idx +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return idx == vertex.idx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx);
    }
}
