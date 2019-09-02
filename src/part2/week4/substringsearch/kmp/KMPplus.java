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
        // TODO: ADD YOUR CODE HERE
    }

    // return offset of first occurrence of text in pattern (or n if no match)
    // simulate the NFA to find match
    public int search(String text) {
        // TODO: ADD YOUR CODE HERE
        return 0;
    }

    public int getMaxJ() {
        return maxJ;
    }
}
