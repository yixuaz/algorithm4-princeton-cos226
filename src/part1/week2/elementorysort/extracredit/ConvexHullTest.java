package part1.week2.elementorysort.extracredit;

import commonutil.Shuffler;
import edu.princeton.cs.algs4.GrahamScan;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ConvexHullTest {
    @Test
    public void basicTest() {
        List<Point2D> expectInput = new ArrayList<>();
        expectInput.add(new Point2D(1, 1));
        expectInput.add(new Point2D(0, 0));
        expectInput.add(new Point2D(2, 2));
        List<Point2D> testInput = new ArrayList<>(expectInput);
        GrahamScan expect = new GrahamScan(expectInput.toArray(new Point2D[0]));
        List<Point2D> test = ConvexHull.solve(testInput);
        int i = 0;
        for (Point2D p : expect.hull()) {
            assertTrue(test.get(i).equals(p));
            i++;
        }
    }

    @Test
    public void basicTest2() {
        List<Point2D> expectInput = new ArrayList<>();
        expectInput.add(new Point2D(1, 1));
        expectInput.add(new Point2D(4, 1));
        expectInput.add(new Point2D(7, 5));
        expectInput.add(new Point2D(7, 6));
        expectInput.add(new Point2D(0, 8));
        expectInput.add(new Point2D(0, 2));
        List<Point2D> testInput = new ArrayList<>(expectInput);
        GrahamScan expect = new GrahamScan(expectInput.toArray(new Point2D[0]));
        List<Point2D> test = ConvexHull.solve(testInput);
        int i = 0;
        for (Point2D p : expect.hull()) {
            assertTrue(test.get(i).equals(p));
            i++;
        }
    }

    @Test
    public void randomTest() {
        boolean visualize = false;
        int n = 10;
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 99) n <<= 1;
            int base = n;
            int[] p = Shuffler.getRandomArray(2 * n, 0, base);
            List<Point2D> expectInput = new ArrayList<>();
            List<Point2D> testInput = new ArrayList<>();
            for (int j = 0; j < p.length; j += 2) {
                Point2D cur = visualize ? new Point2D(p[j] / base, p[j + 1] / base) : new Point2D(p[j], p[j + 1]);
                expectInput.add(cur);
                testInput.add(cur);
            }

            GrahamScan expect = new GrahamScan(expectInput.toArray(new Point2D[0]));
            List<Point2D> exp = new ArrayList<>();
            expect.hull().forEach(exp::add);
            System.out.println("expect:" + exp);
            List<Point2D> test = ConvexHull.solve(testInput);
            System.out.println("test:" + test);

            int idx = 0;
            for (int expectIdx = 0; expectIdx < exp.size(); expectIdx++) {
                if (!test.get(idx).equals(exp.get(expectIdx))) {
                    if (visualize) {
                        visualize(expectInput, n, exp, test);
                    }
                    if (checkThePointIsInLinear(test, idx)) {
                        expectIdx--;
                    } else if (checkThePointIsInLinear(exp, expectIdx)) {
                        continue;
                    } else {
                        assertTrue(false);
                    }
                }
                idx++;
            }
            System.out.println("pass " + i);
        }
    }

    private boolean checkThePointIsInLinear(List<Point2D> all, int idx) {
        int n = all.size();
        int pre = (idx + n - 1) % n, next = (idx + 1) % n;
        return ConvexHull.ccw(all.get(pre), all.get(idx), all.get(next)) == 0;
    }

    private void visualize(List<Point2D> expectInput, int n, List<Point2D> convex, List<Point2D> test) {
        while (true) {
            StdDraw.setPenColor(Color.red);
            StdDraw.setPenRadius(0.01);
            for (Point2D pp : expectInput) {
                pp.draw();
            }
            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(0.002);
            for (int i = 0; i < convex.size(); i++) {
                int next = (i + 1) % convex.size();
                Point2D pp = convex.get(i), ppnext = convex.get(next);
                pp.drawTo(ppnext);
            }
            StdDraw.setPenColor(Color.blue);
            StdDraw.setPenRadius(0.001);
            for (int i = 0; i < test.size(); i++) {
                int next = (i + 1) % test.size();
                Point2D pp = test.get(i), ppnext = test.get(next);
                pp.drawTo(ppnext);
            }
            StdDraw.show();
            if (StdDraw.isMousePressed()) {
                StdDraw.clear();
                break;
            }
        }
    }

}