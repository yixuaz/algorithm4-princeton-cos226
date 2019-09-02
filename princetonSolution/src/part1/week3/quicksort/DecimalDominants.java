package part1.week3.quicksort;

import java.util.ArrayList;
import java.util.List;

import static commonutil.PrimitiveArrayConverter.convertToIntArray;

public class DecimalDominants {
    public static int[] getEleOccurMoreThanOneOfTen(int[] arr) {
        return convertToIntArray(getOccurMoreThanOneOfK(arr,10));
    }
    private static List<Integer> getOccurMoreThanOneOfK(int[] arr, int k) {//O (k * N)
        int[] cnt = new int[k - 1];
        int[] candidate = new int[k - 1];
        for (int i : arr) {
            if (joinSameKindSuccess(cnt,candidate,i))
                continue;
            if (findEmptyPosSuccess(cnt,candidate,i))
                continue;
            decreaseOneInEachPos(cnt);
        }
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] > 0) {
                candidates.add(candidate[i]);
            }
        }
        List<Integer> ans = new ArrayList<>();
        for (int candi : candidates) {
            if (checkMoreThanOneOfK(candi,arr,k)) {
                ans.add(candi);
            }
        }
        return ans;
    }

    private static boolean checkMoreThanOneOfK(int candi, int[] arr, int k) {
        int expect = arr.length / k + 1;
        for (int i : arr) {
            if (candi == i) {
                expect--;
            }
        }
        return expect <= 0;
    }

    private static void decreaseOneInEachPos(int[] cnt) {
        for (int i = 0; i < cnt.length; i++) {
            assert cnt[i] > 0;
            cnt[i]--;
        }
    }

    private static boolean findEmptyPosSuccess(int[] cnt, int[] candidate, int val) {
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] == 0) {
                cnt[i]++;
                candidate[i] = val;
                return true;
            }
        }
        return false;
    }

    private static boolean joinSameKindSuccess(int[] cnt, int[] candidate, int val) {
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] > 0 && candidate[i] == val) {
                cnt[i]++;
                return true;
            }
        }
        return false;
    }

}
