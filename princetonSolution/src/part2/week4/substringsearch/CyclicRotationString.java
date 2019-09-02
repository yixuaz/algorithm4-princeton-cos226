package part2.week4.substringsearch;

import part2.week4.substringsearch.kmp.KMPplus;

public class CyclicRotationString {
    public static boolean isCyclicRotation(String a, String b) {
        if (a.length() != b.length())
            return false;
        KMPplus kmp = new KMPplus(b);
        return kmp.search(a + a) != -1;
    }
}
