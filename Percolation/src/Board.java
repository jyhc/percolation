import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

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
        int row = blocks.length;        
        copy = new int[row][];
        twinCopy = new int[row][];
        for (int i = 0; i < row; i++) {// (where blocks[i][j] = block in row i, column j)
            int col = blocks[i].length;
            copy[i] = new int[col];
            twinCopy[i] = new int[col];
            for (int j = 0; j < col; j++) {
                copy[i][j] = blocks[i][j];
                twinCopy[i][j] = blocks[i][j];
                if (copy[i][j] != 0) {
                    int shouldBe = (1 + i) * row - (col - (j + 1)); //convert from row,col to value
                    if (copy[i][j] != shouldBe) {
                        int iCorrect = copy[i][j] % col - 1;
                        int jCorrect = copy[i][j] / row - 1;
                        manhattan += Math.abs(i - iCorrect) + Math.abs(j - jCorrect);
                        hamming++;
                    }
                } else {    //keep track of blank tile for finding neighbor
                    blanki = i;
                    blankj = j;
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
            
        }
        if (blankj -1 >= 0) { //has left neighbor
            
        }
        if (blanki + 1 < row) { //has right neighbor
            
        }
        if (blanki + 1 < col) {   //has down neighbor
            
        }
    }
       
    public String toString() {               // string representation of this board (in the output format specified below)
        
    }

    public static void main(String[] args) {// unit tests (not graded)
        
    }
}