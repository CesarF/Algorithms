
/**
 * Collinear points data structure using brute force algorithm
 * It works only with four point line segments 
 * 
 * @author CesarF 5/19/2017
 *
*/
public class BruteCollinearPoints {
     
    private LineNode first;
    private int quantity;
 
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
         
        quantity = 0;
        for (int i = 0; i < points.length; i++) {
            Point pc = null;
            Point qc = null;
            Point p = points [i];
             
            if (p == null) throw new NullPointerException();
            for (int j = i + 1 ; j < points.length; j++) {
                Point q = points [j];
                if (q == null) throw new NullPointerException();    
                qc = q;
                pc = p;
                double slope1 = p.slopeTo(q);
                if (slope1 == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                if (qc.compareTo(pc) < 0) {
                    qc = pc;
                    pc = q;
                }
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points [k];
                    if (r == null) throw new NullPointerException();
                    double slope2 = p.slopeTo(r);
                    if ( slope2 == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                    if ( slope2 == slope1 ) {
                        if (r.compareTo(pc) < 0)
                            pc = r;
                        else if (qc.compareTo(r) < 0)
                            qc = r;
                        for (int l = k +1; l < points.length; l++) {
                            Point s = points [l];
                            if (s == null) throw new NullPointerException();
                            double slope3 = p.slopeTo(s);
                            if ( slope3 == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                            if ( slope3 == slope1 ) {
                                if (s.compareTo(pc) < 0) 
                                    pc = s;
                                else if (qc.compareTo(s) < 0) 
                                    qc = s;
                                LineSegment ls = new LineSegment(pc, qc);
                                LineNode ln = new LineNode();
                                ln.segment = ls;
                                quantity ++;
                                LineNode temp = first;
                                first = ln;
                                first.next = temp;
                                l = points.length;
                                k = points.length;
                            }
                        }
                    }                   
                }                           
            }
        }
    }
     
    private class LineNode {
         
        private LineNode next;
        private LineSegment segment;
                 
    }
     
    public int numberOfSegments() {
        // the number of line segments
        return quantity;
    }
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] segments = new LineSegment[quantity];
        LineNode node = first;
        for (int i = 0; i < segments.length; i++) {
            segments[i] = node.segment;
            node = node.next;
        }
        return segments;
    }
}