package part2.week4.substringsearch.kmp;

public class KMP implements MyKMP{
    private final int[][] dfa;
    private final char[] pat;
    private final int basePos;
    private int maxJ; // for Tandem repeats Questions
    public KMP(String pattern) {
        pat = pattern.toCharArray();
        int m = pat.length;
        int R = 256;
        basePos = 0;
        dfa = new int[R][m];
        dfa[pat[0]][0] = 1;
        for (int i = 1, x = 0; i < m; i++) {
            for (int j = 0; j < R; j++) {
                dfa[j][i] = dfa[j][x];
            }
            dfa[pat[i]][i] = i + 1;
            x = dfa[pat[i]][x];
        }
    }
    public KMP(String pattern, int R, int base) {
        pat = pattern.toCharArray();
        int m = pat.length;
        this.basePos = base;
        dfa = new int[R][m];
        dfa[pat[0] - base][0] = 1;
        for (int i = 1, x = 0; i < m; i++) {
            for (int j = 0; j < R; j++) {
                dfa[j][i] = dfa[j][x];
            }
            dfa[pat[i] - base][i] = i + 1;
            x = dfa[pat[i] - base][x];
        }
    }
    public int search(String text) {
        maxJ = 0;
        char[] txt = text.toCharArray();
        int j = 0, i;
        for (i = 0; i < txt.length && j < pat.length; i++) {
            j = dfa[txt[i] - basePos][j];
            maxJ = Math.max(j, maxJ);
        }
        if (j == pat.length) return i - j;
        return -1;
    }

    public int getMaxJ() {
        return maxJ;
    }
}
