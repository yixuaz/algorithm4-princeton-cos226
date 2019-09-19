package part2.week4.trie.extracredit;

import java.util.List;

public class LongestPrefix {
    private class TrieNode {
        String val;
        TrieNode[] chds = new TrieNode[11];
    }
    private final TrieNode root;
    // assumption string only contains 0~9 and .
    public LongestPrefix(List<String> dict) {
        root = new TrieNode();
        for (String ip : dict) {
            TrieNode p = root;
            for (char c : ip.toCharArray()) {
                int idx = val(c);
                if (p.chds[idx] == null) {
                    p.chds[idx] = new TrieNode();
                }
                p = p.chds[idx];
            }
            p.val = ip;
        }
    }

    private int val(char c) {
        if (c == '.') return 10;
        int val = c - '0';
        if (val < 0 || val > 9) {
            throw new IllegalArgumentException("input invalid : " + c);
        }
        return val;
    }


    public String solve(String query) {
        TrieNode p = root;
        char[] queryChars = query.toCharArray();
        String ans = null;
        for (char c : queryChars) {
            if (p.val != null) {
                ans = p.val;
            }
            if (p.chds[val(c)] == null) {
                break;
            }
            p = p.chds[val(c)];
        }
        return ans;
    }
}
