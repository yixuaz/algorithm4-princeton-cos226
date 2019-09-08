package part1.week4.project.better;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private static final char END_LINE = '\n';

    private final char[] tiles;
    private final int n;
    private final int manhattan;
    private final int hamming;
    private final int zeroIdx;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length < 2)
            throw new IllegalArgumentException("tiles is not correct");
        n = tiles.length;
        this.tiles = new char[n * n];
        int zeroPos = 0;
        for (int i = 0; i < n; i++) {
            int base = i * n;
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    zeroPos = base + j;
                this.tiles[base + j] = (char) tiles[i][j];
            }
        }
        zeroIdx = zeroPos;
        manhattan = preManhattan();
        hamming = preHamming();
    }

    private Board(char[] tiles, int n, int zeroIdx, int manhattan, int hamming) {
        this.tiles = tiles.clone();
        this.n = n;
        this.manhattan = manhattan;
        this.hamming = hamming;
        this.zeroIdx = zeroIdx;
    }

    private Board(char[] tiles, int n, int zeroIdx) {
        this.tiles = tiles.clone();
        this.n = n;
        this.manhattan = preManhattan();
        this.hamming = preHamming();
        this.zeroIdx = zeroIdx;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append(END_LINE);
        for (int i = 0; i < n * n; i++) {
            s.append((int) tiles[i]).append(" ");
            if (i % n == n - 1)
                s.append(END_LINE);
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    private int preHamming() {
        int cnt = 0, expect = 1;
        for (int i = 0; i < n; i++) {
            int base = i * n;
            for (int j = 0; j < n; j++, expect++) {
                if (tiles[base + j] == expect || expect == n * n) {
                    continue;
                }
                cnt++;
            }
        }
        return cnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private int preManhattan() {
        int distance = 0, expect = 1;
        for (int i = 0; i < n; i++) {
            int base = i * n;
            for (int j = 0; j < n; j++, expect++) {
                if (tiles[base + j] == expect || tiles[base + j] == 0) {
                    continue;
                }
                int expectRow = (tiles[base + j] - 1) / n, expectCol = (tiles[base + j] - 1) % n;
                distance += Math.abs(expectRow - i) + Math.abs(expectCol - j);
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.n != this.n) return false;
        int end = n * n;
        for (int i = 0; i < end; i++) {
            if (that.tiles[i] != tiles[i])
                return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> nei = new ArrayList<>(4);
        int y = zeroIdx / n, x = zeroIdx % n;
        if (y > 0) nei.add(neighbor(zeroIdx - n));
        if (y < n - 1) nei.add(neighbor(zeroIdx + n));
        if (x > 0) nei.add(neighbor(zeroIdx - 1));
        if (x < n - 1) nei.add(neighbor(zeroIdx + 1));
        return Collections.unmodifiableList(nei);
    }

    private Board neighbor(int newZeroIdx) {
        int hammingDiff = calHammingDiff(newZeroIdx);
        int manhantanDiff = calManhantanDiff(newZeroIdx);
        swap(zeroIdx, newZeroIdx);
        Board ret = new Board(tiles,
                              n, newZeroIdx, manhattan + manhantanDiff, hamming + hammingDiff);
        swap(newZeroIdx, zeroIdx);
        return ret;
    }

    private int calManhantanDiff(int j) {
        if (j + 1 == tiles[j]) return 1;
        int tarx = (tiles[j] - 1) % n;
        int tary = (tiles[j] - 1) / n;
        int curx = j % n;
        int cury = j / n;
        int newx = zeroIdx % n;
        int newy = zeroIdx / n;
        int newManhantan = Math.abs(tary - newy) + Math.abs(tarx - newx);
        int oldManhantan = Math.abs(tary - cury) + Math.abs(tarx - curx);
        return Integer.compare(newManhantan, oldManhantan);
    }

    private int calHammingDiff(int j) {
        if (j + 1 == tiles[j]) return 1;
        if (zeroIdx + 1 == tiles[j]) return -1;
        return 0;
    }


    private void swap(int i, int j) {
        char tmp = tiles[i];
        tiles[i] = tiles[j];
        tiles[j] = tmp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board ret;
        int y = (tiles[0] == 0 || tiles[1] == 0) ? n : 0;

        swap(y, y + 1);
        ret = new Board(tiles, n, zeroIdx);
        swap(y + 1, y);
        return ret;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] t = { { 1, 2 }, { 3, 0 } };
        Board b = new Board(t);
        System.out.println(b);
        System.out.println("ori goal:" + b.isGoal());
        Board twin = b.twin();
        System.out.println(twin);
        System.out.println("twin goal:" + twin.isGoal());
        System.out.println("manhattan:" + twin.manhattan());
        for (Board nei : twin.neighbors()) {
            System.out.println("nei :" + nei);
        }
        System.out.println(twin.equals(b));
        System.out.println(b.equals(new Board(t)));
        int[][] t2 = { { 0, 2 }, { 1, 3 } };
        Board b2 = new Board(t2);
        System.out.println(b2.twin());
        System.out.println(b2.twin().equals(b2));
    }

}