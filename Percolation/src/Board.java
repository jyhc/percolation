import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

import edu.princeton.cs.algs4.In;

public class Board {
    
    private final int[][] copy;
    private final int[][] twinCopy;
    private int row;
    private int col;
    private int manhattan = 0; //caching calculation from constructor
    private int hamming = 0;
    private int blanki = 0;
    private int blankj = 0;
    
    public Board(int[][] blocks) {          // construct a board from an n-by-n array of blocks
        row = blocks.length;        
        copy = new int[row][];
        twinCopy = new int[row][];
        for (int i = 0; i < row; i++) {// (where blocks[i][j] = block in row i, column j)
            col = blocks[i].length;
            copy[i] = new int[col];
            twinCopy[i] = new int[col];
            for (int j = 0; j < col; j++) {
                copy[i][j] = blocks[i][j];
                twinCopy[i][j] = blocks[i][j];
                if (copy[i][j] != 0) {
                    int shouldBe = (1 + i) * row - (col - (j + 1)); //convert from row,col to value
                    if (shouldBe == row*col) shouldBe = 0;
                    if (copy[i][j] != shouldBe) {
                        int iCorrect;
                        int jCorrect;
                        if (copy[i][j] %  col == 0) {
                            jCorrect = col - 1;
                            iCorrect = copy[i][j] / col - 1;                            
                        } else {
                            if (copy[i][j] < col) {
                                iCorrect = 0;
                                jCorrect = copy[i][j] - 1;
                            } else {
                                iCorrect = copy[i][j] / col;
                                jCorrect = copy[i][j] % row - 1;
                            }
                        }
                        manhattan += Math.abs(i - iCorrect) + Math.abs(j - jCorrect);
                        hamming++;
                    }
                } else {
                    blanki = i;
                    blankj = j;   //keep track of blank tile for finding neighbor
                }
            }
        }        
    }

    
    public int dimension() {                 // board dimension n
        return row;
    }
    
    public int hamming() {                   // number of blocks out of place
        return hamming;
    }
    
    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        return manhattan;
    }
    
    public boolean isGoal() {                // is this board the goal board?
        if (hamming == 0 && manhattan == 0) return true;
        else return false;
    }
    
    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
        boolean isValid = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (copy[i][j] == 0) {
                    isValid = false;
                    break;
                }
                if (j == col-1) {
                    isValid = false;
                    continue;
                }               
                if (isValid == true) {  //should occur only once
                    twinCopy[i][j] = copy[i][j - 1];
                    twinCopy[i][j - 1] = copy[i][j];
                    Board temp = new Board(twinCopy);
                    twinCopy[i][j] = copy[i][j];    //reset twinCopy
                    twinCopy[i][j - 1] = copy[i][j - 1];
                    return temp;
                }
                isValid = true;
            }
            
        }
        throw new java.util.NoSuchElementException();
    }
    
    public boolean equals(Object y) {        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.copy, that.copy);
    }
    
    public int hashCode() {            //override by convention when overriding equals()
        int hash = 1;
        hash = 31*hash + Arrays.deepHashCode(copy);
        hash = 31*hash + manhattan;
        return hash;
    }
    
    public Iterable<Board> neighbors() {     // all neighboring boards
        Stack<Board> stackNeighbor = new Stack<Board>();
        if (blanki - 1 >= 0) { // has top neighbor
            twinCopy[blanki][blankj] = copy[blanki - 1][blankj];
            twinCopy[blanki - 1][blankj] = copy[blanki][blankj];
            stackNeighbor.push((new Board(twinCopy)));
            twinCopy[blanki][blankj] = copy[blanki][blankj];    //reset twinCopy
            twinCopy[blanki - 1][blankj] = copy[blanki - 1][blankj];
        }
        if (blankj -1 >= 0) { //has left neighbor
            twinCopy[blanki][blankj] = copy[blanki][blankj - 1];
            twinCopy[blanki][blankj - 1] = copy[blanki][blankj];
            stackNeighbor.push((new Board(twinCopy)));
            twinCopy[blanki][blankj] = copy[blanki][blankj];    //reset twinCopy
            twinCopy[blanki][blankj - 1] = copy[blanki][blankj - 1];
        }
        if (blanki + 1 < row) { //has down neighbor
            twinCopy[blanki][blankj] = copy[blanki + 1][blankj];
            twinCopy[blanki + 1][blankj] = copy[blanki][blankj];
            stackNeighbor.push((new Board(twinCopy)));
            twinCopy[blanki][blankj] = copy[blanki][blankj];    //reset twinCopy
            twinCopy[blanki + 1][blankj] = copy[blanki + 1][blankj];
        }
        if (blankj + 1 < col) {   //has right neighbor
            twinCopy[blanki][blankj] = copy[blanki][blankj + 1];
            twinCopy[blanki][blankj + 1] = copy[blanki][blankj];
            stackNeighbor.push((new Board(twinCopy)));
            twinCopy[blanki][blankj] = copy[blanki][blankj];    //reset twinCopy
            twinCopy[blanki][blankj + 1] = copy[blanki][blankj + 1];
        }
        return stackNeighbor;
    }
       
    public String toString() {               // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(row + "\n");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                s.append(String.format("%d ", copy[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {// unit tests (not graded)
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.print(initial.toString());
        Board twin = initial.twin();
        System.out.print(twin.toString());
        System.out.print(initial.hamming() + "\n");
        System.out.print(initial.manhattan());
    }
}