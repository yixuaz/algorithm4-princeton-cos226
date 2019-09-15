package part2.week2.shortestpath.extracredit;

import edu.princeton.cs.algs4.BellmanFordSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ArbitrageDetectionTest {
    @Test
    public void basicTest() {
        String[] money = {"USD", "EUR", "GBP", "CHF", "CAD"};
        double[][] input = {{1, 0.741, 0.657, 1.061, 1.011},
                {1.35, 1, 0.888, 1.433, 1.366},
                {1.521, 1.126, 1, 1.614, 1.538},
                {0.943, 0.698, 0.620, 1, 0.953},
                {0.995, 0.732, 0.65, 1.049, 1}};
        List<DirectedEdge> result = expect(input);
        Assert.assertEquals(!result.isEmpty(), ArbitrageDetection.solve(input));

        double cur = 1000;
        for (DirectedEdge edge : result) {
            System.out.println(cur + money[edge.from()] + "->"
                    + cur * input[edge.from()][edge.to()] + money[edge.to()]);
            cur =  cur * input[edge.from()][edge.to()];
        }
        assertTrue(cur > 1000);
    }

    @Test
    public void randomTest() {
        int n = 2;
        for (int i = 0; i < 80; i++) {
            if (i % 10 == 9) n <<= 1;
            double[][] input = new double[n][n];
            for (int j = 0; j < n; j++) input[j][j] = 1;
            for (int j = n - 2; j >= 0; j--) {
                for (int k = j + 1; k < n; k++) {
                    input[j][k] = Math.random();
                    input[k][j] = (1.0 / Math.random());// + Math.random();
                }
            }
            boolean hasAribitrage = !expect(input).isEmpty();
            System.out.println("pass" + i + hasAribitrage);
            assertEquals(hasAribitrage, ArbitrageDetection.solve(input));
        }
    }


    private static List<DirectedEdge> expect(double[][] exchangeRates) {
        int v = exchangeRates.length;
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(v + 1);
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                graph.addEdge(new DirectedEdge(i, j, -Math.log(exchangeRates[i][j])));
            }
        }
        for (int i = 0; i < v; i++) {
            graph.addEdge(new DirectedEdge(v, i, 0));
        }
        BellmanFordSP sp = new BellmanFordSP(graph, v);
        if (!sp.hasNegativeCycle()) {
            return Collections.emptyList();
        }
        List<DirectedEdge> res = new ArrayList<>();
        sp.negativeCycle().forEach(res::add);
        return res;
    }

}