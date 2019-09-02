package part2.week5.project;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String input = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(input);
        int first = 0, n = input.length();
        for (int i = 0; i < n; i++) {
            if (first == csa.index(i))
                BinaryStdOut.write(i);
        }
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(input.charAt((csa.index(i) + n - 1) % n));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String lastCol = BinaryStdIn.readString();
        char[] t = lastCol.toCharArray();
        int[] next = new int[t.length];
        int[] cnt = new int[R + 1];
        for (char c : t)
            cnt[c + 1]++;
        for (int r = 0; r < R; r++)
            cnt[r + 1] += cnt[r];
        for (int i = 0; i < t.length; i++)
            next[cnt[t[i]]++] = i;
        int idx = next[first];
        for (int i = 0; i < t.length; idx = next[idx], i++)
            BinaryStdOut.write(t[idx]);
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
