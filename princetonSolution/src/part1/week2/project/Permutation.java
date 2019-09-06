package part1.week2.project;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Bonus
 * <p>
 * the bonus on the permutation is that could we use only O(k) space to finish it.
 * <p>
 * let think from k = 1. how to use O 1 space to get random one value.
 * we keep a output value. first it should be the first element. then next element will
 * have 1/2 chance to replace this output value. the third element will have 1/3 chance to replace output value
 * <p>
 * at now, the first element will have 1 * 1/2 * 2/3 = 1/3 in the output value
 * the second element will have 1/2 * 2/3 = 1/3
 * the third element will have 1/3
 * they are the same
 * <p>
 * so how to expand this algorithm to K elements.
 * <p>
 * assume k is 2, total element is 3, so we want each element have 2/3 chance in the result.
 * at first we put 2 element in randomized queue.
 * the next element will have 2/3 chance to replace one of element in queue
 * now the problem solve
 * <p>
 * so we can get the formula
 * first we put k element in randomized queue. then the next element we have
 * k/i to replace it in the randomized queue. i is the index of i th element start from 1
 * <p>
 * take N =5, k= 3 as example
 * first put 1st, 2nd, 3rd into random queue
 * the 4th have 3/4 chance to replace one of them
 * the 5th have 3/5 chance to replace one of them
 * <p>
 * the 1st have 1 * (2/3 * 3 /4 + 1/4) * (2/3 * 3/5 + 2/5) = 3/5 in the queue
 * u can calculate by yourself for other elements.
 */
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
            i++;
            if (StdRandom.uniform(i) < k) {
                res.dequeue();
                res.enqueue(curStr);
            }
        }
        for (String s : res) {
            StdOut.println(s);
        }
    }
}
