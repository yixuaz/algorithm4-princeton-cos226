package part2.week4.substringsearch.extracredit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchMultiplePattern {
    // only lower case
    private static final int M = 1000000007;
    private static final int R = 26;
    private final int len;
    private long base = 1;
    private final Map<Integer, String> fingerPrints;

    // all the pattern have the same length
    public SearchMultiplePattern(List<String> patterns) {
        if (patterns == null || patterns.isEmpty())
            throw new IllegalArgumentException("error input");
        fingerPrints = new HashMap<>();
        len = patterns.get(0).length();
        for (String pattern : patterns) {
            long fingerPrint = 0;
            for (char c : pattern.toCharArray()) {
                fingerPrint = (fingerPrint * R + c - 'a') % M;
            }
            fingerPrints.put((int) fingerPrint, pattern);
        }

        for (int i = 1; i < len; i++) {
            base = base * R % M;
        }
    }

    public String query(String text) {
        if (text == null || text.length() < len) {
            return null;
        }
        char[] txt = text.toCharArray();
        long hash = 0;
        for (int i = 0; i < txt.length; i++) {
            if (i >= len) {
                hash = (hash - base * (txt[i - len] - 'a') + M) % M;
            }
            hash = (hash * R + txt[i] - 'a') % M;
            if (i >= len - 1) {
                String possible = fingerPrints.get((int) hash);
                if (possible != null && possible.equals(text.substring(i - len + 1, i + 1))) {
                    return possible;
                }
            }
        }
        return null;
    }
}
