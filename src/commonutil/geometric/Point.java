package commonutil.geometric;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Objects;

public class Point {
    private final int x;    // x coordinate
    private final int y;    // y coordinate

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate.
     * @return the x-coordinate
     */
    public int x() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     * @return the y-coordinate
     */
    public int y() {
        return y;
    }

    /**
     * Plot this point using standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Plot a line from this point to that point using standard draw.
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
