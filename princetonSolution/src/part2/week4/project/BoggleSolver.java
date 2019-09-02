package part2.week4.project;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import part2.week4.project.BoggleBoard;

public class BoggleSolver {
    private static final int Q = 'Q';
    private static final int U = 20;
    private static final int A = 'A';

    private class TrieNode {
        TrieNode[] chds = new TrieNode[26];
        String word = null;
        int cnt = 0;
    }

    private final int[] scores = { 0, 0, 0, 1, 1, 2, 3, 5, 11 };
    private final int[][] neighbors = {
            { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 }
    };
    private final TrieNode root;
    private int curCnt;
    private int rows, cols;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        root = new TrieNode();
        for (String word : dictionary) {
            if (word.length() < 3) continue;
            TrieNode p = root;
            char[] wordChars = word.toCharArray();
            for (char c : wordChars) {
                int i = c - A;
                if (p.chds[i] == null) {
                    p.chds[i] = new TrieNode();
                }
                p = p.chds[i];
            }
            p.word = word;
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        curCnt++;
        rows = board.rows();
        cols = board.cols();
        Bag<String> result = new Bag<>();
        boolean[][] seen = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (root.chds[board.getLetter(i, j) - A] != null) {
                    dfs(root.chds[board.getLetter(i, j) - A], i, j, result, seen, board);
                }
            }
        }
        return result;
    }

    private void dfs(TrieNode cur, int y, int x, Bag<String> result, boolean[][] seen,
                     BoggleBoard board) {
        if (board.getLetter(y, x) == Q) {
            if (cur.chds[U] == null) return;
            cur = cur.chds[U];
        }
        seen[y][x] = true;
        if (cur.word != null && cur.cnt < curCnt) {
            result.add(cur.word);
            cur.cnt = curCnt;
        }
        for (int[] nei : neighbors) {
            int ny = nei[0] + y, nx = nei[1] + x;
            if (ny < 0 || nx < 0 || ny == rows || nx == cols || seen[ny][nx]) continue;
            int c = board.getLetter(ny, nx) - A;
            TrieNode next = cur.chds[c];
            if (next == null) continue;
            dfs(next, ny, nx, result, seen, board);
        }
        seen[y][x] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        char[] wordChars = word.toCharArray();
        TrieNode p = root;
        for (char ch : wordChars) {
            int c = ch - A;
            if (p.chds[c] == null)
                return 0;
            p = p.chds[c];
        }
        int len = Math.min(8, word.length());
        return p.word == null ? 0 : scores[len];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}