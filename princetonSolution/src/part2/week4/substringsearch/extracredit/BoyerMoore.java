package part2.week4.substringsearch.extracredit;

import java.util.Arrays;

public class BoyerMoore {
    public static int search(String text, String pattern) {
        char[] txt = text.toCharArray();
        char[] pat = pattern.toCharArray();
        int M = pat.length, N = txt.length;
        int[] right = new int[256];
        Arrays.fill(right, -1);
        for (int i = 0; i < M; i++) {
            right[pat[i]] = i;
        }
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat[j] != txt[i + j]) {
                    skip = Math.max(1, j - right[txt[i + j]]);
                    break;
                }
            }
            if (skip == 0)
                return i;
        }
        return -1;
    }
}
