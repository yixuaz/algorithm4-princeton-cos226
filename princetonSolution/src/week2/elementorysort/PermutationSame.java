package week2.elementorysort;

import commonutil.ISorter;

import java.util.Arrays;

public class PermutationSame {
    public static boolean same(int[] a, int[] b) {
        if (a.length != b.length) return false;
        ISorter shellSorter = new ShellSorter();
        shellSorter.sort(a);
        shellSorter.sort(b);
        return Arrays.equals(a,b);
    }
}
