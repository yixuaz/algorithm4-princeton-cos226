package commonutil;

import week3.mergesort.MergeWithHalfArray;

public class MergeSorter implements ISorter {
    private InsertSorter insertSorter = new InsertSorter();

    @Override
    public void sort(int[] arr) {
        sort(arr,0,arr.length - 1);
    }
    private void sort(int[] arr, int lo, int hi) {
        if (hi - lo <= 7) {
            insertSorter.sort(arr,lo,hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(arr, lo, mid);
        sort(arr,mid + 1, hi);
        if (arr[mid] <= arr[mid + 1]) return;
        MergeWithHalfArray.merge(arr,lo,mid,hi);
    }

    public <T,E> E template(T[] arr, int lo, int hi, E endRet,
                            IMerger<T,E> merger, IResultAggr<E> resultAggr) {
        if (lo >= hi) return endRet;
        int mid = lo + (hi - lo) / 2;
        E res1 = template(arr, lo, mid, endRet, merger, resultAggr);
        E res2 = template(arr,mid + 1, hi, endRet, merger, resultAggr);
        E res3 = merger.merge(arr,lo,mid,hi);
        return resultAggr.aggr(res1,res2,res3);
    }
}
