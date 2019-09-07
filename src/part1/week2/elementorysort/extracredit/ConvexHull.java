package part1.week2.elementorysort.extracredit;

import edu.princeton.cs.algs4.Point2D;

import java.util.List;

public class ConvexHull {
    // solved by m * n, n is data size of input, m is data size of out put
    // hint : Jarvis Algorithm
    // out put it by the upmost point to counter clock wise wihtout colinear point
    public static List<Point2D> solve(List<Point2D> input) {
        // TODO : ADD YOUR CODE HERE
        return null;
    }


    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double cost = (c.y() - b.y()) * (b.x() - a.x()) - (b.y() - a.y()) * (c.x() - b.x());
        if (Math.abs(cost) < 0.0000001) return 0;
        if (cost > 0) return 1; // counter clock wise
        else  return -1; // clock wise
    }
}
