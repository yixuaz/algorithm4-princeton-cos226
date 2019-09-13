package part1.week5.geoapp;

import commonutil.geometric.LineSegment;
import commonutil.geometric.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineSegmentIntersection {
    private static class Event {
        int xPos;
        int ySmall;
        int yLarge;
        int type;

        public Event(int xPos, int ySmall, int yLarge, int type) {
            this.xPos = xPos;
            this.ySmall = ySmall;
            this.yLarge = yLarge;
            this.type = type;
        }
    }

    private static final int X_IN = 0;
    private static final int X_OUT = 2;
    private static final int Y = 1;

    public static List<Point> solve(List<LineSegment> input) {
        List<Point> result = new ArrayList<>();
        List<Event> events = buildEvents(input);
        events.sort((a, b) -> {
            int compare = Integer.compare(a.xPos, b.xPos);
            if (compare == 0) {
                return Integer.compare(a.type, b.type);
            }
            return compare;
        });
        RangeSearchBST<Integer, Event> bst = new RangeSearchBST<>();
        for (Event event : events) {
            if (event.type == X_IN) {
                bst.put(event.ySmall, event);
            } else if (event.type == X_OUT) {
                bst.remove(event.ySmall);
            } else {
                for (int y : bst.search(event.ySmall, event.yLarge)) {
                    result.add(new Point(event.xPos, y));
                }
            }
        }
        return result;
    }

    private static List<Event> buildEvents(List<LineSegment> input) {
        List<Event> events = new ArrayList<>();
        for (LineSegment lineSegment : input) {
            boolean vertical = lineSegment.isVertical();
            if (vertical) {
                int ySmall = Math.min(lineSegment.p().y(), lineSegment.q().y());
                int yLarge = Math.max(lineSegment.p().y(), lineSegment.q().y());
                events.add(new Event(lineSegment.p().x(), ySmall, yLarge, Y));
            } else {
                int xSmall = Math.min(lineSegment.p().x(), lineSegment.q().x());
                int xLarge = Math.max(lineSegment.p().x(), lineSegment.q().x());
                int y = lineSegment.p().y();
                events.add(new Event(xSmall, y, y, X_IN));
                events.add(new Event(xLarge, y, y, X_OUT));
            }
        }
        return events;
    }
}
