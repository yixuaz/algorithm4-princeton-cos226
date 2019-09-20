package part2.week4.substringsearch;

import part2.week4.substringsearch.kmp.KMPplus;

/**
 * we append same string on the origin string. eg. 'abc' -> 'abcabc' then run KMP to check other str
 * in substring of 'abcabc' and same length with 'abc'
 */
public class CyclicRotationString {
    public static boolean isCyclicRotation(String a, String b) {
        if (a.length() != b.length())
            return false;
        KMPplus kmp = new KMPplus(b);
        return kmp.search(a + a) != -1;
    }
}
