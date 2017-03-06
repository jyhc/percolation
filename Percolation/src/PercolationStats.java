import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private Percolation[] perc;
	private double sitefraction[];
	private double times;
	
	public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
		if (n<=0 || trials <=0){
			throw new java.lang.IllegalArgumentException();
		}
		perc=new Percolation[trials];
		sitefraction=new double[trials];		
		for(int i = 0; i < trials/*perc.length*/; i++){
			perc[i] = new Percolation(n);
		}
		times=trials;
	}
		
	public double mean(){ // sample mean of percolation threshold
		return StdStats.mean(sitefraction);
	}
	public double stddev(){  // sample standard deviation of percolation threshold
		return StdStats.stddev(sitefraction);
	}
	public double confidenceLo(){   // low  endpoint of 95% confidence interval
		return mean()-1.96*stddev()/Math.sqrt(times);
	}
	public double confidenceHi(){  	// high endpoint of 95% confidence interval
	    return mean()+1.96*stddev()/Math.sqrt(times);
	}
	   
	public static void main(String[] args) {	  
	    int n=Integer.parseInt(args[0]);
	    int trials=Integer.parseInt(args[1]);
	    PercolationStats stats= new PercolationStats(n,trials);
	    
        for(int j = 0; j < trials/*perc.length*/; j++){            
            while(stats.perc[j].percolates()!=true){
                int row=StdRandom.uniform(1, n+1);
                int col=StdRandom.uniform(1, n+1);
                if(stats.perc[j].isOpen(row, col)==false){
                    stats.perc[j].open(row, col);                    
                }
                
            }
            stats.sitefraction[j]=(double)stats.perc[j].numberOfOpenSites()/(n*n);
            
        }     
        System.out.print("mean\t"+stats.mean()+"\nstddev\t"+stats.stddev());
        System.out.print("\n95% confidence interval\t["+stats.confidenceLo()+", "+stats.confidenceHi()+"]\n");
	}

}
