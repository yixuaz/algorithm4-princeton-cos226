package part1.week5.project;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KdTree {
    private static final double POINT_RADIUS = 0.01;
    private static class Node {
        private Point2D p;
        private final boolean isVertical;
        private Node left, right;
        private final RectHV rect;

        public Node(Point2D p, boolean isVertical, RectHV rect) {
            this.p = p;
            this.isVertical = isVertical;
            this.rect = rect;
        }

        public int compare(RectHV queryRect) {
            return isVertical ? Double.compare(queryRect.xmax(), p.x()) : Double.compare(queryRect.ymax(), p.y());
        }

        public int compare(Point2D queryPoint) {
            return isVertical ? Double.compare(queryPoint.x(), p.x()) : Double.compare(queryPoint.y(), p.y());
        }

        public Point2D minDisToTarPoint(Point2D tar, Point2D curMin) {
            double dis1 = p == null ? Double.POSITIVE_INFINITY : tar.distanceSquaredTo(p);
            double dis2 = curMin == null ? Double.POSITIVE_INFINITY : tar.distanceSquaredTo(curMin);
            return Double.compare(dis1, dis2) < 0 ? p : curMin;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        size = 0;
    }                            // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                  // is the set empty?

    public int size() {
        return size;
    }                        // number of points in the set

    public void insert(Point2D p) {
        checkArgNotNull(p);
        root = insert(root, null, true, p);
    }             // add the point to the set (if it is not already in the set)

    private Node insert(Node cur, Node prev, boolean isVertical, Point2D p) {
        if (cur == null) {
            size++;
            return new Node(p, isVertical, buildRect(prev, p));
        }
        if (cur.p.equals(p)) return cur;
        if (cur.compare(p) < 0) {
            cur.left = insert(cur.left, cur, !isVertical, p);
        } else {
            cur.right = insert(cur.right, cur, !isVertical, p);
        }
        return cur;
    }

    private RectHV buildRect(Node prev, Point2D p) {
        if (prev == null) return new RectHV(0, 0, 1, 1);
        boolean isLeft = prev.compare(p) < 0;
        if (prev.isVertical) {
            if (isLeft) return new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.p.x(), prev.rect.ymax());
            else return new RectHV(prev.p.x(), prev.rect.ymin(), prev.rect.xmax(), prev.rect.ymax());
        }
        if (isLeft) return new RectHV(prev.rect.xmin(), prev.rect.ymin(), prev.rect.xmax(), prev.p.y());
        else return new RectHV(prev.rect.xmin(), prev.p.y(), prev.rect.xmax(), prev.rect.ymax());
    }

    public boolean contains(Point2D p) {
        checkArgNotNull(p);
        Node cur = find(root, p);
        return cur != null;
    }            // does the set contain point p?

    private Node find(Node cur, Point2D p) {
        if (cur == null) return null;
        if (cur.compare(p) < 0) {
            return find(cur.left, p);
        } else {
            if (cur.p.equals(p)) return cur;
            return find(cur.right, p);
        }
    }

    public void draw() {
        draw(root);
    }                        // draw all points to standard draw

    private void draw(Node cur) {
        if (cur == null) return;
        StdDraw.setPenRadius(POINT_RADIUS);
        cur.p.draw();
        StdDraw.setPenRadius();
        drawLine(cur);
        draw(cur.left);
        draw(cur.right);
    }

    private void drawLine(Node cur) {
        StdDraw.setPenColor(cur.isVertical ? Color.red : Color.blue);
        buildLine(cur).draw();
        StdDraw.setPenColor(Color.black);
    }

    private RectHV buildLine(Node cur) {
        if (cur.isVertical) return new RectHV(cur.p.x(), cur.rect.ymin(), cur.p.x(), cur.rect.ymax());
        return new RectHV(cur.rect.xmin(), cur.p.y(), cur.rect.xmax(), cur.p.y());
    }


    public Iterable<Point2D> range(RectHV rect) {
        checkArgNotNull(rect);
        List<Point2D> inRange = new ArrayList<>();
        range(root, rect, inRange);
        return Collections.unmodifiableList(inRange);
    }           // all points that are inside the rectangle (or on the boundary)

    private void range(Node cur, RectHV rect, List<Point2D> inRange) {
        if (cur == null) return;
        if (rect.contains(cur.p)) {
            inRange.add(cur.p);
        }
        if (rect.intersects(buildLine(cur))) {
            range(cur.left, rect, inRange);
            range(cur.right, rect, inRange);
        } else {
            range(cur.compare(rect) < 0 ? cur.left : cur.right, rect, inRange);
        }
    }

    public Point2D nearest(Point2D p) {
        checkArgNotNull(p);
        final Node result = new Node(null, false, null);
        nearest(root, p, result);
        return result.p;
    }         // a nearest neighbor in the set to point p; null if the set is empty

    private void nearest(Node cur, Point2D p, Node result) {
        if (cur == null) return;
        if (cur.p == cur.minDisToTarPoint(p, result.p)) {
            result.p = cur.p;
        }
        boolean isLeft = cur.compare(p) < 0;
        nearest(isLeft ? cur.left : cur.right, p, result);
        Node otherNode = isLeft ? cur.right : cur.left;
        if (otherNode != null && Double.compare(otherNode.rect.distanceSquaredTo(p), result.p.distanceSquaredTo(p)) <= 0) { // critical is smaller
            nearest(otherNode, p, result);
        }
    }

    private static void checkArgNotNull(Object i) {
        if (i == null) {
            throw new IllegalArgumentException("input should not be null");
        }
    }
}
