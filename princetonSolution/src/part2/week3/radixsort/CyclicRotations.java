package part2.week3.radixsort;


import part2.week3.radixsort.suffixarray.ManberMyerSuffixArray;
import part2.week3.radixsort.suffixarray.SuffixArray;

import java.util.HashSet;
import java.util.Set;

public class CyclicRotations {

    public static boolean solve(String[] inputs) {
        Set<String> fingerPrints = new HashSet<>();
        SuffixArray suffixArrayAlgo = new ManberMyerSuffixArray();
        for (String s : inputs) {
            int[] sfxArr = suffixArrayAlgo.getSuffixArray(s + s); // O (L * LOG(L))
            String fingerPrint = convertFingerPrint(s, sfxArr);
            if (!fingerPrints.add(fingerPrint))
                return true;
        }
        return false;
    }

    private static String convertFingerPrint(String s, int[] suffixArray) {
        int largestIdx = suffixArray[suffixArray.length - 1];
        return s.substring(largestIdx) + s.substring(0, largestIdx);
    }
}
