package part1.week1.project;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double ONE_POINT_NINE_SIX = 1.96;
    private final double stddev;
    private final double mean;
    private final double sqrtT;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0) throw new IllegalArgumentException("trial should large than 0");
        double[] res = new double[trials];
        int total = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = 1 + StdRandom.uniform(n);
                int col = 1 + StdRandom.uniform(n);
                percolation.open(row, col);
            }
            res[i] = (double) percolation.numberOfOpenSites() / total;
        }
        mean = StdStats.mean(res);
        stddev = StdStats.stddev(res);
        sqrtT = Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (ONE_POINT_NINE_SIX * stddev) / sqrtT;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (ONE_POINT_NINE_SIX * stddev) / sqrtT;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean                    = %f\n", p.mean);
        StdOut.printf("stddev                  = %f\n", p.stddev);
        StdOut.printf("95%% confidence interval = [%f,%f]\n", p.confidenceLo(), p.confidenceHi());
    }
}
