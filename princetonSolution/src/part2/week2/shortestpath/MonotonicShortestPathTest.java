package part2.week2.shortestpath;

import org.junit.Assert;
import org.junit.Test;
import part2.week2.shortestpath.msp.util.DirectedEdge;
import part2.week2.shortestpath.msp.util.EdgeWeightedDigraph;
import part2.week2.shortestpath.msp.util.MonotonicSP;

import java.util.LinkedList;
import java.util.Random;

public class MonotonicShortestPathTest extends MonotonicShortestPathAllPosEdgeTest{
    @Override
    public MonotonicSP getMSP() {
        return new MonotonicShortestPath();
    }

    @Test
    public void randomTestNegInc() {
        testBaseFunc(13,1000,true,true);
    }
    @Test
    public void randomTestNegDec() {
        testBaseFunc(13,1000,true,false);
    }

    @Test
    public void performanceTest() {
        Random r = new Random();
        int graphSize = 200;
        long time1 = 0, time2 = 0;
        // E is V ^ 2, so time diff should be 4 * before time
        for (int i = 0; i < 4; i++, graphSize <<= 1) {

            int V = graphSize, E = V * (V - 1) / 4;
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(V, E);
            LinkedList<DirectedEdge> res = new LinkedList<>();
            long st = System.nanoTime();
            double ans1 = getMSP().findDecrease(G, 0, V - 1, res);
            time1 += System.nanoTime() - st;

            st = System.nanoTime();
            double ans2 = new MonotonicShortestPathAllPosEdge().findDecrease(G, 0, V - 1, res);
            time2 += System.nanoTime() - st;
            Assert.assertEquals(0, Double.compare(ans1, ans2));
            System.out.println(time1+","+time2);
        }
    }

    @Test
    public void basicTest3() {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(4);
        G.addEdge(new DirectedEdge(0,2,-42));
        G.addEdge(new DirectedEdge(0,2,-5));
        G.addEdge(new DirectedEdge(1,0,-49));
        G.addEdge(new DirectedEdge(2,3,-33));
        G.addEdge(new DirectedEdge(2,3,-48));
        G.addEdge(new DirectedEdge(2,3,-42));
        G.addEdge(new DirectedEdge(3,2,-42));
        LinkedList<DirectedEdge> path = new LinkedList<>();
        System.out.println(getMSP().findDecrease(G, 0, 3, path));
        System.out.println(path);
        path.clear();
        dfs(0,path,G,0d,3,false);
        System.out.println(expectPath);
        System.out.println(pathSum);

    }


}