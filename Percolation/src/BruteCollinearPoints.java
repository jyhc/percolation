import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints{
    
    private int segcount=1;
    private int size;    
    private Comparator<Point> comparator;
    private Point[] eqslope = new Point[4]; //points ordered from smallest to largest
    //private Point temp;
    private LineSegment[] segs;
    private int segsindex = 0;
    
    public BruteCollinearPoints(Point[] points){    // finds all line segments containing 4 points
        if (points == null) throw new java.lang.NullPointerException();
        size = points.length;
        for (int i = 0; i < size; i++){
            if (points[i] == null){
                throw new java.lang.NullPointerException();
            }            
        }
        for (int i = 0; i < size; i++){
            for (int j = i+1; j < size; j++){
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
            }
        }
        segs = new LineSegment[size*size];
        for (int i = 0; i < size; i++){
            for (int j = i+1; j < size; j++){
                for (int k = j+1; k < size; k++){
                    for (int l = k+1; l < size; l++){
                        comparator = points[i].slopeOrder();
                        if (comparator.compare(points[j], points[k]) == 0) segcount++;
                        if (comparator.compare(points[j], points[l]) == 0) segcount++;
                        if (segcount == 3){//store 4 points to eqslope
                            eqslope[0] = points[i];
                            eqslope[1] = points[j];
                            eqslope[2] = points[k];
                            eqslope[3] = points[l];
/*                            for (int c = 0; c < 4; c++){//insertion sort for 4 elements
                                for (int d = c; d > 0; d--){
                                    if (eqslope[d].compareTo(eqslope[d-1]) == -1){//exchange
                                        temp = eqslope[d];
                                        eqslope[d] = eqslope[d-1];
                                        eqslope[d-1] = temp;
                                    }
                                    else break;
                                }                                
                            }*/
                            Arrays.sort(eqslope, 0, 4);//replaced above with array sort
                            segs[segsindex++] = new LineSegment(eqslope[0],eqslope[3]);
                        }
                        segcount = 1; //reset counter                                                
                    }
                }                    
            }
        }            
    }
    
    public int numberOfSegments(){        // the number of line segments
        return segsindex;
    }
    public LineSegment[] segments(){                // the line segments
        int count = 0;
        for (LineSegment seg : segs){
            if (seg == null) break;
            count++;
        }
        LineSegment[] newseg = new LineSegment[count];
        for (int i = 0; i < count; i++){
            newseg[i] = segs[i];
            segs[i] = null;
        }
        return newseg;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

