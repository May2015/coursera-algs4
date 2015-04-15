package collinear;

import java.util.Arrays;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

public class Brute 
{
    public static void main(String[] args)
    {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] arrayPoint = new Point[N];  // store all points
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            arrayPoint[i] = new Point(x, y);
        }

        // set the pen color
        StdDraw.setPenColor(StdDraw.BLUE);
        
        //sort the array point 
        Arrays.sort(arrayPoint);
        
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int m = j + 1; m < N; m++)
                    for (int k = m + 1; k < N; k++)//choose 4 points from N points
                    {
                        double slopAB = arrayPoint[i].slopeTo(arrayPoint[j]);
                        double slopAC = arrayPoint[i].slopeTo(arrayPoint[m]);
                        double slopAD = arrayPoint[i].slopeTo(arrayPoint[k]);
                        
                        //judge whether the first three point on a line segment
                        if (slopAB == slopAC && slopAB == slopAD) 
                        {

                                System.out.println(arrayPoint[i] + " -> " + arrayPoint[j] + " -> " 
                                        + arrayPoint[m] + " -> " + arrayPoint[k]);
                                
                                arrayPoint[i].drawTo(arrayPoint[k]);
                                StdDraw.show(0);
                        }
                            
                    }

        
    }
}