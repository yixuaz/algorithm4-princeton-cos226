package part2.week4.project.better;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import part2.week4.project.BoggleBoard;

public class BoggleSolver {
    private static final int Q = 'Q';
    private static final int U = 'U';
    private static final int A = 'A';
    private static final int SEEN = '#';

    private class TrieNode {
        TrieNode[] chds = new TrieNode[26];
        String word = null;
        int cnt = 0;
    }

    private final int[] scores = { 0, 0, 0, 1, 1, 2, 3, 5, 11 };
    private final TrieNode root;

    private int[][] graph;
    private int[] letter;
    private int curCnt;
    private int letterSize;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        root = new TrieNode();
        for (String word : dictionary) {
            if (word.length() < 3) continue;
            TrieNode p = root;
            char[] wordChars = word.toCharArray();
            if (!validQ(wordChars)) continue;
            int pre = 0;
            for (char c : wordChars) {
                if (pre == Q) {
                    pre = 0;
                    continue;
                }
                int i = c - A;
                if (p.chds[i] == null) {
                    p.chds[i] = new TrieNode();
                }
                p = p.chds[i];
                pre = c;
            }
            p.word = word;
        }
        letterSize = 16;
        letter = new int[letterSize];
        graph = new int[letterSize][];

    }

    private boolean validQ(char[] wordChars) {
        for (int k = 0; k < wordChars.length; k++) {
            if (wordChars[k] != Q) continue;
            if (k + 1 == wordChars.length || wordChars[k + 1] != U) {
                return false;
            }
        }
        return true;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        curCnt++;
        precomputeGraph(board);
        Bag<String> result = new Bag<>();
        for (int i = 0; i < letterSize; i++) {
            TrieNode next = root.chds[letter[i]];
            if (next != null) {
                dfs(next, i, result);
            }
        }
        return result;
    }

    private void precomputeGraph(BoggleBoard board) {
        int hei = board.rows(), wid = board.cols();
        int curSize = hei * wid;
        letterSize = curSize;
        if (curSize > letter.length) {
            letter = new int[curSize];
            graph = new int[curSize][];
        }
        int[] list = new int[8];
        int listIdx = 0;
        for (int i = 0; i < hei; i++) {
            boolean upSafe = i > 0, downSafe = i < hei - 1;
            int base = i * wid;
            for (int j = 0; j < wid; j++) {
                boolean leftSafe = j > 0, rightSafe = j < wid - 1;
                int key = base + j;
                int val = board.getLetter(i, j) - A;
                letter[key] = val;
                if (upSafe && leftSafe) list[listIdx++] = key - wid - 1;
                if (upSafe) list[listIdx++] = key - wid;
                if (leftSafe) list[listIdx++] = key - 1;
                if (upSafe && rightSafe) list[listIdx++] = key - wid + 1;
                if (rightSafe) list[listIdx++] = key + 1;
                if (downSafe && leftSafe) list[listIdx++] = key - 1 + wid;
                if (downSafe) list[listIdx++] = key + wid;
                if (downSafe && rightSafe) list[listIdx++] = key + 1 + wid;
                graph[key] = new int[listIdx];
                System.arraycopy(list, 0, graph[key], 0, listIdx);
                listIdx = 0;
            }
        }
    }

    private void dfs(TrieNode cur, int pos, Bag<String> result) {
        int curLetter = letter[pos];
        letter[pos] = SEEN;
        if (cur.word != null && cur.cnt < curCnt) {
            result.add(cur.word);
            cur.cnt = curCnt;
        }
        for (int nei : graph[pos]) {
            int neiVal = letter[nei];
            if (neiVal == SEEN) continue;
            TrieNode next = cur.chds[neiVal];
            if (next == null) continue;
            dfs(next, nei, result);
        }
        letter[pos] = curLetter;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        char[] wordChars = word.toCharArray();
        if (!validQ(wordChars)) return 0;
        TrieNode p = root;
        int pre = 0;
        for (char ch : wordChars) {
            if (pre == Q) {
                pre = 0;
                continue;
            }
            int c = ch - A;
            if (p.chds[c] == null)
                return 0;
            p = p.chds[c];
            pre = ch;
        }
        int len = Math.min(8, word.length());
        return p.word == null ? 0 : scores[len];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        if (args.length > 2) {
            // algs4 ~ 37000 yawl ~ 7000
            int n = Integer.parseInt(args[2]);
            BoggleBoard[] boards = new BoggleBoard[n];
            for (int i = 0; i < n; i++)
                boards[i] = new BoggleBoard();
            Stopwatch sw = new Stopwatch();
            for (int i = 0; i < n; i++)
                solver.getAllValidWords(boards[i]);
            StdOut.println(sw.elapsedTime());
            return;
        }
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
