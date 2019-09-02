package part2.week3.radixsort.suffixarray;

import edu.princeton.cs.algs4.MSD;

import java.util.Arrays;

public class NaiveSuffixArray implements SuffixArray {
    @Override
    public int[] getSuffixArray(String s) {
        String[] suffixes = new String[s.length()];
        Integer[] idxs = new Integer[suffixes.length];
        for (int i = 0; i < idxs.length; i++) idxs[i] = i;
        for (int i = 0; i < s.length(); i++) { // O n^2
            suffixes[i] = s.substring(i);
        }
        Arrays.sort(idxs, (a, b)->{ // O n^2 LOG n
            return suffixes[a].compareTo(suffixes[b]);
        });
        int[] res = new int[idxs.length];
        for (int i = 0; i < res.length; i++) res[i] = idxs[i];
        return res;
    }
}
