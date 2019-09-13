package part1.week5.geoapp;

import commonutil.geometric.LineSegment;
import commonutil.geometric.Point;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class LineSegmentIntersectionTest {
    @Test
    public void noIntersectionTest() {
        LineSegment lineSegment1 = new LineSegment(new Point(0, 0), new Point(0, 1));
        LineSegment lineSegment2 = new LineSegment(new Point(1, 2), new Point(-1, 2));
        List<Point> res = LineSegmentIntersection.solve(Arrays.asList(lineSegment1, lineSegment2));
        assertEquals(0, res.size());
    }

    @Test
    public void intersectionTest() {
        LineSegment lineSegment1 = new LineSegment(new Point(0, 0), new Point(0, 2));
        LineSegment lineSegment2 = new LineSegment(new Point(1, 1), new Point(-1, 1));
        List<Point> res = LineSegmentIntersection.solve(Arrays.asList(lineSegment1, lineSegment2));
        assertEquals(1, res.size());
        assertEquals(new Point(0, 1), res.get(0));
    }

    @Test
    public void intersectionEdgeLimitTest() {
        LineSegment lineSegment1 = new LineSegment(new Point(0, 0), new Point(0, 1));
        LineSegment lineSegment2 = new LineSegment(new Point(1, 1), new Point(-1, 1));
        List<Point> res = LineSegmentIntersection.solve(Arrays.asList(lineSegment1, lineSegment2));
        assertEquals(1, res.size());
        assertEquals(new Point(0, 1), res.get(0));
    }

    @Test
    public void randomTest() {
        int upLimit = 100;
        for (int i = 0; i < 100; i++) {
            if (i % 20 == 19) upLimit <<= 1;
            Set<LineSegment> xLines = generateLine(upLimit / 5, upLimit, true);
            Set<LineSegment> yLines = generateLine(upLimit / 20, upLimit, false);
            List<LineSegment> testInput = new ArrayList<>();
            testInput.addAll(xLines);
            testInput.addAll(yLines);
            List<Point> res = LineSegmentIntersection.solve(testInput);
            assertEquals(expect(xLines, yLines), new HashSet(res));
        }
    }

    private Set<Point> expect(Set<LineSegment> xLines, Set<LineSegment> yLines) {
        Set<Point> result = new HashSet<>();
        for (LineSegment xLine : xLines) {
            for (LineSegment yLine : yLines) {
                Point isIntersect = xLine.intersect(yLine);
                if (isIntersect != null) {
                    result.add(isIntersect);
                }
            }
        }
        return result;
    }

    @Test
    @Ignore
    public void visualize() {
        visualzieOne();
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
                visualzieOne();
            else if (StdDraw.isKeyPressed(KeyEvent.VK_Q))
                break;
        }
    }
    private void visualzieOne() {
        StdDraw.clear();
        StdDraw.setPenColor();
        StdDraw.setPenRadius();
        int upLimit = 100;
        StdDraw.setXscale(0, upLimit);
        StdDraw.setYscale(0, upLimit);
        Set<LineSegment> xLines = generateLine(upLimit / 5, upLimit, true);
        Set<LineSegment> yLines = generateLine(upLimit / 10, upLimit, false);
        List<LineSegment> testInput = new ArrayList<>();
        testInput.addAll(xLines);
        testInput.addAll(yLines);
        for (LineSegment lineSegment : testInput) {
            lineSegment.draw();
        }
        StdDraw.setPenColor(Color.red);
        StdDraw.setPenRadius(0.01);
        for (Point p : LineSegmentIntersection.solve(testInput)) {
            p.draw();
        }

        StdDraw.show();
        StdDraw.pause(50);
    }

    private static Set<LineSegment> generateLine(int n, int upLimit, boolean isX) {
        Set<LineSegment> ret = new HashSet<>();
        Set<Integer> distinct = new HashSet<>();
        Random r = new Random();
        while (ret.size() < n) {
            int y = r.nextInt(upLimit);
            if (!distinct.add(y)) continue;
            int x1 = r.nextInt(upLimit), x2 = r.nextInt(upLimit);
            if (isX)
                ret.add(new LineSegment(new Point(x1, y), new Point(x2, y)));
            else
                ret.add(new LineSegment(new Point(y, x1), new Point(y, x2)));
        }
        return ret;
    }

}