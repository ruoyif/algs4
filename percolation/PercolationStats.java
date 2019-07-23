/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private final int trials;
    private final double mean;
    private final double stddev;
    public PercolationStats(int n, int trials) {

        double[] possiblity;
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Illegal argument");
        this.trials = trials;
        possiblity = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new  Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (p.isOpen(row, col))
                    continue;
                p.open(row, col);
            }
            possiblity[i] = p.numberOfOpenSites() * 1.0 / (n * n);
        }
        this.mean = StdStats.mean(possiblity);
        this.stddev = StdStats.stddev(possiblity);


    }
    public double mean() {
        return mean;

    }

    public double stddev() {
        return stddev;

    }

    private double half() {
        return 1.96*this.stddev/Math.sqrt(trials);
    }

    public double confidenceLo() {
        return this.mean-half();

    }
    public double confidenceHi() {
        return this.mean+half();

    }
    public static void main(String[] args) {
        PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]),
                                                    Integer.parseInt(args[1]));
        System.out.printf("mean                     = %f\n", pls.mean());
        System.out.printf("stddev                   = %f\n", pls.stddev());
        System.out.printf("95%% confidence Interval  = %f, %f\n",
                          pls.confidenceLo(), pls.confidenceHi());


    }
}
