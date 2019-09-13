package commonutil.geometric;

import edu.princeton.cs.algs4.Point2D;

import java.util.Objects;

public class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param p one endpoint
     * @param q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *                              is <tt>null</tt>
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }
        if (p.x() != q.x() && p.y() != q.y()) {
            throw new IllegalArgumentException("argument is not in vertical or horizontal");
        }
        if ((p.y() == q.y() && p.x() > q.x()) || (p.x() == q.x() && p.y() > q.y())) {
            this.p = q;
            this.q = p;
        } else {
            this.p = p;
            this.q = q;
        }

    }
    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    public boolean isVertical() {
        return p.x() == q.x();
    }

    /**
     * left or up point
     * @return
     */
    public Point p() {
        return p;
    }
    /**
     * right or down point
     * @return
     */
    public Point q() {
        return q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineSegment)) return false;
        LineSegment that = (LineSegment) o;
        return Objects.equals(p, that.p) &&
                Objects.equals(q, that.q);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, q);
    }

    /**
     * return intersect Point, if no intersect return null
     * @param that
     * @return intersect Point, if no intersect return null
     */
    public Point intersect(LineSegment that) {
        if (this.isVertical() == that.isVertical()) {
            return null;
        }
        if (!this.isVertical()) {
            return that.intersect(this);
        }
        if (this.p.x() >= that.p.x() && this.p.x() <= that.q.x()
                && that.p.y() >= this.p.y() && that.p.y() <= this.q.y()) {
            return new Point(this.p.x(), that.p.y());
        }
        return null;
    }
}
