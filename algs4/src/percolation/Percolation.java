/*----------------------------------------------------------------
 *  Author:        Liv.Cao
 *  Written:       2/7/1997
 *  Last updated:  2/10/2006
 *  
 *  Compilation:   javac Percolation.java
 *  
 *  We model a percolation system using an N-by-N grid of sites,
 *  and use weighted quick union algorithm to calc percolation threshold.
 *----------------------------------------------------------------*/

package percolation;

import edu.princeton.cs.algs4.*;

public class Percolation
{
    private boolean[][] site;
    private int size;
    private int sumSites;
    private WeightedQuickUnionUF ufHelperA; // hava virtual top and virtual bottom
    private WeightedQuickUnionUF ufHelperB; //only have virtual top to tackle the backwash bug 
    private int virtTop;
    private int virtBottom;

  /* 
   * create N-by-N grid, with all sites blocked
   */
    public Percolation(int N)
    {
        if (N <= 0){
            throw new IllegalArgumentException("N is ilegal!");}

        size = N;
        sumSites = N * N;
        virtBottom = sumSites + 1;
        virtTop = 0;
        site = new boolean[N + 1][N + 1];
        for (int i = 0; i <= N; i++)
            for (int j = 0; j <= N; j++)
                site[i][j] = false; // initiate the state of sites

        ufHelperA = new WeightedQuickUnionUF(sumSites + 2);
        ufHelperB = new WeightedQuickUnionUF(sumSites + 1);
    }

    public void open(int i, int j) // open site (row i, column j) if it is not
    {
        checkIndex(i, j);
        site[i][j] = true;// open site

        if (i == 1)// connect top or bottom row to virtual root
        {
            ufHelperA.union(virtTop, xyTo1D(i, j));
            ufHelperB.union(virtTop, xyTo1D(i, j));
        }
        if (i == size)
            ufHelperA.union(virtBottom, xyTo1D(i, j));

        for (int y = j, x = i - 1; x < i + 2; x += 2)
        {
            if (1 <= x && x <= size && site[x][y])
            {
                ufHelperA.union(xyTo1D(x, y), xyTo1D(i, j));
                ufHelperB.union(xyTo1D(x, y), xyTo1D(i, j));
            }
        }

        for (int x = i, y = j - 1; y < j + 2; y += 2)
        {
            if (1 <= y && y <= size && site[x][y])
            {
                ufHelperA.union(xyTo1D(x, y), xyTo1D(i, j));
                ufHelperB.union(xyTo1D(x, y), xyTo1D(i, j));
            }
        }
    }

    public boolean isOpen(int i, int j) // is site (row i, column j) open?
    {
        checkIndex(i, j);
        return site[i][j];
    }

    public boolean isFull(int i, int j) // is site (row i, column j) full?
    {
        if (!isOpen(i, j))
            return false;
        int ufIndex = xyTo1D(i, j);
        return ufHelperB.connected(virtTop, ufIndex);
    }

    public boolean percolates() // check the system percolate
    {
        return ufHelperA.connected(virtTop, virtBottom);
    }
    
    private int xyTo1D(int x, int y)  // convert grab coordinate to index of union-find method
    {
        return (x - 1) * size + y;

    }

    private void checkIndex(int i, int j) 
    {
        if (i < 1 || i > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > size)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
}
