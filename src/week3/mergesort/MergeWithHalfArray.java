package week3.mergesort;

public class MergeWithHalfArray {
    public static void merge(int[] arr, int lo, int mid, int hi) {
        assert isSort(arr,lo,mid);
        assert isSort(arr,lo,mid);
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
