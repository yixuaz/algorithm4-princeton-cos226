package week3.mergesort;

import commonutil.IMerger;
import commonutil.IResultAggr;
import commonutil.MergeSorter;

public class CountInversion {
    private static MergeSorter mergeSorter = new MergeSorter();
    private class CountInversionMerger implements IMerger<Integer,Integer> {
        @Override
        public Integer merge(Integer[] arr, int lo, int mid, int hi) {
            int result = 0;
            int[] aux = new int[mid - lo + 1];
            for (int i = lo; i <= mid; i++) aux[i - lo] = arr[i];
            for (int k = lo, preIdx = 0, postIdx = mid + 1; k <= hi; k++) {
                if (preIdx == aux.length) arr[k] = arr[postIdx++];
                else if (postIdx > hi) arr[k] = aux[preIdx++];
                else if (aux[preIdx] <= arr[postIdx]) arr[k] = aux[preIdx++];
                else {
                    result += aux.length - preIdx;
                    arr[k] = arr[postIdx++];
                }
            }
            return result;
        }
    }
    private class SumResultAggr implements IResultAggr<Integer> {
        @Override
        public Integer aggr(Integer e1, Integer e2, Integer e3) {
            return e1 + e2 + e3;
        }
    }
    public int solve(int[] arr) {
        Integer[] convert = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            convert[i] = arr[i];
        }
        return  mergeSorter.template(convert,0,arr.length - 1, 0,
                new CountInversionMerger(), new SumResultAggr());
    }
}
