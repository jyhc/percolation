import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private int size;    
    private Comparator<Point> comparator;
    private Point[] subPoints;  
    private Point[] segmentA;
    private Point[] segmentB;
    private LineSegment[] segs;
    private int segsindex = 0;
    private Point[] coPoints;
    private int coIndex;
    private int collinearValue = 3;
    private final LineSegment[] newseg;
    
    
    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null) throw new java.lang.NullPointerException();
        size = points.length;
        for (int i = 0; i < size; i++){
            if (points[i] == null){
                throw new java.lang.NullPointerException();
            }            
        }
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
            }
        }
        
        segmentA = new Point[size*size];
        segmentB = new Point[size*size];
        segs = new LineSegment[size*size];
        for (int i = 0; i < size; i++) {
            subPoints = new Point[size-1];
            comparator = points[i].slopeOrder();
            
            for (int j = 0, index = 0; j < size; j++) {
            	if (j != i) subPoints[index++] = points[j];                
            }
            Arrays.sort(subPoints,comparator);// sort by slope
            for (int x = 0; x < size-1; x++) {
                coPoints = new Point[size];
                coIndex = 0;
                for (int y = x+1; y < size-1; y++) {
                    if (comparator.compare(subPoints[x], subPoints[y]) != 0){
                        x=y-1;
                        break;
                    } 
                    else {
                    	if (x == (y-1)) coPoints[coIndex++] = subPoints[x];
                    	coPoints[coIndex++] = subPoints[y];
                    }                    
                }
                if (coIndex >= collinearValue) {
                    coPoints[coIndex++] = points[i];
                    Arrays.sort(coPoints,0,coIndex);
                    segmentA[segsindex] = coPoints[0]; // keep track of segments by points
                    segmentB[segsindex] = coPoints[coIndex-1];
                    segs[segsindex++] = new LineSegment(coPoints[0],coPoints[coIndex-1]);
                    for (int z = 0 ; z < segsindex-1; z++) { // Point check for duplicate segments
                    	if (segmentA[segsindex-1] == segmentA[z] && segmentB[segsindex-1] == segmentB[z]) {
                    		segsindex--;
                    		break;
                    	}                    	
                    }                    
                }                
            }            
        }

        newseg = new LineSegment[segsindex];
        for (int i = 0; i < segsindex; i++) {
            newseg[i] = segs[i];
            segs[i] = null;
        }
        segs = null;
    }
    
    public int numberOfSegments() {        // the number of line segments
    	int temp = segsindex;
    	return temp;
    }
    
    public LineSegment[] segments() {                // the line segments
        LineSegment[] segCopy = new LineSegment[segsindex];
    	for (int i = 0; i < segsindex; i++) {
        	segCopy[i] = newseg[i];
        }
    	return segCopy;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
