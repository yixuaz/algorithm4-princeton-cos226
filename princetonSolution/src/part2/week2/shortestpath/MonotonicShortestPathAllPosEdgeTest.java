package part2.week2.shortestpath;


import org.junit.Assert;
import org.junit.Test;
import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.msp.util.MonotonicSP;

import java.util.LinkedList;
import java.util.Random;

public class MonotonicShortestPathAllPosEdgeTest {

    public MonotonicSP getMSP() {
        return new MonotonicShortestPathAllPosEdge();
    }

    @Test
    public void basicTest() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
        G.addEdge(new DirectedEdge(0, 1, 1));
        G.addEdge(new DirectedEdge(0, 2, 4));
        G.addEdge(new DirectedEdge(0, 3, 9));
        G.addEdge(new DirectedEdge(1, 2, 2));
        G.addEdge(new DirectedEdge(1, 3, 6));
        G.addEdge(new DirectedEdge(2, 3, 3));
        G.addEdge(new DirectedEdge(3, 4, 4));
        G.addEdge(new DirectedEdge(3, 6, 8));
        G.addEdge(new DirectedEdge(4, 5, 5));
        G.addEdge(new DirectedEdge(5, 3, 7));
        G.addEdge(new DirectedEdge(5, 6, 6));
        G.addEdge(new DirectedEdge(6, 2, 5));
        G.addEdge(new DirectedEdge(6, 7, 7));
        G.addEdge(new DirectedEdge(7, 5, 9));
        LinkedList<DirectedEdge> path = new LinkedList<>();
        System.out.println(getMSP().findDecrease(G, 0, 7, path));
        System.out.println(path);
    }

    @Test
    public void basicTest2() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(4);
        G.addEdge(new DirectedEdge(0, 1, 3));
        G.addEdge(new DirectedEdge(1, 2, 4));
        G.addEdge(new DirectedEdge(0, 2, 6));
        G.addEdge(new DirectedEdge(2, 3, 6));
        LinkedList<DirectedEdge> path = new LinkedList<>();
        System.out.println(getMSP().findIncrease(G, 0, 3, path));
        System.out.println(path);
    }

    @Test
    public void basicTest3() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(5);
        G.addEdge(new DirectedEdge(0, 1, 3));
        G.addEdge(new DirectedEdge(1, 2, 4));
        G.addEdge(new DirectedEdge(2, 3, 5));
        G.addEdge(new DirectedEdge(0, 3, 7));
        G.addEdge(new DirectedEdge(3, 4, 6));
        LinkedList<DirectedEdge> path = new LinkedList<>();
        System.out.println(getMSP().findDecrease(G, 0, 4, path));
        System.out.println(path);
    }

    @Test
    public void randomTestDec() {
        testBaseFunc(13, 1000, false, false);
    }

    @Test
    public void randomTestInc() {
        testBaseFunc(12, 1000, false, true);
    }

    protected void testBaseFunc(int graphSize, int forloops, boolean isAllowNegativeNumber, boolean increase) {
        Random r = new Random();
        for (int i = 0; i < forloops; i++) {
            // initial global state
            expectPath = new LinkedList<>();
            pathSum = Double.POSITIVE_INFINITY;

            // random generate graph
            int V = 2 + r.nextInt(graphSize), E = r.nextInt(V * (V - 1));
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(V, E);

            // test func
            LinkedList<DirectedEdge> test = new LinkedList<>();
            double testSum = increase ? getMSP().findIncrease(G, 0, V - 1, test)
                    : getMSP().findDecrease(G, 0, V - 1, test);

            // expect func
            dfs(0, new LinkedList<>(), G, 0, V - 1, increase);

            // verify path sum should be same
            Assert.assertTrue(expectPath.toString() + "\n" + test.toString(),
                    Double.compare(pathSum, testSum) == 0);
            verifyPath(test, increase);

            System.out.println("pass " + i);
        }
    }

    private void verifyPath(LinkedList<DirectedEdge> test, boolean increase) {
        if (!expectPath.equals(test)) { // path could be diff, but sum should be correct.
            double sum = 0;
            double preWeight = increase ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            int expectOrder = increase ? 1 : -1;
            for (DirectedEdge e : test) {
                Assert.assertEquals(expectPath.toString() + "\n" + test.toString(),
                        expectOrder,
                        Double.compare(e.weight(), preWeight));
                preWeight = e.weight();
                sum += e.weight();
            }
            Assert.assertEquals(expectPath.toString() + "\n" + test.toString(),
                    0, Double.compare(pathSum, sum));
        }
    }


    LinkedList<DirectedEdge> expectPath = new LinkedList<>();
    double pathSum = Double.POSITIVE_INFINITY;

    protected void dfs(int cur, LinkedList<DirectedEdge> curAns, EdgeWeightedDigraph graph, double curPathSum,
                       int tar, boolean increase) {
        if (cur == tar) {
            if (Double.compare(curPathSum, pathSum) < 0) {
                expectPath = new LinkedList<>(curAns);
                pathSum = curPathSum;
            }
        }
        for (DirectedEdge nei : graph.adj(cur)) {
            boolean valid = false;
            if (increase) {
                valid = curAns.isEmpty() || Double.compare(nei.weight(), curAns.getLast().weight()) > 0;
            } else {
                valid = curAns.isEmpty() || Double.compare(nei.weight(), curAns.getLast().weight()) < 0;
            }
            if (valid) {
                curAns.add(nei);
                dfs(nei.to(), curAns, graph, curPathSum + nei.weight(), tar, increase);
                curAns.removeLast();
            }
        }
    }


}