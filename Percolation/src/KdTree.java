import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    
    private Node root;
    
    public KdTree() {                              // construct an empty set of points 
        root = null;
    }
    public boolean isEmpty() {                     // is the set empty? 
        return size() == 0;
    }
    public int size() {                        // number of points in the set 
        return size(root);
    }
    
    private int size(Node current) {
        if (current == null) return 0;
        else return current.size;
    }
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) throw new java.lang.IllegalArgumentException();
        root = insert(root, null, p, 'h', 'r');
    }
    
    private Node insert(Node current, Node previous, Point2D point, char orientation, char side) {
        if (current == null) {            
            if (previous == null) return new Node(point, new RectHV(0,0,1,1), 1, orientation);
            else {
                if (previous.horizontal) {
                    if (side == 'r') {
                        double currentXmin = previous.p.x();
                        double currentXmax = previous.rect.xmax();
                        double currentYmin = previous.rect.ymin();
                        double currentYmax = previous.rect.ymax();
                        return new Node(point, new RectHV(currentXmin, currentYmin, currentXmax, currentYmax), 1, orientation);
                    } else {
                        double currentXmin = previous.rect.xmin();
                        double currentXmax = previous.p.x();
                        double currentYmin = previous.rect.ymin();
                        double currentYmax = previous.rect.ymax();
                        return new Node(point, new RectHV(currentXmin, currentYmin, currentXmax, currentYmax), 1, orientation);
                    }                            
                } else {
                    if (side == 'r') {
                        double currentXmin = previous.rect.xmin();
                        double currentXmax = previous.rect.xmax();
                        double currentYmin = previous.p.y();
                        double currentYmax = previous.rect.ymax();
                        return new Node(point, new RectHV(currentXmin, currentYmin, currentXmax, currentYmax), 1, orientation);
                    } else {
                        double currentXmin = previous.rect.xmin();
                        double currentXmax = previous.rect.xmax();
                        double currentYmin = previous.rect.ymin();
                        double currentYmax = previous.p.y();
                        return new Node(point, new RectHV(currentXmin, currentYmin, currentXmax, currentYmax), 1, orientation);
                    }  
                }
            }            
        }
        if (current.p.equals(point)) return root;
        if (current.horizontal) {
            if (point.x() > current.p.x()) current.rt = insert(current.rt, current, point, 'v', 'r');
            else current.lb = insert(current.lb, current, point, 'v', 'l');
        }
        else {
            if (point.y() > current.p.y()) current.rt = insert(current.rt, current, point, 'h', 'r');
            else current.lb = insert(current.lb, current, point, 'h', 'l');            
        }
        current.size = size(current.lb) + size(current.rt) + 1;
        return current;
    }
    
    
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new java.lang.IllegalArgumentException();
        return contains(root, p) != null;
    }
    
    private Node contains(Node current, Point2D point) {
        if (current == null) return null;
        if (current.p.equals(point)) return current;
        if (current.horizontal == true) {
            if (point.x() > current.p.x()) return contains(current.rt, point);
            else return contains(current.lb, point);
        }
        else {
            if (point.y() > current.p.y()) return contains(current.rt, point);
            else return contains(current.lb, point);            
        }
    }
    
    public void draw() {                        // draw all points to standard draw 
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        Queue<Node> keys = keys();
        for (Node node : keys) {
            node.p.draw();
            node.rect.draw();
        }
    }
    
    private Queue<Node> keys() {
        Queue<Node> queue = new Queue<>();
        return keys(root, queue);
    } 

    private Queue<Node> keys(Node current, Queue<Node> queue) { 
        if (current == null) return queue;
        queue = keys(current.lb, queue);
        queue.enqueue(current);
        queue = keys(current.rt, queue);
        return queue;
    } 
/*    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        if (rect == null) throw new java.lang.IllegalArgumentException();
    }*/
/*    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new java.lang.IllegalArgumentException();
    }*/
    
    private static class Node { //static-no access to other members of enclosing class
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int size = 0;
        private boolean horizontal;
        
        public Node(Point2D p, RectHV rect, int size, char orientation) {
            this.p = p;
            this.size = size;
            horizontal = (orientation == 'h') ? true : false;
            this.rect = rect;
        }
            
    }
    
    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        KdTree tree = new KdTree();
        Point2D p = new Point2D(0.2, 0.4);
        tree.insert(p);
        p = new Point2D(0.2, 0.4);
        tree.insert(p);
        p = new Point2D(0.3, 0.5);
        tree.insert(p);
        p = new Point2D(0.5, 0.4);
        tree.insert(p);
        p = new Point2D(0.2, 0.4);
        tree.insert(p);
        p = new Point2D(0.1, 0.6);
        tree.insert(p);
        p = new Point2D(0.5, 0.4);
        System.out.print(tree.contains(p));
        tree.draw();
        
    }
 }
