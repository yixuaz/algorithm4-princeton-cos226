package part2.week5.datacompression.lzw;


import edu.princeton.cs.algs4.TrieST;

import java.util.Arrays;

public class MyLZW {
    private static final int L = 256;
    private class TrieNode {
        TrieNode[] chd = new TrieNode[R];
        Integer val;
        String str;
    }
    private final int R;
    private final int base;
    private String[] st;
    private TrieNode root;
    public MyLZW(int r, int base) {
        R = r;
        this.base = base;
    }
    private void init(int r, int base) {
        root = new TrieNode();
        st = new String[L];
        for (int i = 0; i < r; i++) {
            root.chd[i] = new TrieNode();
            root.chd[i].val = i;
            root.chd[i].str = (char)(i + base) + "";
            st[i] = root.chd[i].str;
        }
    }

    public byte[] compress(String input) {
        init(R, base);
        int code = R;
        byte[] ret = new byte[L];
        int len = 0, idx = 0;
        while (idx < input.length()) {
            TrieNode node = getLongestPrefix(input, idx);
            ret[len++] = node.val.byteValue();
            if (idx + node.str.length() < input.length() && code < L)
                add(node.str + input.charAt(idx + node.str.length()), code++);
            idx += node.str.length();
        }
        return Arrays.copyOfRange(ret, 0, len);
    }

    private TrieNode getLongestPrefix(String input, int idx) {
        TrieNode p = root;
        for (int i = idx; i < input.length(); i++) {
            char c = input.charAt(i);
            if (p.chd[c - base] == null) break;
            p = p.chd[c - base];
        }
        return p;
    }

    private void add(String s, int val) {
        TrieNode p = root;
        for (char c : s.toCharArray()) {
            if (p.chd[c - base] == null)
                p.chd[c - base] = new TrieNode();
            p = p.chd[c - base];
        }
        p.val = val;
        p.str = s;
    }

    public String expand(byte[] input) {
        int idx = R;
        StringBuilder stringBuilder = new StringBuilder();
        String val = st[input[0]];
        for (int i = 1; i < input.length; i++) {
            stringBuilder.append(val);
            int curIdx = input[i] + (input[i] < 0 ?  + 256 : 0);
            String next = st[curIdx];
            if (idx == curIdx) next = val + val.charAt(0);
            if (idx < L) st[idx++] = val + next.charAt(0);
            val = next;
        }
        stringBuilder.append(val);
        return stringBuilder.toString();
    }
}
