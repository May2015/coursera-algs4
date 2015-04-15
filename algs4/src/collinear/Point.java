/*************************************************************************
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

package collinear;

import java.util.Comparator;

import edu.princeton.cs.introcs.StdDraw;

public class Point implements Comparable<Point>
{
    public final Comparator<Point> SLOPE_ORDER;        // compare points by slope to this point
    private final int x;                                     // x-coordinates of point
    private final int y;                                     // y-coordinates of point
    
    public Point(int x, int y)                         // construct the point (x, y)
    {
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new ComparatorWithSlope();
    }
    
    /*private final int ccw(Point a, Point b, Point c)  // is a -> b -> c a counterclockwise turn
    {
        double area2 = (b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x);
        
        if (area2 < 0)      return -1;          // a -> b -> c clockwise
        else if (area2 > 0) return +1;          //counter-clockwise
        else                return 0;           //collinear
        
    }*/
    
    private class ComparatorWithSlope implements Comparator<Point>
    {
        
        @Override
        public int compare(Point q1, Point q2) 
        {
            /*double dy1 = q1.y - y;
            double dy2 = q2.y - y;
            
            if (dy1 == 0 && dy2 == 0)    //p, q1, q2 on a horizon line
            {
                return 0;
            }
            else if (dy1 >= 0 && dy2 < 0) return -1;  // q1 above p, q2 below p
            else if (dy2 >= 0 && dy1 < 0) return +1;  // q1 below p, q2 above p
            else 
                 return -ccw(Point.this, q1, q2);*/     //both below or above p
            if (q1 == null && q2 == null) return -1;
            if (q1 == null || q2 == null) return -1;
            double slop1 = Point.this.slopeTo(q1);
            double slop2 = Point.this.slopeTo(q2);
            if (slop1 == Double.NEGATIVE_INFINITY && slop2 == Double.NEGATIVE_INFINITY) return 0;
            if (slop1 == Double.POSITIVE_INFINITY && slop2 == Double.POSITIVE_INFINITY) return 0;
            if (slop1 == Double.NEGATIVE_INFINITY || slop2 == Double.NEGATIVE_INFINITY) return -1;
            if (slop1 == Double.POSITIVE_INFINITY || slop2 == Double.POSITIVE_INFINITY) return +1;
            /*if (slop1 == 0 && slop2 == 0) return 0;            
            if (slop1 > 0 && slop2 <= 0) return +1;
            if (slop1 <= 0 && slop2 > 0) return -1;*/
             //double df = slop1 - slop2;  
            if (slop1 > slop2) return +1;  //both below or above p
            else if (slop1 == slop2 ) return 0;
            else return -1;

        }

    }
    
    public void draw()                               // draw this point
    {
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that)                   // draw the line segment from this point to that point
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString()                           // string representation
    {
        return "(" + x + ", " + y + ")";
    }
    
    public int compareTo(Point that)                // is this point lexicographically smaller than that point?
    {
        return (y < that.y)?-1
               :(y > that.y)?1
               :(y == that.y && x < that.x)?-1
               :(y == that.y && x > that.x)?1
               :0;
    }
    
    public double slopeTo(Point that)                  // the slope between this point and that point
    {
        if (that == null) return -1;           //for selected points
        if (compareTo(that) == 0)
            return Double.NEGATIVE_INFINITY;   //Double.NEGATIVE_INFINITY represent two same points
        if (y == that.y)
            return 0;                          // 0 represent horizontal line 
        if (x == that.x)
            return Double.POSITIVE_INFINITY;   //Double.POSITIVE_INFINITY represent vertical line
        
        return ((double)(y - that.y))/(x - that.x);
    }
    
}