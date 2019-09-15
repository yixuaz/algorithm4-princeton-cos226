package part2.week2.shortestpath.extracredit;

import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class ParallelJobSchedulingTest {
    @Test
    public void basicTest() {
        boolean enableVisualize = false;
        List<Job> input = new ArrayList<>();
        input.add(new Job(0, 41, Arrays.asList(1, 7, 9)));
        input.add(new Job(1, 51, Arrays.asList(2)));
        input.add(new Job(2, 50, Collections.emptyList()));
        input.add(new Job(3, 36, Collections.emptyList()));
        input.add(new Job(4, 38, Collections.emptyList()));
        input.add(new Job(5, 45, Collections.emptyList()));
        input.add(new Job(6, 21, Arrays.asList(3, 8)));
        input.add(new Job(7, 32, Arrays.asList(3, 8)));
        input.add(new Job(8, 32, Arrays.asList(2)));
        input.add(new Job(9, 29, Arrays.asList(4, 6)));
        ParallelJobScheduling.solve(input);
        for (Job job : input) {
            System.out.println("job " + job.id + ", start time: " + job.startTime);
        }
        if (enableVisualize) {
            visualize(input);
            while (true) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_Q))
                    break;
            }
        }
    }

    private class Event {
        int time;
        boolean lock;
        Job job;

        public Event(int time, boolean lock, Job job) {
            this.time = time;
            this.lock = lock;
            this.job = job;
        }
    }

    private void visualize(List<Job> input) {
        int xMax = calculateXmax(input), yMax = calculateYmax(input);
        System.out.println(yMax);
        StdDraw.setXscale(0, xMax);
        StdDraw.setYscale(0, yMax);
        boolean[] used = new boolean[yMax];
        PriorityQueue<Event> pq = new PriorityQueue<>((a, b) -> {
            int res = Integer.compare(a.time, b.time);
            if (res == 0) return Boolean.compare(a.lock, b.lock);
            return res;
        });
        for (Job job : input) {
            pq.offer(new Event(job.startTime, true, job));
            pq.offer(new Event(job.startTime + job.duration, false, job));
        }
        while (!pq.isEmpty()) {
            Event event = pq.poll();
            if (!event.lock) {
                used[event.job.y] = false;
            } else {
                int availbleY = 0;
                for (; availbleY < yMax; availbleY++) {
                    if (!used[availbleY]) break;
                }
                used[availbleY] = true;
                event.job.draw(availbleY);
            }
        }
    }

    private int calculateXmax(List<Job> input) {
        int xMax = 0;
        for (Job job : input)
            xMax = Math.max(xMax, job.startTime + job.duration);
        return xMax;
    }

    private int calculateYmax(List<Job> input) {
        int yMax = 0;
        TreeMap<Integer, Integer> time2Cnt = new TreeMap<>();
        for (Job job : input) {
            int st = job.startTime, end = job.startTime + job.duration;
            time2Cnt.put(st, time2Cnt.getOrDefault(st, 0) + 1);
            time2Cnt.put(end, time2Cnt.getOrDefault(end, 0) - 1);
        }
        int cnt = 0;
        for (int time : time2Cnt.keySet()) {
            cnt += time2Cnt.get(time);
            yMax = Math.max(yMax, cnt);
        }
        return yMax;
    }

}