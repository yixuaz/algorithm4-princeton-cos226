package week3.project;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] result;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        int n = myPoints.length;
        List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < n - 3; i++) {
            Point p1 = myPoints[i];
            for (int j = i + 1; j < n - 2; j++) {
                Point p2 = myPoints[j];
                for (int k = j + 1; k < n - 1; k++) {
                    Point p3 = myPoints[k];
                    for (int m = k + 1; m < n; m++) {
                        Point p4 = myPoints[m];
                        if (Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0 &&
                                Double.compare(p1.slopeTo(p3), p1.slopeTo(p4)) == 0) {
                            lineSegments.add(new LineSegment(p1, p4));
                        }
                    }
                }
            }
        }
        result = lineSegments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return result.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return result.clone();
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
