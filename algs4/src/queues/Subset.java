package queues;

import edu.princeton.cs.introcs.*;

public class Subset {
    public static void main(String[] args)
    {
        assert(args[0] != null);
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) // save strings to randomized queue
        {
            String str = StdIn.readString();
            queue.enqueue(str);
        }
        int N = queue.size();    // number of Strings
        
        assert(k < N || k == N);    //promise k <= N
        
        
        for (int j = 0; j < k; j++)
        {
            String tempStr = queue.dequeue();
            System.out.println(tempStr);
        }
           
    }
 }