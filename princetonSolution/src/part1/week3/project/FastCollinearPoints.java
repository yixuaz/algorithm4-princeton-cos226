package part1.week3.project;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] result;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points is null");
        }
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("point in points is null");
        }
        Point[] myPoints = points.clone();
        Arrays.sort(myPoints);
        for (int i = 1; i < myPoints.length; i++) {
            if (myPoints[i].compareTo(myPoints[i - 1]) == 0)
                throw new IllegalArgumentException("duplicate point in points");
        }
        List<LineSegment> ret = new ArrayList<>();
        int n = myPoints.length;
        for (int i = 0; i < n - 3; i++) {
            Point[] copy = myPoints.clone();
            Point curPnt = myPoints[i];
            Arrays.sort(copy, curPnt.slopeOrder());
            int cnt = 1, j = 0;
            for (; j < copy.length; cnt = 1) {
                boolean hasSmallerPoint = false;
                double curSlope;
                do {
                    curSlope = curPnt.slopeTo(copy[j]);
                    hasSmallerPoint = hasSmallerPoint || (copy[j].compareTo(curPnt) < 0);
                    cnt++;
                    j++;
                } while (j < copy.length && Double.compare(curPnt.slopeTo(copy[j]), curSlope) == 0);
                if (cnt >= 4 && !hasSmallerPoint) {
                    ret.add(new LineSegment(curPnt, copy[j - 1]));
                }
            }
        }
        result = ret.toArray(new LineSegment[0]);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return result.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        return result.clone();
    }           // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
