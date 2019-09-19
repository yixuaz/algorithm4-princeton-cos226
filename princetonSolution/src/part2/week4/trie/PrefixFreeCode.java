package part2.week4.trie;

/**
 * insert the binary strings into a 2-way trie. then during insert check the path could not have the node
 * which have value, and when it reach end, there should not exists more children in the node.
 */
public class PrefixFreeCode {
    public static boolean isPrefixFree(String[] input) {
        TrieNode root = new TrieNode();
        for (String s : input) {
            TrieNode p = root;
            char[] cur = s.toCharArray();
            for (int i = 0; i < cur.length; i++) {
                int key = cur[i] - '0';
                if (p.chds[key] == null) {
                    p.chds[key] = new TrieNode();
                }
                p = p.chds[key];
                if (p.isEnd)
                    return false;
            }
            p.isEnd = true;
            if (p.chds[0] != null || p.chds[1] != null)
                return false;
        }
        return true;
    }

    private static class TrieNode {
        boolean isEnd = false;
        TrieNode[] chds = new TrieNode[2];
    }
}
