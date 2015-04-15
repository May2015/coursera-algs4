package queues;

import edu.princeton.cs.introcs.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int noOfItem;
    private Item[] a;       //array of items
    private int randomNo;
    
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        noOfItem = 0;
        randomNo = 0;
        a = (Item[]) new Object[2];
    }
    
    public boolean isEmpty()                 // is the queue empty?
    {
        return noOfItem == 0;
    }
    
    public int size()                        // return the number of items on the queue
    {
        return noOfItem;
    }
    
    private void resize(int capacity)           // resize the underlying array holding the elements
    {
        assert (capacity >= noOfItem);
        Item[] temp = (Item[])new Object[capacity];
        
        for (int i = 0; i < noOfItem; i++)
            temp[i] = a[i];
        
        a = temp;
    }
    
    public void enqueue(Item item)           // add the item
    {
        if (item == null)
            throw new NullPointerException("null item is forbiden to add!");
        
        if (noOfItem == a.length)           //double size of array if necessary
            resize(2*a.length);
        a[noOfItem++] = item;
    }
    
    public Item dequeue()                    // remove and return a random item
    {
        Item item = sample();
        noOfItem--;
        
        if (noOfItem != 0)   //exchange the last item to removed item  to keep normal random generator 
        {
            a[randomNo] = a[noOfItem]; 
            a[noOfItem] = null;
        }
        
        if (noOfItem > 0 && noOfItem == a.length/4)  // shrink size of array if necessary
            resize(a.length/2);
        
        return item;
    }
    
    public Item sample()                     // return (but do not remove) a random item
    {
        if (isEmpty()) 
            throw new NoSuchElementException("the queue is empty!");  
        
        randomNo = genRandom();
        Item item = a[randomNo];            //save the removed item to return        
        return item;
    }
    
    @Override
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomArrayIterator();
    }
    
    //an iterator, doesn't implement remove() since it's optional
    private class RandomArrayIterator implements Iterator<Item> 
    {
        private int i;
        private int[] tempRandom;
        
        public RandomArrayIterator()
        {
            i = noOfItem;
            tempRandom = new int[noOfItem];
            
            for (int j = 0; j < noOfItem; j++)
            {
                tempRandom[j] = j;
            }
            StdRandom.shuffle(tempRandom);
        }
        
        @Override
        public boolean hasNext()
        {
            return i > 0;
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException("the queue is empty!");
            
            int index = tempRandom[--i];
            return a[index];
        }
    }   
    
    private int genRandom()                     // generate random number from 0 to noOfItem
    {
        return StdRandom.uniform(noOfItem);
    }
    
    /*public static void main(String[] args)   // unit testing
    {
        
    }*/
 }