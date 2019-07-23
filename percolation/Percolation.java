import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
public class Percolation {
    private final int n;
    private boolean[] status;
    private final WeightedQuickUnionUF union;
    private final WeightedQuickUnionUF grid;
    private final int size;
    private int count;

    public Percolation(int n) {
        this.count = 0;
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        size = n*n;
        union = new WeightedQuickUnionUF(size+2);
        grid = new WeightedQuickUnionUF(size+1);
        status = new boolean[size];
        for (int i = 0; i < size; i++) {
                status[i] = false;
        }
    }
    private int postion(int row, int col) {
        return this.n*(row-1)+col;
    }
    private boolean validArg(int i) {
        if (i <= 0 || i > n)
            return false;
        return true;
    }
    public void open(int row, int col) {
        if (!validArg(row) || !validArg(col))
            throw new IllegalArgumentException();
        int pos = postion(row, col);
        if (!isOpen(row, col))
            count++;
        status[pos-1] = true;
        if (row-1 > 0 && isOpen(row-1, col)) {
            int temp = postion(row-1, col);
            grid.union(temp, pos);
            union.union(temp, pos);
        }
        if (col-1 > 0 && isOpen(row, col-1)) {
            int temp = postion(row, col-1);
            grid.union(temp, pos);
            union.union(temp, pos);
        }
        if (row+1 <= n && isOpen(row+1, col)) {
            int temp = postion(row+1, col);
            grid.union(temp, pos);
            union.union(temp, pos);

        }
        if (col+1 <= n && isOpen(row, col+1)) {
            int temp = postion(row, col+1);
            union.union(temp, pos);
            grid.union(temp, pos);

        }
        if (row == 1) {
            union.union(0, pos);
            grid.union(0, pos);

        }
        if (row == n) {
            union.union(size+1, pos);
        }


    }
    public boolean isOpen(int row, int col) {
        if (!validArg(row) ||  !validArg(col))
            throw new IllegalArgumentException();
        return status[postion(row, col)-1];

    }
    public boolean isFull(int row, int col) {
        if (!validArg(row) || !validArg(col))
            throw new IllegalArgumentException();
        int pos = postion(row,  col);
        return grid.connected(0, pos) && union.connected(0, pos);


    }
   public int numberOfOpenSites() {
        return this.count;

   }
    public boolean percolates() {
        return union.connected(0, size+1);
    }

}
