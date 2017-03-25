import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF model;
    private int index;
    private int dimension;
    private boolean bopen[];
    private int opensite=0;
    
    public Percolation(int n) {// create n-by-n grid, with all sites blocked
        if (n <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        dimension = n;
        model = new WeightedQuickUnionUF(n*n+2);
        bopen = new boolean[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            bopen[i] = false;
        }
        
        bopen[n*n+1]=true;
        bopen[n*n]=true;       
        
    }
    
    public void open(int row, int col){    // open site (row, col) if it is not open already
       
        if (row < 1 || row > dimension || col < 1 || col > dimension ){
            throw new java.lang.IndexOutOfBoundsException();            
        }
        
        if (isOpen(row, col) == true){
            return;
        }
        else {            
            
            if (col != 1 && isOpen(row, col-1)){//left side
                model.union(conv_index(row, col), conv_index(row, col-1));                
            }
            
            if (col != dimension && isOpen(row, col+1)){//right side
                model.union(conv_index(row, col), conv_index(row, col+1));
            }
            
            if (row != 1 && isOpen(row-1, col)){//top side
                model.union(conv_index(row, col), conv_index(row-1, col));
            }
            
            if (row != dimension && isOpen(row+1, col)){//bottom side
                model.union(conv_index(row, col), conv_index(row+1, col));
            }
          //connect all top and bottom row to virtual site (point n*n-top site,n*n+1-bottom site)
            if (row == 1){//top row connect to virtual site
            	model.union(conv_index(row, col), dimension*dimension);
            }
            if (row == dimension){//bottom row connect to virtual site
            	model.union(conv_index(row, col), dimension*dimension+1);
            }
            bopen[conv_index(row, col)]=true;
            opensite++;           
        }              
    }
    
    public boolean isOpen(int row, int col){  // is site (row, col) open?
        return bopen[conv_index(row, col)];
    }
    
    public boolean isFull(int row, int col){  // is site (row, col) full?
        return model.connected(conv_index(row, col),dimension*dimension);
    }
    
    public int numberOfOpenSites(){       // number of open sites
        return opensite;
    }
    
    public boolean percolates(){              // does the system percolate?
        return model.connected(dimension*dimension,dimension*dimension+1);
    }

    /*public static void main(String[] args){   // test client (optional)
        
    }*/
    
    private int conv_index(int row, int col){//convert row and col to index
        index = row*dimension-dimension+col-1;
        return index;
    }
}
