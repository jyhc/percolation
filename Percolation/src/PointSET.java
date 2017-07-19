import java.util.Comparator;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
   
   private SET<Point2D> bst; 
    
   public PointSET() {                              // construct an empty set of points 
       bst = new SET<Point2D>();
   }
   
   public boolean isEmpty() {                     // is the set empty? 
       return bst.isEmpty();
   }
   
   public int size() {                        // number of points in the set 
       return bst.size();
   }
   
   public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
       if (p == null) throw new java.lang.IllegalArgumentException();
       bst.add(p);
   }
   
   public boolean contains(Point2D p) {           // does the set contain point p? 
       if (p == null) throw new java.lang.IllegalArgumentException();
       return bst.contains(p);
   }
   
   public void draw() {                        // draw all points to standard draw 
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
       for (Point2D point : bst) point.draw();
   }
   
   public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
       if (rect == null) throw new java.lang.IllegalArgumentException();
       Stack<Point2D> range = new Stack<Point2D>();
       for (Point2D point : bst) {
           if (rect.contains(point)) range.push(point);
       }
       return range;
   }
   
   public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
       if (p == null) throw new java.lang.IllegalArgumentException();
       if (bst.isEmpty()) return null;
       else {
           Comparator<Point2D> comparator = new PointComparator(p);
           MinPQ<Point2D> nearestPQ = new MinPQ<>(comparator);
           for (Point2D point : bst) nearestPQ.insert(point);
           return nearestPQ.delMin();
       } 
   }
   
   private class PointComparator implements Comparator<Point2D>{       

       Point2D reference;
       
       public PointComparator(Point2D ref) {
           reference = ref;
       }
       
       public int compare(Point2D p1, Point2D p2) {
          if (p1.distanceSquaredTo(reference) < p2.distanceSquaredTo(reference)) return -1;
          else if (p1.distanceSquaredTo(reference) > p2.distanceSquaredTo(reference)) return +1;
          else return 0;
       }
   }
   
   public static void main(String[] args) {                 // unit testing of the methods (optional) 
       
   }
}