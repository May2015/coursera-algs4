/****************************************************************
 * this class find solution of puzzle with A* 
 * we use two PQ to store open list of close list
 ****************************************************************/

package puzzle;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Solver 
{
    private MinPQ<SearchNode> openSet;      //the set of tentative nodes to be evaluated, initially containing the start node
    private Queue<SearchNode> closeSet;     //the set of nodes already evaluated
    private SearchNode start;               //start node
    private SearchNode current;             //end node
    private boolean solvable;
    private int moves;
    
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        openSet = new MinPQ<SearchNode>(new ByPriority());
        closeSet = new Queue<SearchNode>();
        solvable = false;
        
        moves = -1;                //initialize the start searchNode and add to openSet
        start = new SearchNode(initial, moves);
        start.setParrent(null);
        openSet.insert(start);
        
        while (!openSet.isEmpty())
        {
            current = openSet.delMin();      //remove the min priority node from openSet
            moves++;
            closeSet.enqueue(current); 
            
            if (current.board.isGoal())             //is the goal node in the close set
            {
                solvable = true;
                break;
            }
            
            Iterator<Board> iter = current.board.neighbors().iterator();   // the neighbors queue iterator of current search node
            boolean contains = false;
            while (iter.hasNext())
            {
               SearchNode neighborNode = new SearchNode(iter.next(), moves + 1); 
               Iterator<SearchNode> iterOfOpen = openSet.iterator();           //iterator of the open set
               
               while (iterOfOpen.hasNext())          // does this board exit? 
               {
                   iterOfOpen.next().board.equals(neighborNode.board);
                   contains = true;
                   break;
               }
               iterOfOpen = null;
               
               if (!contains)           // add neighbor node when it isn't in open set
               {
                   openSet.insert(neighborNode);
                   neighborNode.setParrent(current);
               }
            }
        }
        
        
    }
    
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!isSolvable())
        {
            return -1;
        }
        return moves;
        
    }
    
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable())
        {
            return null;
        }
        
        //path from current to start
        Stack<Board> path = new Stack<Board>();
        while(current.parrent != null)
        {
            Board board = current.board;
            path.push(board);
            current = current.parrent;
        }
        return path;
        
    }
    
   /*public static void main(String[] args) // test client
    {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }*/
    
    /*******************************************************************
     * helper class
     *******************************************************************/
    private class SearchNode
    {
        private Board board;
        private int manhat;
        private int mv;     //moves number from start node
        private int priority; 
        private SearchNode parrent;
       
        public SearchNode(Board board, int moves)
        {
            this.board = board;
            this.mv = moves;            
            this.manhat = board.manhattan();
            this.priority = mv + this.manhat;

            parrent = null;
        }

        public int getPriority()
        {
            return priority;
        }
        
        public void setParrent(SearchNode node)
        {
            parrent = node;
        }
        
    }
   
    
    private class ByPriority implements Comparator<SearchNode>
    {
        @Override
        public int compare(SearchNode node1, SearchNode node2)                  //compare two node by priority
        {
            int diff = node1.getPriority() - node2.getPriority();
            return (diff > 0)?1
                   :(diff == 0)?0
                   :-1;
        }
    }
    
}