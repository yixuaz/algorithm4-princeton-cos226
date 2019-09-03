package part1.week1.project;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * ###Bonus
 *
 * this project have a bonus, if u can only use 1 union find to solve this problem correctly.
 * so the question become how to use 1 union find to solve the backwash problem.
 * the hint is take advantage of find and another state.
 * as previous design, we only use 0 record block site, 1 record open site.
 *
 * because two point will cause backwash, one point is hard to know if it is percolate.
 * so we need to find a solution how to break one of them.
 *
 * use second is easier. because backwash is solved. and another is how to know it is percolate without virtual bottom line.
 * we need to know is there a cell connect any point in bottom line.
 * so when open a block site, we set 1 value, if it bottom line we set 2 value on it.
 *
 * now the definition of open still is value > 0.
 * and we need to check percolate only need check the root status is 2 or not.
 * so what we need do is when union, if there is a set have 2 parent, we set new parent with 2.
 */
public class Percolation {
    private final byte[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private int openCnt;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should large than 0");
        this.n = n;
        grid = new byte[n + 1][n];
        uf = new WeightedQuickUnionUF(n * n + 1);
        openCnt = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        openCnt++;
        grid[--row][--col] = 1;
        if (row == 0) uf.union(row * n + col, n * n);
        if (row == n - 1) grid[row][col] = 2;
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] dir : dirs) {
            int nrow = row + dir[0], ncol = col + dir[1];
            if (ncol < 0 || nrow < 0 || ncol == n
                    || nrow == n || !isOpen(nrow + 1, ncol + 1)) continue;
            int roota = uf.find(row * n + col), rootb = uf.find(nrow * n + ncol);
            uf.union(roota, rootb);
            if (cellValue(roota) == 2 || cellValue(rootb) == 2) {
                int newRoot = uf.find(roota);
                grid[newRoot / n][newRoot % n] = 2;
            }
        }
    }

    private byte cellValue(int root) {
        return grid[root / n][root % n];
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        validate(row, col);
        return grid[row][col] > 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.connected(n * n, (row - 1) * n + col - 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCnt;
    }

    // does the system percolate?
    public boolean percolates() {
        return cellValue(uf.find(n * n)) == 2;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n)
            throw new IllegalArgumentException("invalid range for col and row " + col + "," + row);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(2, 1);
        System.out.println(percolation.percolates());
    }
}
