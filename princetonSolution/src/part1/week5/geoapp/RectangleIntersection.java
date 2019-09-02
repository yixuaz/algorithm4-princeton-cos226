package part1.week5.geoapp;
;
import edu.princeton.cs.algs4.RectHV;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class RectangleIntersection {
    private static class Interval {
        private final double x;
        private final boolean isStart;
        private final RectHV rect;
        public Interval(boolean isStart, RectHV rect) {
            this.isStart = isStart;
            this.rect = rect;
            x = isStart ? rect.xmin() : rect.xmax();
        }
    }
    public static Map<RectHV, Set<RectHV>> findAllIntersection(Set<RectHV> input) {
        return findAllIntersection(input, new IntervalST<>());
    }
    public static Map<RectHV, Set<RectHV>> findAllIntersection(Set<RectHV> input,
                                                               IntervalSearchTree<Double, RectHV> intervalST) {
        PriorityQueue<Interval> pq = new PriorityQueue<>((a,b)->{
            return Double.compare(a.x, b.x);
        });
        for (RectHV rect : input) {
            pq.offer(new Interval(true, rect));
            pq.offer(new Interval(false, rect));
        }
        Map<RectHV, Set<RectHV>> result = new HashMap<>();
        while (!pq.isEmpty()) {
            Interval cur = pq.poll();
            if (cur.isStart) {
                 Iterable<RectHV> curAns = intervalST.intersects(cur.rect.ymin(), cur.rect.ymax());
                 result.putIfAbsent(cur.rect, new HashSet<>());
                 for (RectHV that : curAns) {
                     result.putIfAbsent(that, new HashSet<>());
                     result.get(that).add(cur.rect);
                     result.get(cur.rect).add(that);
                 }
                intervalST.put(cur.rect.ymin(), cur.rect.ymax(), cur.rect);
            } else {
                intervalST.delete(cur.rect.ymin(), cur.rect.ymax());
            }
        }
        return result;
    }
}
