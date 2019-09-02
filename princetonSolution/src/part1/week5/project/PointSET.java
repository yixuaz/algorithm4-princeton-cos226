package part1.week5.project;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PointSET {
    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }                            // construct an empty set of points

    public boolean isEmpty() {
        return set.isEmpty();
    }                  // is the set empty?

    public int size() {
        return set.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        checkArgNotNull(p);
        set.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        checkArgNotNull(p);
        return set.contains(p);
    }            // does the set contain point p?

    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        checkArgNotNull(rect);
        List<Point2D> inRange = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                inRange.add(p);
            }
        }
        return Collections.unmodifiableList(inRange);
    }           // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        checkArgNotNull(p);
        double min = Double.POSITIVE_INFINITY;
        Point2D tar = null;
        for (Point2D cur : set) {
            double dis = cur.distanceSquaredTo(p);
            if (Double.compare(min, dis) > 0) {
                min = dis;
                tar = cur;
            }
        }
        return tar;
    }         // a nearest neighbor in the set to point p; null if the set is empty

    private void checkArgNotNull(Object i) {
        if (i == null) {
            throw new IllegalArgumentException("input should not be null");
        }
    }
}
