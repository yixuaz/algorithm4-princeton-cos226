package week5.geoapp;

import edu.princeton.cs.algs4.RectHV;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

;

public class RectangleIntersection {

    public static Map<RectHV, Set<RectHV>> findAllIntersection(Set<RectHV> input) {
        return findAllIntersection(input, new IntervalST<>());
    }


    public static Map<RectHV, Set<RectHV>> findAllIntersection(Set<RectHV> input,
                                                               IntervalSearchTree<Double, RectHV> intervalST) {
        // TODO : ADD YOUR CODE HERE
        return null;
    }
}
