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
