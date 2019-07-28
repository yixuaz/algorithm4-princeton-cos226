package week2.elementorysort;

import commonutil.ISorter;

public class IntersectionOfTwoSet {
    public static int intersectionCount(int[] a, int[] b) {
        ISorter shellSorter = new ShellSorter();
        shellSorter.sort(a);
        shellSorter.sort(b);
        int cnt = 0;
        int i = 0, j = 0;
        for(; i < a.length && j < b.length;) {
            if (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else {
                i++;
                j++;
                cnt++;
            }
        }
        return cnt;
    }
}
