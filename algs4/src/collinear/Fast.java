package collinear;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

public class Fast 
{
   public static void main(String[] args)
   {
       // rescale coordinates and turn on animation mode
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       StdDraw.show(0);
       
       //StdDraw.setPenRadius(0.01);  // make the points a bit larger for test

       // read in the input
       String filename = args[0];
       In in = new In(filename);
       int N = in.readInt();
       Point[] arrayPoint = new Point[N];  // store all points
       for (int i = 0; i < N; i++)
       {
           int x = in.readInt();
           int y = in.readInt();
           arrayPoint[i] = new Point(x, y);
           //arrayPoint[i].draw();  // for test
       }
       
       // display to screen all at once for test
      // StdDraw.show(0);

       // set the pen color
       StdDraw.setPenColor(StdDraw.BLUE);
       
       //sort the array point 
       //Arrays.sort(arrayPoint); 
       Collection<Integer> subSet = new TreeSet<Integer>();       
       for (int i = 0; i < N - 3; i++)
       {
           
           //Sort the points according to the slopes they makes with p.
           if (arrayPoint[i] != null)
               Arrays.sort(arrayPoint, i+1, N, arrayPoint[i].SLOPE_ORDER);
           else
               continue;
           
           //debug after sort
           /*System.out.println("after sort point refer to p and before sort results points!");
           for (int j = 0; j < N; j++)
           {
               System.out.print(arrayPoint[j] + " -> ");
           }
           System.out.println();*/
           
           //Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. 
           //If so, these points, together with p, are collinear
           Point orgPoint = arrayPoint[i];

           subSet.add(i);
           
           for (int j = i+1; j < N - 2; j++)
           {
               double slopAB = orgPoint.slopeTo(arrayPoint[j]);
               double slopAC = orgPoint.slopeTo(arrayPoint[j+1]);
               double slopAD = orgPoint.slopeTo(arrayPoint[j+2]);
                  // Debug
               /*if (i == 3 && j == 5)
               {

                   System.out.print("when i == 3 && j == 5 the slop set!"); 
                   System.out.println("this is arrayPoint[j]" + arrayPoint[i] + arrayPoint[j]);
                   System.out.printf("%f, %f, %f", slopAB, slopAC, slopAD);
               }*/
               if (slopAB == slopAC && slopAB == slopAD)
               {
                   subSet.add(j);
                   subSet.add(j+1);
                   subSet.add(j+2);
               }
               else
               {
                   if (subSet.size() > 3)   // there are 4 or more points on a line segment 
                   {
                       // Debug
                       //System.out.print(i);
                       
                       break; // stop the loop after find 4 or more point on a line
                   }
               }
           }
           
           if (subSet.size() > 3)
           {
               // Debug
               //System.out.print(99);
               
               Point[] result = new Point[subSet.size()];
               Iterator<Integer> it = subSet.iterator();
               int index = 0;
               while (it.hasNext())  // print all points
               {
                   result[index] = arrayPoint[it.next()];
                   index++;
               }
               
               //sort the result points
               Arrays.sort(result);
               
               //debug after sort
               /*System.out.println("after sort all result points!");
               for (int j = 0; j < N; j++)
               {
                   System.out.print(arrayPoint[j] + " -> ");
               }
               System.out.println();*/
               
               for (int j = 0; j < result.length; j++)
               {
                   if (j != result.length - 1)
                       System.out.print(result[j] + " -> ");
                   else
                       System.out.println(result[j]);
               }
               result[0].drawTo(result[result.length - 1]);   //draw the first point to the last
               StdDraw.show(0);
           }
           
           subSet.clear();   //reset set collections          
       }
  }
}
