package part2.week5.project;

import java.util.Arrays;

public class CircularSuffixArray {
    private final char[] str;
    private final int[] index;
    private final int len;

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private final int idx;

        public CircularSuffix(int idx) {
            this.idx = idx;
        }

        @Override
        public int compareTo(CircularSuffix that) {
            for (int i = 0; i < len; i++) {
                if (str[this.idx + i] != str[that.idx + i])
                    return str[this.idx + i] < str[that.idx + i] ? -1 : 1;
            }
            return 0;
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("input should not be null");
        len = s.length();
        str = (s + s).toCharArray();
        CircularSuffix[] tmp = new CircularSuffix[len];
        for (int i = 0; i < len; i++) {
            tmp[i] = new CircularSuffix(i);
        }
        Arrays.sort(tmp);
        index = new int[len];
        for (int i = 0; i < len; i++)
            index[i] = tmp[i].idx;
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
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < 12; i++)
            System.out.println(csa.index(i));
    }

}

/**
 * Tests  1-13: time to create a circular suffix array for the first
 *              n character of dickens.txt and call index(i) for each i
 *
 *             [ max allowed time = 10 seconds and <= 12x reference ]
 *
 *                  n    student  reference      ratio
 * ---------------------------------------------------
 * => passed     1000       0.00       0.00      14.05
 * => passed     2000       0.00       0.00       3.97
 * => passed     4000       0.00       0.00       4.64
 * => passed     8000       0.00       0.00       3.24
 * => passed    16000       0.01       0.00       3.62
 * => passed    32000       0.02       0.00       3.83
 * => passed    64000       0.04       0.01       7.51
 * => passed   128000       0.11       0.01      11.30
 * => passed   256000       0.22       0.02      10.78
 * => passed   512000       0.31       0.04       7.02
 * => passed  1024000       0.57       0.10       5.94
 * => passed  2048000       1.42       0.21       6.76
 * => passed  4096000       3.74       0.55       6.84
 *
 * Estimated running time (using last 6 measurements)
 *     = 9.69e-07 * n^0.98  (R^2 = 0.97)
 *
 *
 * Tests 14-26: time to create circular suffix array for n random ASCII characters
 *             and call index(i) for each i
 *
 *             [ max allowed time = 10 seconds and <= 20x reference ]
 *
 *                  n    student  reference      ratio
 * ---------------------------------------------------
 * => passed     1000       0.00       0.00       8.98
 * => passed     2000       0.00       0.00       5.78
 * => passed     4000       0.00       0.00       4.23
 * => passed     8000       0.00       0.00       5.83
 * => passed    16000       0.00       0.00       8.27
 * => passed    32000       0.01       0.00      10.75
 * => passed    64000       0.02       0.00      10.93
 * => passed   128000       0.04       0.00       9.97
 * => passed   256000       0.10       0.01       8.05
 * => passed   512000       0.22       0.03       8.45
 * => passed  1024000       0.50       0.04      11.64
 * => passed  2048000       1.18       0.09      13.66
 * => passed  4096000       2.59       0.16      16.14
 */
