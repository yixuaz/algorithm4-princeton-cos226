package part2.week3.radixsort;

import commonutil.ISorter;

public class LSDRadixSorter implements ISorter {
    private static final int BITS = 32;
    private static final int UNIT = 8;
    private static final int R = 256;
    private static final int MASK = 255;
    @Override
    public void sort(int[] arr) {
        // TODO: ADD YOUR CODE HERE
    }

    private int valAt(int val, int pos) {
        return (val >> (UNIT * pos)) & MASK;
    }

}
