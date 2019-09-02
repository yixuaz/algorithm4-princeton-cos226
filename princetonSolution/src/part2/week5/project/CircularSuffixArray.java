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
