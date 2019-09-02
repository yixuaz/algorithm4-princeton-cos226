package part1.week3.quicksort;

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
