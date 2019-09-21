package part2.week5.project.better;

public class CircularSuffixArray {
    private static final int R = 256;
    private static final int CUTOFF = 15;
    private final int len;
    private final int[] index;
    private final char[] str;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("input should not be null");
        len = s.length();
        str = (s + s).toCharArray();
        index = new int[len];
        for (int i = 0; i < len; i++)
            index[i] = i;
        msdSort();
    }

    private void msdSort() {
        int[] aux = new int[len];
        sort(0, len - 1, 0, aux);
    }

    private void sort(int lo, int hi, int d, int[] aux) {
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }
        if (d == index.length) return;
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            count[str[index[i] + d] + 2]++;
        }
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];
        for (int i = lo; i <= hi; i++) {
            aux[count[str[index[i] + d] + 1]++] = index[i];
        }
        for (int i = lo; i <= hi; i++)
            index[i] = aux[i - lo];

        for (int r = 0; r < R; r++) {
            int left = lo + count[r], right = lo + count[r + 1] - 1;
            if (left < right) sort(left, right, d + 1, aux);
        }
    }

    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i < hi; i++)
            for (int j = i + 1; j > lo && less(index[j], index[j - 1], d); j--) {
                int tmp = index[j];
                index[j] = index[j - 1];
                index[j - 1] = tmp;
            }
    }

    private boolean less(int v, int w, int d) {
        for (int i = d; i < len; i++) {
            if (str[v + i] != str[w + i])
                return str[v + i] < str[w + i];
        }
        return false;
    }


    // length of s
    public int length() {
        return len;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= index.length)
            throw new IllegalArgumentException("I is not valid");
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String input = "AAAAAAAAAAAAA";
        CircularSuffixArray csa = new CircularSuffixArray(input);
        // CircularSuffixArray2 csa2 = new CircularSuffixArray2(input);
        for (int i = 0; i < input.length(); i++)
            System.out.println(csa.index(i) + "," + i);

        input = "AAAAAAAAAAAAAAAAAAAAAAAAAA";
        csa = new CircularSuffixArray(input);
        // CircularSuffixArray2 csa2 = new CircularSuffixArray2(input);
        for (int i = 0; i < input.length(); i++)
            System.out.println(csa.index(i) + "," + i);
    }

}
/**
 * Running 26 total tests.
 *
 * Tests  1-13: time to create a circular suffix array for the first
 *              n character of dickens.txt and call index(i) for each i
 *
 *             [ max allowed time = 10 seconds and <= 12x reference ]
 *
 *                  n    student  reference      ratio
 * ---------------------------------------------------
 * => passed     1000       0.00       0.00       8.15
 * => passed     2000       0.00       0.00       1.04
 * => passed     4000       0.00       0.00       1.04
 * => passed     8000       0.00       0.00       1.06
 * => passed    16000       0.00       0.00       1.32
 * => passed    32000       0.01       0.00       1.50
 * => passed    64000       0.01       0.01       2.13
 * => passed   128000       0.02       0.01       2.19
 * => passed   256000       0.05       0.02       2.25
 * => passed   512000       0.05       0.04       1.11
 * => passed  1024000       0.10       0.10       1.01
 * => passed  2048000       0.21       0.21       0.99
 * => passed  4096000       0.45       0.45       0.99
 *
 * Estimated running time (using last 6 measurements)
 *     = 1.50e-06 * n^0.81  (R^2 = 0.96)
 *
 *
 * Tests 14-26: time to create circular suffix array for n random ASCII characters
 *             and call index(i) for each i
 *
 *             [ max allowed time = 10 seconds and <= 20x reference ]
 *
 *                  n    student  reference      ratio
 * ---------------------------------------------------
 * => passed     1000       0.00       0.00       1.01
 * => passed     2000       0.00       0.00       0.71
 * => passed     4000       0.00       0.00       0.52
 * => passed     8000       0.00       0.00       0.57
 * => passed    16000       0.00       0.00       0.73
 * => passed    32000       0.00       0.00       0.96
 * => passed    64000       0.00       0.00       1.00
 * => passed   128000       0.00       0.00       1.06
 * => passed   256000       0.01       0.01       0.91
 * => passed   512000       0.02       0.03       0.75
 * => passed  1024000       0.03       0.04       0.76
 * => passed  2048000       0.07       0.08       0.88
 * => passed  4096000       0.16       0.16       1.00
 *
 * Estimated running time (using last 6 measurements)
 *     = 3.28e-08 * n^1.01  (R^2 = 0.99)
 */
