package part2.week3.maxflow;

import org.junit.Assert;
import org.junit.Test;
import part2.week3.maxflow.maxweightclosure.util.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MaxWeightClosureTest {
    @Test
    public void allPositiveTest() {
        Vertex vertex0 = new Vertex(0, 8);
        Vertex vertex1 = new Vertex(1, 9);
        Vertex vertex2 = new Vertex(2, 20);
        Vertex vertex3 = new Vertex(3, 12);
        List<Vertex> vertices = Arrays.asList(vertex0, vertex1, vertex2,
                vertex3);
        Set<Vertex> ans = new HashSet<>();
        Assert.assertEquals(49, MaxWeightClosure.solve(vertices, ans));
        System.out.println(ans);
    }

    @Test
    public void allNegativeTest() {
        Vertex vertex0 = new Vertex(0, -8);
        Vertex vertex1 = new Vertex(1, -9);
        Vertex vertex2 = new Vertex(2, -20);
        Vertex vertex3 = new Vertex(3, -12);
        vertex1.addEdge(vertex0);
        vertex2.addEdge(vertex0);
        vertex3.addEdge(vertex0);
        List<Vertex> vertices = Arrays.asList(vertex0, vertex1, vertex2,
                vertex3);
        Set<Vertex> ans = new HashSet<>();
        Assert.assertEquals(0, MaxWeightClosure.solve(vertices, ans));
        System.out.println(ans);
    }

    @Test
    public void basicTest() {
        Vertex vertex0 = new Vertex(0, 8);
        Vertex vertex1 = new Vertex(1, -9);
        Vertex vertex2 = new Vertex(2, -20);
        Vertex vertex3 = new Vertex(3, 12);
        Vertex vertex4 = new Vertex(4, -10);
        Vertex vertex5 = new Vertex(5, 5);
        vertex0.addEdge(vertex1);
        vertex0.addEdge(vertex3);
        vertex1.addEdge(vertex4);
        vertex1.addEdge(vertex5);
        vertex3.addEdge(vertex4);
        vertex2.addEdge(vertex3);
        List<Vertex> vertices = Arrays.asList(vertex0, vertex1, vertex2,
                vertex3, vertex4, vertex5);
        Set<Vertex> ans = new HashSet<>();
        Assert.assertEquals(7, MaxWeightClosure.solve(vertices, ans));
        System.out.println(ans);
    }

    @Test
    public void randomTest() {
        int V = 4;
        for (int i = 0; i < 150; i++) {
            if (i % 10 == 9) V++;
            List<Vertex> vertices = buildRandom(V);
            int expect = bruteForce(vertices);
            Set<Vertex> ans = new HashSet<>();
            assertEquals(expect, MaxWeightClosure.solve(vertices, ans));
            assertEquals(expect, check(ans, vertices));
            System.out.println(ans);
        }
    }

    private int bruteForce(List<Vertex> vertices) {
        return dfs(vertices, 0, 0, new HashSet<Vertex>());
    }

    private int dfs(List<Vertex> vertices, int curIdx, int sum, Set<Vertex> maxClosure) {
        if (curIdx == vertices.size()) {
            for (Vertex in : maxClosure) {
                for (Vertex v : in.adj()) {
                    if (!maxClosure.contains(v)) return Integer.MIN_VALUE;
                }
            }
            return sum;
        }
        Vertex nowVertex = vertices.get(curIdx);
        int ans1 = dfs(vertices, curIdx + 1, sum, maxClosure);
        maxClosure.add(nowVertex);
        int ans2 = dfs(vertices, curIdx + 1, sum + nowVertex.weight(), maxClosure);
        maxClosure.remove(nowVertex);
        return Math.max(ans1, ans2);
    }

    private int check(Set<Vertex> ans, List<Vertex> vertices) {
        int sum = 0;
        for (Vertex v : vertices) {
            if (ans.contains(v)) {
                sum += v.weight();
            } else {
                // check closure not have an edge to vertex not in closure
                for (Vertex in : ans) {
                    Assert.assertTrue(!in.adj().contains(v));
                }
            }
        }
        return sum;
    }

    private List<Vertex> buildRandom(int v) {
        Random r = new Random();
        List<Vertex> res = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            Vertex cur = new Vertex(i, -25 + r.nextInt(50));
            res.add(cur);
        }
        int edges = r.nextInt(v * (v - 1) / 2);
        for (int i = 0; i < edges; i++) {
            int from = r.nextInt(v), to = r.nextInt(v);
            res.get(from).addEdge(res.get(to));
        }
        return res;
    }

}