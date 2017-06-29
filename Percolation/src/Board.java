import java.util.Arrays;

public class Board {
    
    private final int[][] copy;
    private int row;
    private int col;
    private int manhattan = 0; //caching calculation from constructor
    private int hamming = 0;
    
    public Board(int[][] blocks) {          // construct a board from an n-by-n array of blocks
        int row = blocks.length;        
        copy = new int[row][];
        for (int i = 0; i < row; i++) {// (where blocks[i][j] = block in row i, column j)
            int col = blocks[i].length;
            copy[i] = new int[col];            
            for (int j = 0; j < col; j++) {
                copy[i][j] = blocks[i][j];
                if (copy[i][j] != 0) {
                    int shouldBe = (1 + i) * row - (col - (j + 1)); //convert from row,col to value
                    if (copy[i][j] != shouldBe) {
                        int iCorrect = copy[i][j] % col - 1;
                        int jCorrect = copy[i][j] / row - 1;
                        manhattan += Math.abs(i - iCorrect) + Math.abs(j - jCorrect);
                        hamming++;
                    }
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
        int temp = -1;
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
                if (isValid == true) {
                    copy[i][j] = copy[i][j-1];
                    copy[i][j-1] = temp; 
                    return new Board(copy);
                }
                isValid = true;
                temp = copy[i][j];
            }
            
        }
        throw new java.util.NoSuchElementException();
    }
    
    public boolean equals(Object y) {        // does this board equal y?
        
    }
    
    public int hashCode() {            //override by convention when overriding equals()
        int hash = 1;
        hash = 31*hash + Arrays.deepHashCode(copy);
        hash = 31*hash + manhattan;
        return hash;
    }
    
    public Iterable<Board> neighbors() {     // all neighboring boards
        
    }
    public String toString() {               // string representation of this board (in the output format specified below)
        
    }

    public static void main(String[] args) {// unit tests (not graded)
        
    }
}