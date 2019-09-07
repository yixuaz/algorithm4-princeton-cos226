package part1.week3.mergesort;

import commonutil.IMerger;
import commonutil.IResultAggr;
import commonutil.MergeSorter;

/**
 * we can calculate counting inversions on merge sort template. it is also divide and conquer;
 * there are two half, we calculate left part inversions number, also calculating the right part inversions.
 * then we need to merge them, in this process, we only need to calculate the part from right side go ahead left side.
 * for example. left side {1,3, 5} right side {2,4} so 2 is smaller than 3,5.
 * so when merge to {1,2,?, ?, ?} it will add two more inversion. 4 is smaller than 5.
 * so when merge to {1,2,3,4,?}, it will add one more inversion.
 */
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
