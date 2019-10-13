

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Georman Calderon
 * Date: 13.10.2019
 *
 * Models a percolation system using an n-by-n grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be connected to an open site
 * in the top row via a chain of neighboring (left, right, up, down) open sites.
 * The system percolates if there is a full site in the bottom row.
 */

public class Percolation {


   private final int len;
   private final int top = 0; // virtual top
   private final int bottom; // virtual bottom
   private boolean [][] sites;
   private int openSitesCounter;

   private final WeightedQuickUnionUF uf;

    /**
     * Class constructor creates n-by-n grid, with all sites blocked
     * @param n size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.len = n;
        this.sites = new boolean[n][n];
        bottom = n * n + 1;
        uf = new  WeightedQuickUnionUF(n * n + 2);

    }



    /** Opens site (row, col) if it is not open already
     *  @param  row  index of row in percolation system
     *  @param  col  index of column in percolation system
     */
    public void open(int row, int col){

        if(this.isOpen(row,col)) return;

        sites[row-1][col-1] = true;
        openSitesCounter++;
        int siteOpenedIndex=getSiteIndex(row, col);

        if (row == 1) { // union virtual top
            uf.union(siteOpenedIndex, top);
        }
        if (row == len) { // union virtual bottom
            uf.union(siteOpenedIndex, bottom);
        }

        // union possible neighbor(s)
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(siteOpenedIndex, getSiteIndex(row - 1, col));
        }
        if (row < len && isOpen(row + 1, col)) {
            uf.union(siteOpenedIndex, getSiteIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(siteOpenedIndex, getSiteIndex(row, col - 1));
        }
        if (col < len && isOpen(row, col + 1)) {
            uf.union(siteOpenedIndex, getSiteIndex(row, col + 1));
        }

        

    }


    /**
     * Maps site from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
     * @param row row
     * @param col column
     * @return index of site in 1-dimensional union find object
     */
    private int getSiteIndex(int row, int col) {
        return len * (row - 1) + col;
    }

    /**
     * Is site (row, col) open?
     * @param row index of row in percolation system
     * @param col index of column in percolation system
     * @return result if site is already open
     */
    public boolean isOpen(int row, int col){
      return sites[row-1][col-1]  ;

    }

    /**
     * Is site (row, col) full?
     * @param row index of row in percolation system
     * @param col index of column in percolation system
     * @return result if an open site can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down) open sites
     */
    public boolean isFull(int row, int col){
        return uf.connected(getSiteIndex(row, col), top);
    }

    /**
     * Number of open sites
     * @return number of open sites
     */
    public int numberOfOpenSites(){

     return openSitesCounter;
    }

    /**
     * Does the system percolate?
     * @return result if the system percolates
     */
    public boolean percolates(){
        return uf.connected(top,bottom);
    }

    // test client (optional)
    public static void main(String[] args){

    }
}
