package commonutil;

public class InsertSorter implements ISorter{
    @Override
    public void sort(int[] arr) {
        sort(arr,0,arr.length - 1, 1);
    }
    public void sort(int[] arr, int from, int to) {
        sort(arr,from,to,1);
    }
    public void sort(int[] arr, int from, int to, int step) {
        for (int i = from + step; i <= to; i++) {
            int cur = arr[i], j = i - step;
            for (; j >= from && arr[j] > cur; j -= step) {
                arr[j + step] = arr[j];
            }
            arr[j + step] = cur;
        }
    }
}
