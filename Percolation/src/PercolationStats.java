import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private Percolation[] perc;
	private double sitefraction[];
	
	public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
		if (n<=0 || trials <=0){
			throw new java.lang.IllegalArgumentException();
		}
		perc=new Percolation[trials];
		sitefraction=new double[trials];		
		for(int i = 0; i < trials/*perc.length*/; i++){
			perc[i] = new Percolation(n);
		}
	}
		
	public double mean(){ // sample mean of percolation threshold
		return StdStats.mean(sitefraction);
	}
	public double stddev(){  // sample standard deviation of percolation threshold
		return StdStats.stddev(sitefraction);
	}
	public double confidenceLo(){   // low  endpoint of 95% confidence interval
		
	}
	public double confidenceHi(){  	// high endpoint of 95% confidence interval
		
	}
	   
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdRandom.uniform(a, b)
	}

}
