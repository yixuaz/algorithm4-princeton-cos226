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
        // TODO: ADD YOUR CODE HERE
    }

    // for example for a-z, R = 26, base = 'a'
    public KMP(String pattern, int R, int base) {
        pat = pattern.toCharArray();
        int m = pat.length;
        this.basePos = base;
        dfa = new int[R][m];
        // TODO: ADD YOUR CODE HERE
    }
    public int search(String text) {
        // TODO: ADD YOUR CODE HERE
        return 0;
    }

    // for Tandem repeats Questions
    public int getMaxJ() {
        return maxJ;
    }
}
