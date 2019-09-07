package part1.week3.mergesort;

/**
 * Merging with smaller auxiliary array. Suppose that the subarray a[0] to a[n−1]
 * is sorted and the subarray a[n] to a[2∗n−1] is sorted.
 * How can you merge the two subarrays so that a[0] to a[2∗n−1] is sorted
 * using an auxiliary array of length nn (instead of 2n)?
 */
public class MergeWithHalfArray {
    public static void merge(int[] arr, int lo, int mid, int hi) {
        assert isSort(arr, lo, mid);
        assert isSort(arr, mid + 1, hi);
        int[] aux = new int[mid - lo + 1];
        //TODO: ADD YOUR CODE HERE
        assert isSort(arr,lo,hi);
    }

    private static boolean isSort(int[] arr, int i, int j) {
        for (int k = i + 1; k <= j; k++) {
            if (arr[k - 1] > arr[k])
                return false;
        }
        return true;
    }

}
