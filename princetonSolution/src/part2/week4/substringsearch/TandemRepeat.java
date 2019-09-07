package part2.week4.substringsearch;

import part2.week4.substringsearch.kmp.KMP;
import part2.week4.substringsearch.kmp.KMPplus;
import part2.week4.substringsearch.kmp.MyKMP;

public class TandemRepeat {
    public static int solve(String pattern, String text) {
        int m = text.length();
        int n = pattern.length();
        if (n == 0) return -1;
        int k = m / n;
        if (k == 0) return 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            sb.append(pattern);
        }
        //MyKMP kmp = new KMP(sb.toString(),26,'a');
        MyKMP kmp = new KMPplus(sb.toString());

                kmp.search(text);
        return kmp.getMaxJ() / n;
    }
}