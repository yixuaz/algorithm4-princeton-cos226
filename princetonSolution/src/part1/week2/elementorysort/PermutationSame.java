package part1.week2.elementorysort;

import commonutil.ISorter;

import java.util.Arrays;

/**
 * the key idea behind it is to find a representative of a pattern.
 * so we can sort both array, use the smallest alphabetic order as a representative to
 * compare them are equal.
 */
public class PermutationSame {
    public static boolean same(int[] a, int[] b) {
        if (a.length != b.length) return false;
        ISorter shellSorter = new ShellSorter();
        shellSorter.sort(a);
        shellSorter.sort(b);
        return Arrays.equals(a, b);
    }
}
