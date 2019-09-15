package part2.week2.shortestpath.extracredit;

import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.List;

public class Job {
    int id;
    int duration;
    List<Integer> completeBefore;
    int startTime;
    int y; // for draw

    public Job(int id, int duration, List<Integer> completeBefore) {
        this.id = id;
        this.duration = duration;
        this.completeBefore = completeBefore;
    }

    public void draw(int y) {
        double offset = 0.5;
        this.y = y;
        double Y = y + offset;
        StdDraw.line(startTime, Y, startTime + duration, Y);
        StdDraw.text(startTime + duration / 2, Y, toString());
        StdDraw.text(startTime + duration / 2, y + 0.25, completeBefore.toString());
    }

    @Override
    public String toString() {
        return id + ":" + "{" + startTime + "~" + (startTime + duration)
                + "}";
    }
}
