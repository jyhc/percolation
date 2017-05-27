import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private int size;    
    private Comparator<Point> comparator;
    private Point[] sub_points;    
    private LineSegment[] segs;
    private int segsindex = 0;
    private Point[] coPoints;
    private int coIndex;
    private int collinearValue = 3;
    
    
    public FastCollinearPoints(Point[] points){     // finds all line segments containing 4 or more points
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
            sub_points = new Point[size-i-1];
            comparator = points[i].slopeOrder();
            for (int j = i+1; j < size; j++){
                sub_points[i] = points[j];                
            }
            Arrays.sort(sub_points,comparator);//sort by slope
            for (int x = 0; x < sub_points.length; x++){
                coPoints = new Point[size];
                coIndex = 0;
                for (int y = x+1; y < sub_points.length; y++){
                    if (sub_points[x].slopeTo(points[i]) != sub_points[y].slopeTo(points[i])){
                        x=y;
                        break;
                    } 
                    coPoints[coIndex++] = sub_points[y];
                    
                }
                if (coIndex >= collinearValue){
                    coPoints[coIndex++] = points[i];
                    Arrays.sort(coPoints,0,coIndex);
                    segs[segsindex++] = new LineSegment(coPoints[0],coPoints[coIndex-1]);
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
}