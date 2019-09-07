package part1.week3.quicksort;

/**
 * all three problem could be solved by the third one.
 * to solve third one, i will introduce a concept **cut**. if cut is -1, so we will cut the array into two part.
 * the first part last element index is -1, the second part first element index is 0.
 * so we can binary search the cut on the small length array. according to cut1 and k, we should know where is cut2
 * we cut the two array. so there are four element is neighbor of the two cut.
 * L1 is the first array left part last element, R1 is the first aray right part first element.
 * the invariant is cut1 + 1 + cut2 + 2 == k
 * if the place we cut which R1 < L2, means cut1 could be larger
 * if R2 < L1, means cut1 too larger, should be less.
 * if L2 < R1 && L1 < R2. we find the correct cut, so just return ans.
 */
public class SelectionInTwoSortedArray {
    public static int getHalfOfNthElementInTwoSameLengthArray(int[] A, int[] B) {
        assert A.length == B.length;
        int k = A.length;
        if (k <= 0) {
            throw new IllegalArgumentException("invalid k " + k);
        }
        return help(A, B, k);
    }

    public static int getHalfOfNthElementInTwoArray(int[] A, int[] B) {
        int k = (A.length + B.length) / 2;
        if (k <= 0) {
            throw new IllegalArgumentException("invalid k " + k);
        }
        return help(A, B, k);
    }

    public static int getKthElementInTwoArray(int[] A, int[] B, int k) {
        if (k <= 0 || k > A.length + B.length) {
            throw new IllegalArgumentException("invalid k " + k);
        }
        return help(A, B, k);
    }

    private static int help(int[] A, int[] B, int k) { // need k th element
        int l1 = A.length, l2 = B.length;
        if (l2 < l1) return help(B, A, k);
        // iterate cut cut = i means including ith element in the cut. so cut -1 means use 0 element in A
        int s = Math.max(k - B.length - 1, -1), e = Math.min(k - 1, l1 - 1);
        while (s <= e) {
            int cut1 = (e - s) / 2 + s;
            int cut2 = (k - (cut1 + 1)) - 1; // cut1 + 1 means how many element used in A.
            int L1 = cut1 == -1 ? Integer.MIN_VALUE : A[cut1];
            int R1 = cut1 == l1 - 1 ? Integer.MAX_VALUE : A[cut1 + 1];
            int L2 = cut2 == -1 ? Integer.MIN_VALUE : B[cut2];
            int R2 = cut2 == l2 - 1 ? Integer.MAX_VALUE : B[cut2 + 1];
            assert cut1 + cut2 == k - 2; // so cut 1 pos, cut 2 pos get max , is k
            if (R1 < L2) {
                s = cut1 + 1;
            } else if (R2 < L1) {
                e = cut1 - 1;
            } else {
                return Math.max(L1, L2);
            }
        }
        throw new IllegalStateException("INVALID AREA");
    }
}
