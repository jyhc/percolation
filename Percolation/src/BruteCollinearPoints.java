import java.util.Comparator;

public class BruteCollinearPoints{
    
    private int segcount=1;
    private int size;    
    private Comparator<Point> comparator;
    private Point[] eqslope = new Point[4]; //points ordered from smallest to largest
    private Point temp;
    private LineSegment[] segs;
    private int segsindex;
    
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
        segs = new LineSegment[size];
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
                            for (int c = 0; c < 4; c++){//insertion sort for 4 elements
                                for (int d = c; d > 0; d--){
                                    if (eqslope[d].compareTo(eqslope[d-1]) == -1){//exchange
                                        temp = eqslope[d];
                                        eqslope[d] = eqslope[d-1];
                                        eqslope[d-1] = temp;
                                    }
                                    else break;
                                }                                
                            }
                            segs[++segsindex] = new LineSegment(eqslope[0],eqslope[3]);
                        }
                        else segcount = 1; //reset counter                                                
                    }
                }                    
            }
        }            
    }
    
    public int numberOfSegments(){        // the number of line segments
        return segsindex;
    }
    public LineSegment[] segments(){                // the line segments
        return segs;
    }
}