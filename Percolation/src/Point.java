import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    public Point(int x, int y) {// constructs the point (x, y)
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw() {// draws this point
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that) {// draws the line segment from this point to that point
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString() {// string representation
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    public int compareTo(Point that){     // compare two points by y-coordinates, breaking ties by x-coordinates
        if (this.x == that.x && this.y == that.y) return 0;
        if (this.y < that.y || (this.y == that.y && this.x < that.x )) return -1;
        else return +1;            
    }
    
    public  double slopeTo(Point that){       // the slope between this point and that point
        double ydiff = that.y - this.y;
        double xdiff = that.x - this.x;
        if (xdiff == 0 && ydiff == 0) return Double.NEGATIVE_INFINITY;
        else if (ydiff == 0) return +0.0;
        else if (xdiff == 0) return Double.POSITIVE_INFINITY;
        else return ydiff/xdiff;
    }
    public Comparator<Point> slopeOrder(){              // compare two points by slopes they make with this point
        return new PointComparator(this);
    }
    
    private class PointComparator implements Comparator<Point>{       
    
        private Point ref;
        
        public PointComparator(Point reference){
            ref = reference;
        }
        
        public int compare(Point p1, Point p2) {
           double slope1 = ref.slopeTo(p1);
           double slope2 = ref.slopeTo(p2);
           if (slope1 == slope2) return 0;
           if (slope1 < slope2) return -1;
           else return +1;   
        }
    }

}