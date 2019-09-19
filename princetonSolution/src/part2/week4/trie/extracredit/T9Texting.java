package part2.week4.trie.extracredit;

import java.util.ArrayList;
import java.util.List;

public class T9Texting {
    private class TrieNode {
        String val;
        TrieNode[] chds = new TrieNode[26];
    }

    private final TrieNode root;

    // assumption string only contains a~z
    public T9Texting(List<String> dict) {
        root = new TrieNode();
        for (String ip : dict) {
            TrieNode p = root;
            for (char c : ip.toCharArray()) {
                int idx = c - 'a';
                if (p.chds[idx] == null) {
                    p.chds[idx] = new TrieNode();
                }
                p = p.chds[idx];
            }
            p.val = ip;
        }
    }

    private final int[][] map = {{}, {}, {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {9, 10, 11},
            {12, 13, 14}, {15, 16, 17, 18}, {19, 20, 21}, {22, 23, 24, 25}};

    public List<String> query(String input) {
        char[] cs = input.toCharArray();
        List<String> result = new ArrayList<>();
        dfs(cs, 0, result, root);
        return result;
    }

    private void dfs(char[] cs, int i, List<String> result, TrieNode p) {
        if (result.size() >= 10) {
            return;
        }
        if (i == cs.length) {
            if (p.val != null) {
                result.add(p.val);
            }
            return;
        }
        for (int possible : map[cs[i] - '0']) {
            if (p.chds[possible] != null) {
                dfs(cs, i + 1, result, p.chds[possible]);
            }
        }
    }
}
