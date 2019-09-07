package part1.week2.elementorysort;

import commonutil.ISorter;

/**
 * this problem could be solved by the hash set, but this lecture is totally about elementary sort,
 * we could use sort two array, then find a way to solve the problem.
 * think we can first sort by y then by x, and maintain two pointer. one iterate a[], one iterate b[].
 * move the small one, if they are same,then we find a answer.
 */
public class IntersectionOfTwoSet {
    public static int intersectionCount(int[] a, int[] b) {
        ISorter shellSorter = new ShellSorter();
        shellSorter.sort(a);
        shellSorter.sort(b);
        int cnt = 0;
        int i = 0, j = 0;
        for (; i < a.length && j < b.length; ) {
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
