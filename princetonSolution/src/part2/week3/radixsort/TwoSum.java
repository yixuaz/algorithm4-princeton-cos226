package part2.week3.radixsort;

public class TwoSum {
    public static boolean solve(int[] arr, int tar) {
        LSDRadixSorter sorter = new LSDRadixSorter();
        sorter.sort(arr);
        int i = 0, j = arr.length - 1;
        while (i <= j) {
            long cur = (long)arr[i] + arr[j];
            if (cur > tar) j--;
            else if (cur < tar) i++;
            else return true;
        }
        return false;
    }
}
