package part1.week2.elementorysort;

import commonutil.ISorter;
import commonutil.InsertSorter;

public class ShellSorter implements ISorter {
    private InsertSorter insertSorter = new InsertSorter();

    public void sort(int[] arr) {
        int k = 1, n = arr.length;
        while (3 * k + 1 < n) k = 3 * k + 1;
        while (k >= 1) {
            insertSorter.sort(arr, 0, n - 1, k);
            k /= 3;
        }
    }


}
