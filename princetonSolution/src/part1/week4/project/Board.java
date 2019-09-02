package part1.week4.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private static final char END_LINE = '\n';

    private final int[][] tiles;
    private final int n;
    private final int manhattan;
    private final int hamming;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null || tiles.length < 2)
            throw new IllegalArgumentException("tiles is not correct");
        n = tiles.length;
        this.tiles = new int[n][];
        for (int i = 0; i < n; i++)
            this.tiles[i] = tiles[i].clone();
        manhattan = preManhattan();
        hamming = preHamming();
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append(END_LINE);
        for (int[] row : tiles) {
            for (int i = 0; i < row.length; i++)
                stringBuilder.append(' ').append(row[i]);
            stringBuilder.append(END_LINE);
        }
        return stringBuilder.toString();
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
            for (int j = 0; j < n; j++, expect++) {
                if (tiles[i][j] == expect || expect == n * n) {
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
            for (int j = 0; j < n; j++, expect++) {
                if (tiles[i][j] == expect || tiles[i][j] == 0) {
                    continue;
                }
                int expectRow = (tiles[i][j] - 1) / n, expectCol = (tiles[i][j] - 1) % n;
                distance += Math.abs(expectRow - i) + Math.abs(expectCol - j);
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int expect = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++, expect++) {
                if (expect == n * n) {
                    break;
                }
                if (tiles[i][j] != expect) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.n != this.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.tiles[i][j] != tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> nei = new ArrayList<>();
        // step 1 find 0
        int i = 0;
        for (; i < n * n; i++) {
            if (tiles[i / n][i % n] == 0)
                break;
        }
        // step 2 find neighbors
        int y = i / n, x = i % n;
        int[][] dirs = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
        for (int[] dir : dirs) {
            int ny = y + dir[0], nx = x + dir[1];
            if (ny < 0 || nx < 0 || ny == n || nx == n) continue;
            swap(y, x, ny, nx);
            nei.add(new Board(tiles));
            swap(y, x, ny, nx);
        }
        return Collections.unmodifiableList(nei);
    }

    private void swap(int i, int j, int ny, int nx) {
        int tmp = tiles[i][j];
        tiles[i][j] = tiles[ny][nx];
        tiles[ny][nx] = tmp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board ret;
        int y = (tiles[0][0] == 0 || tiles[0][1] == 0) ? 1 : 0;
        swap(y, 0, y, 1);
        ret = new Board(tiles);
        swap(y, 0, y, 1);
        return ret;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] t = {{1, 2}, {3, 0}};
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
        int[][] t2 = {{0, 2}, {1, 3}};
        Board b2 = new Board(t2);
        System.out.println(b2.twin());
        System.out.println(b2.twin().equals(b2));
    }

}