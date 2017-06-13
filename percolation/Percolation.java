import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation data structure. executed to test percolation algorithm
 * 
 * @author CesarF 5/19/2017
 *
 */
public class Percolation {

    private int gridL;
    private int openCells;
    private WeightedQuickUnionUF tree;
    private int[] positions;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException("n is not a valid  value");
        gridL = n;
        tree = new WeightedQuickUnionUF(n * n + 2);
        positions = new int[n * n + 2];
        positions[0] = 1;
        positions[positions.length - 1] = 1;
        openCells = 0;

    }

    public void open(int row, int col) {
        validate(row, col);
        // open site (row, col) if it is not open already
        int pos = xyTo1D(row, col);
        if (positions[pos] == 0) {
            positions[pos] = 1;
            openCells++;
            int up = xyTo1D(row - 1, col);
            if (positions[up] == 1)
                tree.union(pos, up);
            int down = xyTo1D(row + 1, col);
            if (positions[down] == 1)
                tree.union(pos, down);
            int left = xyTo1D(row, col - 1);
            if (col > 1 && positions[left] == 1)
                tree.union(pos, left);
            int right = xyTo1D(row, col + 1);
            if (col < gridL && positions[right] == 1)
                tree.union(pos, right);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        // is site (row, col) open?
        int pos = xyTo1D(row, col);
        return positions[pos] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        // is site (row, col) full?
        if (isOpen(row, col)) {
            int pos = xyTo1D(row, col);
            if (tree.connected(pos, 0))
                return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openCells;
    }

    public boolean percolates() {
        // does the system percolate?
        return tree.connected(gridL * gridL + 1, 0);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(6);

        System.out.println(p.percolates());
    }

    private void validate(int row, int col) {
        int pos = xyTo1D(row, col);
        if (pos <= 0 || pos > gridL * gridL)
            throw new IndexOutOfBoundsException();
    }

    private int xyTo1D(int row, int col) {
        int res = ((row - 1) * gridL) + (col);
        
        if (res < 0)
            return 0;
        if (res > gridL * gridL)
            return gridL * gridL + 1;
        return res;
    }

}
