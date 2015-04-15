/*************************************
 * this class to test deque class api and iterator 
 ************************************/
package queues;

import java.util.Iterator;

import edu.princeton.cs.introcs.*;

public class TestDeque
{
    private In inFile;      // the numbers for test
    private int N;          // the size of test numbers
    private Out outStd;
    private Deque<Integer> dequeObj;
    private int[] temp;
    
    public TestDeque(int[] a)
    {
        //inFile = in;
        outStd = new Out();
       // N = in.readInt();
        temp = a;
        N = a.length;
        dequeObj = new Deque<Integer>();
    }
    
   public void testAddFirst() // test first in first out
   {
       //insert numbers into dequeue
       /*outStd.println("original array: ");
       while (!inFile.isEmpty())
       {
           int i = inFile.readInt();
           dequeObj.addFirst(i);
           outStd.print(i);
           outStd.print(" ");
       }
       outStd.println("");*/
       for (int i = 0; i < N; i++)
       {
           dequeObj.addFirst(temp[i]);
       }
   }
   
   public void testRemoveLast()
   {
       //remove numbers from queue 
       outStd.println("RemoveLast(): ");
       while (!dequeObj.isEmpty())
       {
           int j = dequeObj.removeLast();
           outStd.print(j);
           outStd.print(" ");
       }
       outStd.println("");
   }
   
   public void testRemoveFirst() 
   {
       //insert numbers into dequeue
       outStd.println("RemoveFirst(): ");
       while (!dequeObj.isEmpty())
       {
           int j = dequeObj.removeFirst();
           outStd.print(j);
           outStd.print(" ");
       }
       outStd.println("");  
   }
   
   public void testAddLast()
   {
       for (int i = 0; i < N; i++)
       {
           dequeObj.addLast(temp[i]);
       }
   }
   
   public void testIterator() //test Iterator
   {
       Iterator<Integer> iterator = dequeObj.iterator();
       
       while(iterator.hasNext())
       {
           int str = iterator.next();
           System.out.println(str);
       }
   }
}