package part1.week5.project.better;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;
import part1.week5.project.PointSET;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** the version save memory by removing rectHV in node */
public class KdTree {
    private static final double POINT_RADIUS = 0.01;
    private static final int QUERY_RECT = 4;
    private static final int NODE_RECT = 0;
    private static final int XMIN = 0;
    private static final int YMIN = 1;
    private static final int XMAX = 2;
    private static final int YMAX = 3;

    private static class Node {
        private Point2D p;
        private final boolean isVertical;
        private Node left, right;

        public Node(Point2D p, boolean isVertical) {
            this.p = p;
            this.isVertical = isVertical;
        }

        public int compare(double[] d) {
            return isVertical ? Double.compare(d[QUERY_RECT + XMAX], p.x()) :
                   Double.compare(d[QUERY_RECT + YMAX], p.y());
        }

        public int compare(Point2D queryPoint) {
            return isVertical ? Double.compare(queryPoint.x(), p.x()) :
                   Double.compare(queryPoint.y(), p.y());
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
        root = insert(root, true, p);
    }             // add the point to the set (if it is not already in the set)

    private Node insert(Node cur, boolean isVertical, Point2D p) {
        if (cur == null) {
            size++;
            return new Node(p, isVertical);
        }
        if (cur.p.equals(p)) return cur;
        if (cur.compare(p) < 0) {
            cur.left = insert(cur.left, !isVertical, p);
        }
        else {
            cur.right = insert(cur.right, !isVertical, p);
        }
        return cur;
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
        }
        else {
            if (cur.p.equals(p)) return cur;
            return find(cur.right, p);
        }
    }

    public void draw() {
        draw(root, null, new double[] { 0, 0, 1, 1 });
    }                        // draw all points to standard draw

    private void draw(Node cur, Node pre, double[] nodeRect) {
        if (cur == null) return;
        if (pre != null) {
            nodeRect = update(nodeRect, pre.p, pre.isVertical, pre.left == cur);
        }
        StdDraw.setPenRadius(POINT_RADIUS);
        cur.p.draw();
        StdDraw.setPenRadius();
        drawLine(cur, nodeRect);
        draw(cur.left, cur, nodeRect);
        draw(cur.right, cur, nodeRect);
    }

    private void drawLine(Node cur, double[] nodeRect) {
        StdDraw.setPenColor(cur.isVertical ? Color.red : Color.blue);
        double[] d = buildLine(cur, nodeRect);
        new RectHV(d[0], d[1], d[2], d[3]).draw();
        StdDraw.setPenColor(Color.black);
    }

    private double[] buildLine(Node cur, double[] d) {
        if (cur.isVertical) return new double[] { cur.p.x(), d[YMIN], cur.p.x(), d[YMAX] };
        return new double[] { d[XMIN], cur.p.y(), d[XMAX], cur.p.y() };
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArgNotNull(rect);
        List<Point2D> inRange = new ArrayList<>();
        double[] queryRectAndNodeRect = {
                0, 0, 1, 1,
                rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax(),
                };
        range(root, null, inRange, queryRectAndNodeRect);
        return Collections.unmodifiableList(inRange);
    }           // all points that are inside the rectangle (or on the boundary)

    private void range(Node cur, Node pre, List<Point2D> inRange,
                       double[] queryRectAndNodeRect) {
        if (cur == null) return;
        if (pre != null) {
            queryRectAndNodeRect = update(queryRectAndNodeRect, pre.p, pre.isVertical,
                                          pre.left == cur);
        }
        if (contains(queryRectAndNodeRect, cur.p)) {
            inRange.add(cur.p);
        }
        if (intersects(queryRectAndNodeRect, buildLine(cur, queryRectAndNodeRect))) {
            range(cur.left, cur, inRange, queryRectAndNodeRect);
            range(cur.right, cur, inRange, queryRectAndNodeRect);
        }
        else {
            range(cur.compare(queryRectAndNodeRect) < 0 ? cur.left : cur.right, cur, inRange,
                  queryRectAndNodeRect);
        }
    }

    private boolean intersects(double[] d, double[] cur) {
        return d[QUERY_RECT + XMAX] >= cur[XMIN]
                && d[QUERY_RECT + YMAX] >= cur[YMIN]
                && cur[XMAX] >= d[QUERY_RECT + XMIN]
                && cur[YMAX] >= d[QUERY_RECT + YMIN];
    }

    private boolean contains(double[] d, Point2D p) {
        return (p.x() >= d[QUERY_RECT + XMIN]) && (p.x() <= d[QUERY_RECT + XMAX])
                && (p.y() >= d[QUERY_RECT + YMIN]) && (p.y() <= d[QUERY_RECT + YMAX]);
    }

    private double[] update(double[] d, Point2D p, boolean isVertical, boolean isLeft) {
        double[] ret = d.clone();
        int pos = NODE_RECT;
        if (isLeft) pos += 2;
        if (!isVertical) pos += 1;
        ret[pos] = isVertical ? p.x() : p.y();
        return ret;
    }

    private double distanceSquaredTo(double[] d, Point2D p) {
        double dx = 0.0, dy = 0.0;
        double xmin = d[NODE_RECT + XMIN], xmax = d[NODE_RECT + XMAX],
                ymin = d[NODE_RECT + YMIN], ymax = d[NODE_RECT + YMAX];
        if (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx * dx + dy * dy;
    }

    public Point2D nearest(Point2D p) {
        checkArgNotNull(p);
        final Node result = new Node(null, false);
        nearest(root, null, p, result, new double[] { 0, 0, 1, 1 });
        return result.p;
    }         // a nearest neighbor in the set to point p; null if the set is empty

    private void nearest(Node cur, Node pre, Point2D tar, Node result, double[] nodeRect) {
        if (cur == null) return;
        if (pre != null) {
            nodeRect = update(nodeRect, pre.p, pre.isVertical, pre.left == cur);
        }
        if (cur.p == cur.minDisToTarPoint(tar, result.p)) {
            result.p = cur.p;
        }
        boolean isLeft = cur.compare(tar) < 0;
        nearest(isLeft ? cur.left : cur.right, cur, tar, result, nodeRect);
        Node otherNode = isLeft ? cur.right : cur.left;
        if (otherNode != null && Double.compare(
                distanceSquaredTo(update(nodeRect, cur.p, cur.isVertical, cur.left == otherNode),
                                  tar),
                result.p.distanceSquaredTo(tar)) <= 0) { // critical is smaller
            nearest(otherNode, cur, tar, result, nodeRect);
        }
    }

    private static void checkArgNotNull(Object i) {
        if (i == null) {
            throw new IllegalArgumentException("input should not be null");
        }
    }

    // performance comparing
    public static void main(String[] args) {
        String filePath = "princetonSolution\\src\\part1\\week5\\project\\testfiles\\";
        String[] testFileNames = { "input10K.txt", "input100K.txt", "input1M.txt" };
        for (String testFileName : testFileNames) {
            System.out.println("testing:" + testFileName);
            In in = new In(filePath + testFileName);
            PointSET brute = new PointSET();
            KdTree kdtree = new KdTree();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                brute.insert(p);
            }
            int ops = 0;
            Stopwatch stopWatch = new Stopwatch();

            while (stopWatch.elapsedTime() < 1) {
                brute.nearest(new Point2D(Math.random(), Math.random()));
                ops++;
            }

            System.out.println("brute ops per second : " + ops);

            stopWatch = new Stopwatch();
            ops = 0;
            while (stopWatch.elapsedTime() < 1) {
                kdtree.nearest(new Point2D(Math.random(), Math.random()));
                ops++;
            }

            System.out.println("kdtree ops per second : " + ops);
        }
    }
}
