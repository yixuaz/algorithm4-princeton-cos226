package week2.project;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return;
        }
        RandomizedQueue<String> res = new RandomizedQueue<>();
        int i = 0;
        for (; i < k; i++) {
            res.enqueue(StdIn.readString());
        }
        while (!StdIn.isEmpty()) {
            String curStr = StdIn.readString();
            if (StdRandom.uniform(++i) < k) {
                res.dequeue();
                res.enqueue(curStr);
            }
        }
        for (String s : res) {
            StdOut.println(s);
        }
    }
}
