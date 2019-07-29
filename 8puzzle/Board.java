import java.util.ArrayList;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int n;
    private final int[][] blocks;

    public Board(int[][] tiles) {
        this.n = tiles.length;
        blocks = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                blocks[row][col] = tiles[row][col];
            }
        }

    }
    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sb.append(String.format("%6d", blocks[row][col]));
            }
            sb.append("\n");
        }
        return sb.toString();

    }
    // board dimension n
    public int dimension() {
        return this.n;
    }
    private int getRow(int value) {
        return (value - 1) / n;

    }
    private int getCol(int value) {
        return (value - 1) % n;
    }


    // number of tiles out of place
    public int hamming() {
        int hd = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == n-1 && col == n-1) {
                    continue;
                }
                else {
                    if (blocks[row][col] != n * row + col + 1) {
                        hd++;
                    }
                }

            }
        }
        return hd;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int md = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int value = blocks[row][col];
                if (value == 0)
                    continue;
                else
                    md += Math.abs(getRow(value) - row) + Math.abs(getCol(value) - col);
            }
        }
        return md;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == n-1 && col == n-1) {
                    continue;
                }
                else {
                    if (blocks[row][col] != n * row + col + 1) {
                        return false;
                    }
                }

            }
        }
        return true;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y.getClass().isInstance(this)) {
            Board that = (Board) y;
            if (that.n != this.n)
                return false;
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (blocks[row][col] != that.blocks[row][col]) {
                        return false;
                    }
                }

            }
            return true;
        }
        return false;

    }

    // all neighboring boards
    private void exch(int row, int col, int row1, int col1) {
        int temp = blocks[row][col];
        blocks[row][col] = blocks[row1][col1];
        blocks[row1][col1] = temp;

    }
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] == 0) {
                    if (row -1 >= 0) {
                        Board neighborU = new Board(blocks);
                        neighborU.exch(row, col, row-1, col);
                        neighbors.add(neighborU);
                    }
                    if (col -1 >= 0) {
                        Board neighborL = new Board(blocks);
                        neighborL.exch(row, col, row, col-1);
                        neighbors.add(neighborL);
                    }
                    if (row +1 < n) {
                        Board neighborD = new Board(blocks);
                        neighborD.exch(row, col, row+1, col);
                        neighbors.add(neighborD);
                    }
                    if (col +1 < n) {
                        Board neighborR = new Board(blocks);
                        neighborR.exch(row, col, row, col+1);
                        neighbors.add(neighborR);
                    }
                }
            }
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(this.blocks);
        int initialRow = 0;
        int initialCol = 0;
        while (blocks[initialRow][initialCol] == 0)
            initialRow++;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] != blocks[initialRow][initialCol] && blocks[row][col] != 0) {
                    twin.exch(row, col, initialRow, initialCol);
                    return twin;
                }

            }
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
//        int[][] tiles= {{132, 31231, 43123,4131}, {1, 10, 6, 8}, {5, 9, 7, 12}, {13, 14, 15, 11}};
//        Board initial = new Board(tiles);
//        System.out.print(initial.toString());
    }


}
