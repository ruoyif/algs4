import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    private SearchNode current;
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        SearchNode tile = new SearchNode(initial, null);
        SearchNode twin = new SearchNode(initial.twin(), null);

        MinPQ<SearchNode> tilesPQ = new MinPQ<>();
        MinPQ<SearchNode> twinsPQ = new MinPQ<>();
        current = tile;
        SearchNode twinCurrent = twin;

        tilesPQ.insert(current);
        twinsPQ.insert(twinCurrent);
        while (true) {
            current = tilesPQ.delMin();
            if (current.board.isGoal())
                break;
            insert(current, tilesPQ);
            twinCurrent = twinsPQ.delMin();
            if (twinCurrent.board.isGoal())
                break;
            insert(twinCurrent, twinsPQ);
        }


    }

    private void insert(SearchNode cur, MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = cur.board.neighbors();
        for (Board b : neighbors) {
            if (cur.parent == null || !b.equals(cur.parent.board)) {
                pq.insert(new SearchNode(b, cur));
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        public int move;
        public int priority;
        public Board board;
        public SearchNode parent;

        public SearchNode(Board board, SearchNode parent) {
            this.board = board;
            this.parent = parent;
            if (parent == null) {
                this.move = 0;
            }
            else
                this.move = parent.move + 1;
            this.priority = this.move + board.manhattan();
        }
        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return current.board.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable())
            return current.move;
        else
            return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (current.board.isGoal()) {
            Stack<Board> s = new Stack<>();
            SearchNode cur = current;
            while (cur != null) {
                s.push(cur.board);
                cur = cur.parent;
            }
            return s;

        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}