package part1.week3.quicksort;

/**
 * Selection in two sorted arrays. Given two sorted arrays a[] and b[],
 * of lengths n1 and n2 and an integer 0 ≤ k < n1+n2, design an algorithm to find a key of rank k.
 * The order of growth of the worst case running time of your algorithm should be logn, where n = n1 + n2
 * <p>
 * Version 1: n1=n2 (equal length arrays) and k=n/2 (median).
 * Version 2: k=n/2 (median).
 * Version 3: no restrictions.
 */
public class SelectionInTwoSortedArray {
    public static int getHalfOfNthElementInTwoSameLengthArray(int[] A, int[] B) {
        assert A.length == B.length;
        int k = A.length;
        if (k <= 0) {
            throw new IllegalArgumentException("invalid k " + k);
        }
        //TODO: ADD YOUR CODE HERE
        return -1;
    }

    public static int getHalfOfNthElementInTwoArray(int[] A, int[] B) {
        int k = (A.length + B.length) / 2;
        if (k <= 0) {
            throw new IllegalArgumentException("invalid k " + k);
        }
        //TODO: ADD YOUR CODE HERE
        return -1;
    }

    public static int getKthElementInTwoArray(int[] A, int[] B, int k) {// K is start from 1
        if (k <= 0 || k > A.length + B.length) {
            throw new IllegalArgumentException("invalid k " + k);
        }
        //TODO: ADD YOUR CODE HERE
        return -1;
    }

}
