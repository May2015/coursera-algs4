/*----------------------------------------------------------------
 *  Author:        Liv.Cao
 *  Written:       2/7/1997
 *  Last updated:  2/10/2006
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats N T
 *  
 *  Prints three values: mean stddev and 95% confidence interval
 *  
 *  This program takes the grid size N and experiment number T as a command-line argument.
 *  Once the system percolation, record percolation threshold.       
 *  After repeating T experiments,estimate the mean, stddev, 95% confidence interval values.
 *----------------------------------------------------------------*/

package percolation;

import edu.princeton.cs.introcs.*;

public class PercolationStats {
    private int sites;
    private int size;
    private double[] prob; // record percolation threshold
    private int noOfTest;
    private Percolation perc;

    public PercolationStats(int N, int T) // perform T independent experiments
                                          // on an N-by-N grid
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("wrong arguments!");
        sites = N * N;
        size = N;
        noOfTest = T;
        prob = new double[T];
        
        //record the result of T times
        for (int i = 0; i < noOfTest; i++) {
            // get sum of open sites when the system percolates
            int randRow = 0;
            int randColumn = 0;
            int opened = 0;
            perc = new Percolation(size); 
    
            while (!perc.percolates()) 
            {
                do {
                    randRow = StdRandom.uniform(1, size + 1);
                    randColumn = StdRandom.uniform(1, size + 1);
                } while (perc.isOpen(randRow, randColumn));
    
                perc.open(randRow, randColumn);
                opened++;
            }
    
            perc = null; //release resource
            prob[i] = ((double) opened) / sites;
        }
    }

    public double mean() // sample mean of percolation threshold
    {
        double mean = 0;
        for (int i = 0; i < noOfTest; i++) {
            mean += prob[i];
        }
        return mean / noOfTest;
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        double mean = mean();
        double stddev = 0;
        
        if (noOfTest == 1) return Math.abs(prob[noOfTest - 1] - mean);
        
        for (int i = 0; i < noOfTest; i++) {
            double temp = (prob[i] - mean);
            stddev += temp * temp;
        }
        return Math.sqrt(stddev / (noOfTest - 1));
    }

    public double confidenceLo() // low endpoint of 95% confidence interval
    {
        double mean = mean();
        return (mean - confidenceHelper());
    }

    public double confidenceHi() // high endpoint of 95% confidence interval
    {
        double mean = mean();
        return (mean + confidenceHelper());
    }

    private double confidenceHelper()
    {
        double stddev = stddev();
        double temp = 1.96 * stddev / (Math.sqrt(noOfTest));
        return temp;
    }

    public static void main(String[] args) 
    {
        int N = 0, T = 0;
        if (args.length == 2) 
        {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStatsObj = new PercolationStats(N, T);

        System.out.printf("mean                     = %.6f\n", 
                percolationStatsObj.mean());
        System.out.printf("stddev                   = %.6f\n", 
                percolationStatsObj.stddev());
        System.out.printf("95%% confidence interval = %.6f, %.6f\n", 
                percolationStatsObj.confidenceLo(),
                percolationStatsObj.confidenceHi());

    }

}
