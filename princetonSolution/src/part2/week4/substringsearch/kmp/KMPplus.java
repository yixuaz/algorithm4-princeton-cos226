package part2.week4.substringsearch.kmp;


public class KMPplus implements MyKMP{
    private char[] pat;
    private int[] next;
    private int maxJ; // for Tandem repeats Questions

    // create Knuth-Morris-Pratt NFA from pattern
    public KMPplus(String pattern) {
        pat = pattern.toCharArray();
        int m = pat.length;
        next = new int[m];
        next[0] = -1;
        // next[i] means , if i position mismatch, where should j could be a possible match position.
        // invariant: in each loop [...j] = [...i], so [..j]c[j+1] == [...i]c[i+1]
        // next[i] should be same as mismatch on j pos, so next[i] = next[j]
        // if [..j]c[j+1] != [...i]c[i+1],
        // we can try maybe c[j] could be equal c[i+1], when [...]c[j] = [...]c[i]c[i+1]
        // so next[i] = j;
        int rank = 0, j = 0;
        for (int i = 1; i < m; i++) {
            // assert pattern.substring(0,j).equals(pattern.substring(i - j,i)); //invariant
            next[i] = pat[i] == pat[j] ? next[j] : j;
            while (j >= 0 && pat[i] != pat[j])
                j = next[j];
            j++;
        }
    }

    // return offset of first occurrence of text in pattern (or n if no match)
    // simulate the NFA to find match
    public int search(String text) {
        maxJ = 0;
        char[] txt = text.toCharArray();
        int j = 0, i;
        for (i = 0; i < txt.length && j < pat.length; i++) {
            while (j >= 0 && pat[j] != txt[i])
                j = next[j];
            j++;
            maxJ = Math.max(j, maxJ);
        }
        if (j == pat.length) return i - j;
        return -1;
    }

    public int getMaxJ() {
        return maxJ;
    }
}
