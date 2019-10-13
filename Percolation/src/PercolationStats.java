import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * @author Georman Calderon
 * Date: 10.8.2018
 *
 * Performs a series of computational experiment for percolation system.
 */
public class PercolationStats {
    private final int numOfExperiments; // number of experiment carried outs
    private final double[] fractions; // an array storing fraction of each percolation
    private final static double CONFIDENCE_95=1.96;

    /**
     * Class constructor performs trials independent experiments on an n-by-n grid
     * @param n percolation system grid size
     * @param trials number of trials that were performed
     */
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N <= 0 or T <= 0");
        }
        numOfExperiments = trials;
        fractions = new double[numOfExperiments];
        for (int expNum = 0; expNum < numOfExperiments; expNum++) {
            Percolation pr = new Percolation(n);
            int openedSite = 0;
            while (!pr.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!pr.isOpen(row, col)) {
                    pr.open(row, col);
                    openedSite++;
                }
            }
            double fraction = (double) openedSite / (n * n);
            fractions[expNum] = fraction;
        }

    }

    /**
     * Sample mean of percolation threshold
     * @return sample mean
     */
    public double mean(){
        return StdStats.mean(fractions);
    }


    /**
     * Sample standard deviation of percolation threshold
     * @return sample standard
     */
    public double stddev(){
        return StdStats.stddev(fractions);
    }

    /**
     * Low endpoint of 95% confidence interval
     * @return low endpoint
     */
    public double confidenceLo(){
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(numOfExperiments));
    }

    /**
     * High endpoint of 95% confidence interval
     * @return high endpoint
     */
    public double confidenceHi(){
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(numOfExperiments));
    }

    /**
     * @param args args[0] - size of n-by-n grid, args[1] - number of trials
     */
    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();
        PercolationStats pStats = new PercolationStats(N, T);

        String confidence = pStats.confidenceLo() + ", "
                + pStats.confidenceHi();
        StdOut.println("mean                    = " + pStats.mean());
        StdOut.println("stddev                  = " + pStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
        System.out.println("------");
        System.out.printf("Total time: %f secs. (for N=%d, T=%d)",
                sw.elapsedTime(), N, T);



    }
}
