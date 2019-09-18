package part2.week3.radixsort;

import commonutil.ISorter;

public class LSDRadixSorter implements ISorter {
    private static final int BITS = 32;
    private static final int UNIT = 8;
    private static final int R = 256;
    private static final int MASK = 255;

    @Override
    public void sort(int[] arr) {
        int N = arr.length;
        int[] aux = new int[N];
        int W = BITS / UNIT;
        for (int d = 0; d < W; d++) {
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++)
                count[valAt(arr[i], d) + 1]++;
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
            if (d == W - 1) {
                int shift1 = count[R] - count[R / 2];
                int shift2 = count[R / 2];
                for (int r = 0; r < R / 2; r++)
                    count[r] += shift1;
                for (int r = R / 2; r < R; r++)
                    count[r] -= shift2;
            }
            for (int i = 0; i < N; i++)
                aux[count[valAt(arr[i], d)]++] = arr[i];
            for (int i = 0; i < N; i++)
                arr[i] = aux[i];
        }
    }

    private int valAt(int val, int pos) {
        return (val >> (UNIT * pos)) & MASK;
    }

}
