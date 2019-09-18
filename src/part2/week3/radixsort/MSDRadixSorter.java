package part2.week3.radixsort;

import commonutil.ISorter;
import commonutil.InsertSorter;

public class MSDRadixSorter implements ISorter {
    private static final int BITS = 32;
    private static final int UNIT = 8;
    private static final int R = 256;
    private static final int MASK = 255;
    public static final int CUTOFF = 15;
    private InsertSorter insertSorter = new InsertSorter();

    @Override
    public void sort(int[] arr) {
        int l = arr.length;
        int[] aux = new int[l];
        int[] pos = new int[l];
        int[] neg = new int[l];
        int posEnd = 0, negEnd = 0;
        for (int i : arr) {
            if (i >= 0) pos[posEnd++] = i;
            else neg[negEnd++] = Math.abs(i);
        }
        sort(pos, 0, posEnd - 1, 0, aux);
        sort(neg, 0, negEnd - 1, 0, aux);
        int idx = 0;
        for (int i = negEnd - 1; i >= 0; i--)
            arr[idx++] = -neg[i];
        for (int i = 0; i < posEnd; i++)
            arr[idx++] = pos[i];

    }

    private void sort(int[] arr, int s, int e, int d, int[] aux) {
        if (e - s <= CUTOFF) {
            insertSorter.sort(arr, s, e);
            return;
        }
        // TODO: ADD YOUR CODE HERE
    }

    private int valAt(int val, int pos) {
        return (val >> (BITS - (pos + 1) * UNIT)) & MASK;
    }
}









