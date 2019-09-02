package part2.week3.radixsort.suffixarray;

import java.util.Arrays;

public class ManberMyerSuffixArray implements SuffixArray {
    @Override
    public int[] getSuffixArray(String s) {
        char[] cs = s.toCharArray();
        return buildSuffixArray(cs, cs.length);
    }
    private void sort(Suffix[] suffixes) {
        Arrays.sort(suffixes, (a,b) -> {
            if (a.curRank == b.curRank)
                return Integer.compare(a.nextRank, b.nextRank);
            return Integer.compare(a.curRank, b.curRank);
        });
    }
    private int[] buildSuffixArray(char[] cs, int n) {
        Suffix[] suffixes = new Suffix[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = new Suffix(i, cs[i], val(cs, i + 1));
        }
        sort(suffixes);
        // This array is needed to get the index in suffixes[] from original index. Mapping is needed to get next suffix.
        int[] ind = new int[n];
        for (int k = 4; k < 2 * n; k <<= 1) {
            int rank = 0;
            int prevRank = suffixes[0].curRank;
            suffixes[0].curRank = rank;
            ind[suffixes[0].index] = 0;
            for (int i = 1; i < n; i++) {
                if (suffixes[i].curRank == prevRank && suffixes[i].nextRank == suffixes[i - 1].nextRank) {
                    prevRank = suffixes[i].curRank;
                    suffixes[i].curRank = rank;
                } else {
                    prevRank = suffixes[i].curRank;
                    suffixes[i].curRank = ++rank;
                }
                ind[suffixes[i].index] = i;
            }

            for (int i = 0; i < n; i++) {
                int nextIdx = suffixes[i].index + k / 2;
                suffixes[i].nextRank = (nextIdx < n) ? suffixes[ind[nextIdx]].curRank : -1;
            }
            sort(suffixes);
        }
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[i] = suffixes[i].index;
        return res;
    }
    private int val(char[] cs, int pos) {
        if (pos < cs.length) return cs[pos];
        return -1;
    }

    private class Suffix {
        private int index;
        private int curRank;
        private int nextRank;

        public Suffix(int index, int curRank, int nextRank) {
            this.index = index;
            this.curRank = curRank;
            this.nextRank = nextRank;
        }
    }
}
