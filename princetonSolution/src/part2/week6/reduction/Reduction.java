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
     * answer : add new path between s and t, then cal find longest circle. remove the path added in the circle.
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
        int max = 0;
        for (int i : A) {
            max = Math.max(max, Math.abs(i));
        }
        max++;
        int[] newA = new int[A.length + 1];
        for (int i = 0; i < A.length; i++) newA[i] = max + A[i];
        newA[A.length] = -3 * max;
        return FourSum.solveInN2(newA);
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
        int max = 0;
        for (int i : A) {
            max = Math.max(max, Math.abs(i));
        }
        max = max + 1;
        int[] newA = new int[2 * A.length];
        for (int i = 0; i < A.length; i++) newA[i] = 8 * max + A[i];
        for (int i = A.length; i < newA.length; i++) newA[i] = -8 * (A[i - A.length] + 2 * max);
        return threeSumNotDistinct(newA);
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
