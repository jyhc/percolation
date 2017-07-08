import java.util.Comparator;
import java.util.Stack;

import edu.princeton.cs.algs4.MinPQ;;

public class Solver {
    private Board goal = null;
    private Boolean solvable = null;
        
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        Board search = initial;
        Board searchTwin = search.twin();
        Comparator<Board> comparator = new BoardComparator();
        MinPQ<Board> solverPQ = new MinPQ<Board>(comparator);
        MinPQ<Board> solverTwinPQ = new MinPQ<Board>(comparator);
        while (!search.isGoal() && !searchTwin.isGoal()) {
            for (Board board: search.neighbors()) {
                if (!search.parent.equals(board)) {
                    solverPQ.insert(board);
                }
            }
            search = solverPQ.delMin();
            for (Board board: searchTwin.neighbors()) {
                if (!searchTwin.parent.equals(board)) {
                    solverTwinPQ.insert(board);
                }
            }
            searchTwin = solverTwinPQ.delMin();         
        }
        if (search.isGoal()) {
            goal = search;
            solvable = true;
        }
        if (searchTwin.isGoal()) {
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
            Board temp = goal;
            while (temp.parent != null) {
                solution.push(temp);
                temp = temp.parent;
            }
        }
    }
       
    private class BoardComparator implements Comparator<Board>{       

        public int compare(Board b1, Board b2) {
           if (b1.manhattan() + b1.steps < b2.manhattan() + b2.steps) return -1;
           else if (b1.manhattan() + b1.steps > b2.manhattan() + b2.steps) return +1;
           else if (b1.hamming() + b1.steps > b2.hamming() + b2.steps) return -1;
           else if (b1.hamming() + b1.steps < b2.hamming() + b2.steps) return +1;
           else throw new java.lang.Error();             
        }
    }

    
    public static void main(String[] args) {// solve a slider puzzle (given below)
        
    }
}