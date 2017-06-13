import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Percolation stats, executed with two arguments: first n and secont T
 * @author CesarF
 * 5/19/2017
 *
 */
public class PercolationStats {

    private int trials;
    private double[] results;

    public PercolationStats(int n, int pTrials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || pTrials <= 0)
            throw new IllegalArgumentException();
        trials = pTrials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            boolean notYet = true;
            while (notYet) {
                p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
                if (p.percolates()) {
                    notYet = false;
                    results[i] = ((double) p.numberOfOpenSites() / (n * n));
                }
            }
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        if (results.length == 1) return Double.NaN;
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); 
        int t = Integer.parseInt(args[1]); 
        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo()
                + ", " + stats.confidenceHi() + "]");
    }
}
