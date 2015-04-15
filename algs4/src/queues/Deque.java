package queues;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int length; // size of dequeue
    private Node head;   
    private Node tail;

    private class Node // helper linked list class
    {
        private Item item;
        private Node prevous;
        private Node next;
    }

    public Deque() // construct an empty dequeue
    {
        head = null;
        tail = null;
        length = 0;
    }

    public boolean isEmpty() // is the deque empty?
    {
        return head == null;
    }

    public int size() // return the number of items on the deque
    {
        return length;
    }

    public void addFirst(Item item) // add the item to the front
    {
        checkAddOpt(item);

        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        head.prevous = null;
        length++;

        if (oldHead == null) // insert the first item into dequeue  from addFirst()
        {
            tail = head; 
        } 
        else 
        {
            oldHead.prevous = head; 
        }
    }

    public void addLast(Item item) // add the item to the end
    {
        checkAddOpt(item);

        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.next = null;
        tail.prevous = oldTail;

        if (oldTail != null)
            oldTail.next = tail;
        else
            head = tail; // insert the first item into dequeue from addLast()

        length++;
    }

    public Item removeFirst() // remove and return the item from the front
    {
        if (isEmpty())
            throw new NoSuchElementException(
                    "the queue is empty!");

        Item item = head.item; // save item to return
        head = head.next;
        length--;

        if (length == 0) // judge whether this item is the last item of the dequeue
        { 
            tail = head;
        }
        else
        {
            head.prevous = null;
        }
        return item;
    }

    public Item removeLast()               // remove and return the item from the end
    {
        if (isEmpty())
            throw new NoSuchElementException("the queue is empty!");       
        
        Item item = tail.item; // save item to return
        tail = tail.prevous;
        length--;
        
        if (tail == null) // judge whether this item is the last item of the dequeue
        {
            head = tail;           
        }
        else
        {
            
            tail.next = null; 
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() // return an iterator over items in order
                                     // from front to end
    {
        return new ListIterator();
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> 
    {
        private Node current = head;
        
        @Override
        public boolean hasNext()  
        { 
            return current != null;                     
        }
        
        @Override
        public void remove()      
        { 
            throw new UnsupportedOperationException();  
        }

        @Override
        public Item next() 
        {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    private void checkAddOpt(Item item) // check the reference of added item
    {
        if (item == null)
            throw new NullPointerException("null item is forbiden to add!");
    }

    /*public static void main(String[] args) // unit testing
    {
        //In in = new In(args[0]);      // input file
        int[] a = {1, 2};
        Deque<Integer> obj123 = new Deque<Integer>();
        
        for (int i:a)
        {
            //obj123.addFirst(i);
            obj123.addLast(i);
        }
        
        while (!obj123.isEmpty())
        {
           // obj123.removeFirst();
            obj123.removeLast();
        }
        obj123.removeLast();
        //obj123.removeFirst();
    }*/

}