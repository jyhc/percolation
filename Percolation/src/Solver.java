import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;;

public class Solver {
    private TreeNode goal = null;
    private Boolean solvable = null;
    //private Board parent = null;
    //private int steps = 0;
        
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new java.lang.IllegalArgumentException();
        TreeNode search = new TreeNode(initial);
        TreeNode searchTwin = new TreeNode(search.board.twin());
        Comparator<TreeNode> comparator = new BoardComparator();
        MinPQ<TreeNode> solverPQ = new MinPQ<>(comparator);
        MinPQ<TreeNode> solverTwinPQ = new MinPQ<>(comparator);
        while (!search.board.isGoal() && !searchTwin.board.isGoal()) {
            for (Board neighbor: search.board.neighbors()) {
                if (!neighbor.equals(search.parentBoard)) {
                    TreeNode neighborNode = new TreeNode(neighbor);
                    neighborNode.parentNode = search;
                    neighborNode.parentBoard = search.board;
                    neighborNode.steps = search.steps + 1;
                    solverPQ.insert(neighborNode);                    
                }
            }
            search = solverPQ.delMin();
            for (Board neighbor: searchTwin.board.neighbors()) {
                if (!neighbor.equals(searchTwin.parentBoard)) {
                    TreeNode neighborNode = new TreeNode(neighbor);
                    neighborNode.parentNode = searchTwin;
                    neighborNode.parentBoard = searchTwin.board;
                    neighborNode.steps = searchTwin.steps + 1;
                    solverTwinPQ.insert(neighborNode);
                }
            }
            searchTwin = solverTwinPQ.delMin();
        }
        if (search.board.isGoal()) {
            goal = search;
            solvable = true;
        }
        if (searchTwin.board.isGoal()) {
            goal = searchTwin;
            solvable = false;
        }
    }
    
    public boolean isSolvable() {           // is the initial board solvable?
        return solvable;
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!solvable) return -1;
        else return goal.steps;
        
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!solvable) return null;
        else {
            Stack<Board> solution = new Stack<Board>();
            TreeNode temp = goal;
            if (temp.parentBoard == null) {
                solution.push(temp.board);
                return solution;
            }
            while (temp.parentBoard != null) {
                solution.push(temp.board);
                temp = temp.parentNode;
            }
            solution.push(temp.board);
            return solution;
        }
    }
       
    private class BoardComparator implements Comparator<TreeNode>{       

        public int compare(TreeNode b1, TreeNode b2) {
           if (b1.board.manhattan() * 2 + b1.steps < b2.board.manhattan() * 2 + b2.steps) return -1;
           else if (b1.board.manhattan() * 2 + b1.steps > b2.board.manhattan() * 2 + b2.steps) return +1;
           //else if (b1.board.hamming() * 2 + b1.steps > b2.board.hamming() * 2 + b2.steps) return -1;
           //else if (b1.board.hamming() * 2 + b1.steps < b2.board.hamming() * 2 + b2.steps) return +1;
           else if (b1.board == b2.board)throw new java.lang.Error();
           else return 1;
        }
    }

    private class TreeNode {    //wrapper to store information for board
        private TreeNode parentNode = null;
        private Board parentBoard = null;
        private int steps = 0;
        private Board board;
        
        public TreeNode(Board board) {
            this.board = board;
        }
        
    }
    
    public static void main(String[] args) {// solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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