package part2.week6.reduction;

import part1.week6.hashtables.FourSum;

public class Reduction {
    /**
     * Longest path and longest cycle. Consider the following two problems
     *
     * LongestPath: Given an undirected graph GG and two distinct vertices ss and tt, find a simple path (no repeated vertices) between ss and tt with the most edges.
     * LongestCycle: Given an undirected graph G', find a simple cycle (no repeated vertices or edges except the first and last vertex) with the most edges.
     * Show that LongestPath linear-time reduces to LongestCycle.
     */

    /**
     * answer :
     */

    /**
     * 3Sum and 4Sum. Consider the following two problems:
     * <p>
     * 3Sum: Given an integer array a, are there three distinct indices i, j, and k such that a_i + a_j + a_k = 0?
     * 4Sum: Given an integer array b, are there four distinct integers i, j, k, and l such that b_i + b_j + b_k + b_l =0?
     * Show that 3Sum linear-time reduces to 4Sum.
     * <p>
     * PLEASE USE week6.hashtables.FourSum TO SOLVE THIS PROBLEM
     */
    public static boolean threeSum(int[] A) {
        // TODO: ADD YOUR CODE HERE
        return FourSum.solveInN2(null);
    }


    /**
     * 3Sum and 3Linear. Consider the following two problems:
     * <p>
     * 3Linear: Given an integer array a, are there three indices (not necessarily distinct)
     * i, j, and k such that a_i + a_j = 8 * a_k?
     * 3Sum: Given an integer array b, are there three indices (not necessarily distinct) i, j, and k such that b_i + b_j + b_k =0?
     * Show that 3Linear linear-time reduces to 3Sum.
     * <p>
     * PLEASE USE threeSumNotDistinct TO SOLVE THIS PROBLEM
     */
    public static boolean threeLinear(int[] A) {
        // TODO: ADD YOUR CODE HERE
        return threeSumNotDistinct(null);
    }

    private static boolean threeSumNotDistinct(int[] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                for (int k = 0; k < A.length; k++) {
                    if (A[i] + A[j] + A[k] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
