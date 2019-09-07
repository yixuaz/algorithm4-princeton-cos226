package part1.week2.elementorysort.extracredit;

import edu.princeton.cs.algs4.Point2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ConvexHull {
    // solved by m * n, n is data size of input, m is data size of out put
    // hint : Jarvis Algorithm
    // out put it by the upmost point to counter clock wise wihtout colinear point
    public static List<Point2D> solve(List<Point2D> input) {
        List<Point2D> points = new ArrayList<>(new HashSet<>(input));
        int upmost = 0;
        for (int i = 1; i < points.size(); i++) {
            int compare = Double.compare(points.get(i).y(), points.get(upmost).y());
            if (compare < 0) {
                upmost = i;
            } else if (compare == 0 && points.get(i).x() < points.get(upmost).x()) {
                upmost = i;
            }
        }
        List<Point2D> preResult = new ArrayList<>();
        preResult.add(points.get(upmost));
        int p = upmost;
        while (true) {
            int q = (p + 1) % points.size();
            for (int i = 0; i < points.size(); i++) {
                int ccw = ccw(points.get(p), points.get(i), points.get(q));
                if (ccw > 0) {
                    q = i;
                }
            }
            if (q == upmost) break;
            preResult.add(points.get(q));
            p = q;
        }

        return preResult;
    }


    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double cost = (c.y() - b.y()) * (b.x() - a.x()) - (b.y() - a.y()) * (c.x() - b.x());
        if (Math.abs(cost) < 0.0000001) return 0;
        if (cost > 0) return 1; // counter clock wise
        else  return -1; // clock wise
    }
}
