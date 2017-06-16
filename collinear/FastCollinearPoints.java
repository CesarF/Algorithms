import java.util.Arrays;
 
/**
 * Collinear points data structure using quick sort algorithm
 * Only adds line segments when current node is the first of line
 * 
 * @author CesarF 5/19/2017
 *
*/
public class FastCollinearPoints {
     
	private LineSegment[] segments;
    private LineNode first;
    private int quantity;
     
    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException();
        quantity = 0;
        for (int i = 0; i < points.length ; i++) {
            Point r = points [i];
            // System.out.println("Point "+r+" - "+i+" - "+points.length);
            Point p = null;
            // System.out.println("r: "+r);
            if (r == null) throw new NullPointerException();
            Point[] tPoints = Arrays.copyOf(points, points.length);
            Arrays.sort(tPoints, 0, points.length, r.slopeOrder());
            double currentSlope = Double.NEGATIVE_INFINITY;
            int count = 0;
            Point q =  null;
            for (int j = 1; j < tPoints.length; j++) {               
                Point s = tPoints[j];
                // System.out.println("\t r: "+r+" s: "+s);
                if (s == null) throw new NullPointerException();
                double slope = r.slopeTo(s);        
                 
                // System.out.println("\t\t "+slope+" - "+count );
                // System.out.println("\t\t\t p: "+p+" - q: "+q );
                if (slope == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
                if (slope == currentSlope) {    
                    count ++;
                    if (s.compareTo(p) < 0)
                        p = s;
                    else if ( q.compareTo(s) < 0)
                        q = s;
                }   
                if (j == tPoints.length-1 || slope != currentSlope) {   
                    if (count >= 2 ) {
                    	if (r.compareTo(p) == 0) {
	                    	LineNode ln = new LineNode();
	                        ln.line = new LineSegment(p, q);
	                        quantity ++;
	                        LineNode temp = first;
	                        first = ln;
	                        first.next = temp;
                    	}
                        // System.out.println("\t\t\t Added: "+ls);
                    }
                    count = 0;
                    currentSlope = slope;                           
                     
                    if (s.compareTo(r) < 0) {
                        p = s;
                        q = r;
                    }
                    else {
                        p = r;
                        q = s;
                    }
                }           
            }
        }
    }
     
    private class LineNode {
         
        private LineNode next;
        private LineSegment line;
                 
    }
        
    public int numberOfSegments()  {
        // the number of line segments
        return quantity;
    }
    public LineSegment[] segments() {
        // the line segments
		if (segments == null) {
			segments = new LineSegment[quantity];
			LineNode node = first;
			for (int i = 0; i < segments.length; i++) {
				segments[i] = node.line;
				node = node.next;
			}
		}        
        return segments;
    }
 
}