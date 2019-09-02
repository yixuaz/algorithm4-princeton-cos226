package part1.week5.geoapp;

import commonutil.intervalTree.IntervalSTExp;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RectangleIntersectionTest {
    @Test
    public void basicTest1() { // two intersect
        RectHV rect1 = new RectHV(0,0,2,2);
        RectHV rect2 = new RectHV(1,1,3,3);
        Set<RectHV> input = new HashSet<>(Arrays.asList(rect1, rect2));
        Map<RectHV, Set<RectHV>> res = RectangleIntersection.findAllIntersection(input);
        Set<RectHV> have1 = new HashSet<>(Arrays.asList(rect1));
        Set<RectHV> have2 = new HashSet<>(Arrays.asList(rect2));
        Assert.assertTrue(equals(res.get(rect1), have2));
        Assert.assertTrue(equals(res.get(rect2), have1));
    }

    @Test
    public void basicTest2() { // two not intersect
        RectHV rect1 = new RectHV(0,0,2,2);
        RectHV rect2 = new RectHV(3,3,4,4);
        Set<RectHV> input = new HashSet<>(Arrays.asList(rect1, rect2));
        Map<RectHV, Set<RectHV>> res = RectangleIntersection.findAllIntersection(input);
        Set<RectHV> empty = new HashSet<>();
        Assert.assertTrue(equals(res.get(rect1), empty));
        Assert.assertTrue(equals(res.get(rect2), empty));
    }

    @Test
    public void basicTest3() {
        RectHV rect1 = new RectHV(0,1,7,2);
        RectHV rect2 = new RectHV(1,0,2,3);
        RectHV rect3 = new RectHV(3,0,4,3);
        RectHV rect4 = new RectHV(-2,-2,-1,-1);
        Set<RectHV> input = new HashSet<>(Arrays.asList(rect1, rect2, rect3, rect4));
        Map<RectHV, Set<RectHV>> res = RectangleIntersection.findAllIntersection(input);
        Set<RectHV> have1 = new HashSet<>(Arrays.asList(rect2,rect3));
        Set<RectHV> have23 = new HashSet<>(Arrays.asList(rect1));
        Set<RectHV> empty = new HashSet<>();
        Assert.assertTrue(equals(res.get(rect1), have1));
        Assert.assertTrue(equals(res.get(rect2), have23));
        Assert.assertTrue(equals(res.get(rect3), have23));
        Assert.assertTrue(equals(res.get(rect4), empty));
    }
    @Test
    public void basicTest4() {
        RectHV rect1 = new RectHV(0.3575171210237521,0.7146836650768305,
                0.654698890179744,0.7389769277103051);
        RectHV rect2 = new RectHV(0.5242190274474139,0.604704260298527,
                0.5984919519775737,0.7351331826091808);
        RectHV rect3 = new RectHV(0.26932805480982835,0.6878393553752091,
                0.6415024527196731,0.7007551209727795);
        RectHV rect4 = new RectHV(0.6194634824768956,0.6978808556408181,
                0.6985437759233379,0.7976011872680293);
        Set<RectHV> input = new HashSet<>(Arrays.asList(rect1, rect2, rect3, rect4));
        Map<RectHV, Set<RectHV>> res = RectangleIntersection.findAllIntersection(input);
        Set<RectHV> have13 = new HashSet<>(Arrays.asList(rect2,rect4));
        Set<RectHV> have2 = new HashSet<>(Arrays.asList(rect1,rect3));
        Set<RectHV> have4 = new HashSet<>(Arrays.asList(rect1,rect3));

        Assert.assertTrue(equals(res.get(rect1), have13));
        Assert.assertTrue(equals(res.get(rect2), have2));
        Assert.assertTrue(equals(res.get(rect3), have13));
        Assert.assertTrue(equals(res.get(rect4), have4));
    }

    @Test
    public void randomTest() {
        int N = 2000;
        Set<RectHV> input = generateRect(N);
        IntervalSearchTree<Double, RectHV> invST = new IntervalST<>();
        Map<RectHV, Set<RectHV>> test = RectangleIntersection.findAllIntersection(input,
                invST);
        for (RectHV cur : input) {
            Set<RectHV> expect = new HashSet<>();
            for (RectHV k : input) {
                if (k == cur) continue;
                if (k.intersects(cur)) expect.add(k);
            }
            Assert.assertTrue(equals(expect, test.get(cur)));
        }
    }

    public static boolean equals(Set<?> set1, Set<?> set2){
        if(set1 == null || set2 == null){
            return false;
        }
        if(set1.size() != set2.size()){
            return false;
        }
        return set1.containsAll(set2);
    }


    @Test
    @Ignore
    public void visualize() {
        int N = 50;
        Set<RectHV> input = generateRect(N);
        for (RectHV rect : input) rect.draw();
        StdDraw.enableDoubleBuffering();
        IntervalSearchTree<Double, RectHV> invST = new IntervalST<>();
        Map<RectHV, Set<RectHV>> res = RectangleIntersection.findAllIntersection(input,
                invST);
        boolean find = false;
        while (true) {
            if (StdDraw.isMousePressed()) {
                if (find) {
                    StdDraw.clear();
                    StdDraw.setPenColor(Color.black);
                    for (RectHV rect : input) rect.draw();
                }
                find = false;
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                for (RectHV rect : input) {
                    if (Math.abs(rect.xmin() - x) < 0.02 && Math.abs(rect.ymin() - y) < 0.02) {
                        System.out.println(rect);
                        find = true;
                        StdDraw.setPenColor(Color.red);
                        rect.draw();
                        StdDraw.setPenColor(Color.blue);
                        for (RectHV intersect : res.get(rect)) intersect.draw();
                        StdDraw.show();
                        StdDraw.pause(50);
                        break;
                    }
                }
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                if (invST instanceof IntervalST) {
                    System.out.println("use exp");
                    invST = new IntervalSTExp<>();
                } else {
                    System.out.println("use test");
                    invST = new IntervalST<>();
                }
                res = RectangleIntersection.findAllIntersection(input,
                        invST);
            }
        }
    }

    private static Set<RectHV> generateRect(int n) {
        Set<RectHV> ret = new HashSet<>();
        Set<Double> distinct = new HashSet<>();
        while (ret.size() < n) {
            double[] resx = {Math.random(),Math.random()};
            double[] resy = {Math.random(),Math.random()};
            assert check(distinct, resx, resy);
            Arrays.sort(resx);
            Arrays.sort(resy);
            RectHV cur = new RectHV(resx[0],resy[0],resx[1],resy[1]);
            if (cur.height() * cur.width() > 0.01 || cur.height() < 0.01 || cur.width() < 0.01) continue;
            ret.add(cur);
        }
        assert ret.size() == n;
        return ret;
    }

    private static boolean check(Set<Double> distinct, double[] resx, double[] resy) {
        if (!distinct.add(resx[0])) return false;
        if (!distinct.add(resx[1])) return false;
        if (!distinct.add(resy[0])) return false;
        if (!distinct.add(resy[1])) return false;
        return true;
    }

}