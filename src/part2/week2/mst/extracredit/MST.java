package part2.week2.mst.extracredit;

import edu.princeton.cs.algs4.Edge;

public interface MST {
    Iterable<Edge> edges();

    double weight();
}
