package week5.geoapp;

import commonutil.intervalTree.IntervalSTExp;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class IntervalSTTest {
    @Test
    public void randomTest() {
        int N = 3000;
        IntervalSearchTree<Double, Double> expect = new IntervalSTExp<>();
        IntervalSearchTree<Double, Double> test = new IntervalST<>();
        expect = new IntervalSTExp<>();
        test = new IntervalST<>();
        List<double[]> all = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            double p1 = Math.random();
            double p2 = Math.random();
            double low = Math.min(p1,p2);
            double high = Math.max(p1,p2);
            System.out.println(low + "," + high);
            expect.put(low, high, (double) i);
            test.put(low, high, (double) i);
            all.add(new double[]{low,high});
        }
        Assert.assertEquals(expect.size(), test.size());
        Assert.assertEquals(expect.check(), test.check());

        // generate random intervals and check for overlap
        Random ran = new Random();
        for (int i = 0; i < N; i++) {

            if (ran.nextBoolean()) {
                double p1 = Math.random();
                double p2 = Math.random();
                double low = Math.min(p1, p2);
                double high = Math.max(p1, p2);
                Set<Double> expectSet = new HashSet<>(), testSet = new HashSet<>();
                for (Double r : expect.intersects(low, high)) expectSet.add(r);
                for (Double r : test.intersects(low, high)) testSet.add(r);
                Assert.assertTrue(RectangleIntersectionTest.equals(expectSet, testSet));
            } else {
                double[] cur = all.remove(ran.nextInt(all.size()));
                Assert.assertEquals(expect.delete(cur[0], cur[1]),test.delete(cur[0], cur[1]));
                Assert.assertEquals(expect.size(), test.size());
                Assert.assertEquals(expect.check(), test.check());
            }
        }
    }
    @Test
    public void basicTest() {
        IntervalSearchTree<Integer, Double> expect = new IntervalSTExp<>();
        IntervalSearchTree<Integer, Double> test = new IntervalST<>();

        prepare(expect);
        prepare(test);

        Assert.assertEquals(expect.delete(-10,103), test.delete(-10, 103));

        //Assert.assertEquals(expect.height(), test.height());
        Assert.assertEquals(expect.size(), test.size());
        Assert.assertEquals(expect.check(), test.check());
        ArrayList<int[]> queries = new ArrayList<>();
        prepare(queries);

        for (int[] query : queries) {
            System.out.println("Query: " + Arrays.toString(query));
            Set<Double> expectSet = new HashSet<>(), testSet = new HashSet<>();
            for (Double r : expect.intersects(query[0], query[1])) expectSet.add(r);
            for (Double r : test.intersects(query[0], query[1])) testSet.add(r);

            Assert.assertTrue(RectangleIntersectionTest.equals(expectSet, testSet));
        }

    }

    private void prepare(ArrayList<int[]> queries) {
        queries.add(new int[]{-1, -1});
        queries.add(new int[]{0, 0});
        queries.add(new int[]{1, 1});
        queries.add(new int[]{2, 2});
        queries.add(new int[]{3, 3});
        queries.add(new int[]{4, 4});
        queries.add(new int[]{5, 5});
        queries.add(new int[]{6, 6});
        queries.add(new int[]{7, 7});
        queries.add(new int[]{10, 10});
        queries.add(new int[]{11, 11});
        queries.add(new int[]{12, 16});
        queries.add(new int[]{100, 100});
        queries.add(new int[]{200, 400});

        queries.add(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE});
        queries.add(new int[]{Integer.MIN_VALUE, -10000});
        queries.add(new int[]{Integer.MIN_VALUE, -1101});
        queries.add(new int[]{Integer.MIN_VALUE, -1100});
        queries.add(new int[]{Integer.MIN_VALUE, -999});
        queries.add(new int[]{Integer.MIN_VALUE, -899});
        queries.add(new int[]{-900, -899});
        queries.add(new int[]{-899, -800});

        queries.add(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE});
        queries.add(new int[]{10000, Integer.MAX_VALUE});
        queries.add(new int[]{1101, Integer.MAX_VALUE});
        queries.add(new int[]{1100, Integer.MAX_VALUE});
        queries.add(new int[]{1000, Integer.MAX_VALUE});
        queries.add(new int[]{900, Integer.MAX_VALUE});
        queries.add(new int[]{899, 900});
        queries.add(new int[]{898, 899});
    }

    private void prepare(IntervalSearchTree<Integer, Double> expect) {
        double intervalId = 0d;
        expect.put(1, 5, ++intervalId);
        expect.put(2, 5, ++intervalId);
        expect.put(3, 6, ++intervalId);
        expect.put(7, 7, ++intervalId);
        expect.put(4, 10, ++intervalId);
        expect.put(15, 20, ++intervalId);

        expect.put(0, 103, ++intervalId);
        expect.put(-10, 103, ++intervalId);
        expect.put(1000, Integer.MAX_VALUE, ++intervalId);
        expect.put(900, Integer.MAX_VALUE, ++intervalId);
        expect.put(1100, Integer.MAX_VALUE, ++intervalId);
        expect.put(Integer.MIN_VALUE, Integer.MAX_VALUE, ++intervalId);


    }
}