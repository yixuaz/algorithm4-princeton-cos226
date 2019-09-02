package part1.week3.mergesort;

public class MergeWithHalfArray {
    public static void merge(int[] arr, int lo, int mid, int hi) {
        assert isSort(arr,lo,mid);
        assert isSort(arr,lo,mid);
        int[] aux = new int[mid - lo + 1];
        for (int i = lo; i <= mid; i++) {
            aux[i - lo] = arr[i];
        }
        for (int k = lo, j = 0, i = mid + 1; k <= hi; k++) {
            if (j == aux.length) arr[k] = arr[i++];
            else if (i > hi) arr[k] = aux[j++];
            else if (aux[j] <= arr[i]) arr[k] = aux[j++];
            else arr[k] = arr[i++];
        }
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
