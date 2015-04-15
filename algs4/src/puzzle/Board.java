/*
 * 
 */
package puzzle;

import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdRandom;

public class Board {
    private int[][] tiles;
    private final int N;                //dimension of board
    
    // construct a board from an N-by-N array of blocks  
    // (where blocks[i][j] = block in row i, column j)  
    public Board(int[][] blocks)           
    {
        N = blocks.length;
        tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                tiles[i][j] = blocks[i][j];     //initial blocks of board

            }
    }
    
    public int dimension()                 // board dimension N
    {
        return N;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        int ham = -1;                        // initial state
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (tiles[i][j] != i * N + j + 1)
                {
                    ham += 1;               // don't count 0 block  and consider initial state (ham - 1 + 1)
                }  
            }
        
        return ham;  
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhat = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (tiles[i][j] != 0)
                {
                    int rightX = 0;                     //correct x-coordinate of blocks[i][j]
                    int rightY = 0;                     //correct y-coordinate of blocks[i][j]
                    if (tiles[i][j]%N != 0)
                    {
                        rightX =  tiles[i][j]/N;                       
                        rightY = tiles[i][j]%N - 1;                            
                    }
                    else
                    {
                        rightX = tiles[i][j]/N - 1;
                        rightY = N - 1;
                    }
                    
                    manhat += Math.abs(i - rightX) + Math.abs(j - rightY);   
                }
            }
        return manhat;                 // consider initial state
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        return (hamming() == 0);
    }
    
    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    {
        Board twinBoard = new Board(this.tiles);
        //find blank's position
        int blankI = -1;
        int blankJ = -1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (tiles[i][j] == 0)
                {
                    blankI = i;             //initial blank block position
                    blankJ = j;             //initial blank block position
                }   
            }
        
        //find a block that it's not same row with blank to guarantee not blank block
        int randRow = blankI + 1;
        int randColumn = StdRandom.uniform(N);
        if (randRow > N - 1)
            randRow = blankI - 1;
        
        //prefer to choose the right block of the above block
        int neighborColumn = randColumn + 1;       
        if (!isValidIndex(randRow, neighborColumn))
            neighborColumn = randColumn - 1;
        
        swap(twinBoard, randRow, randColumn, randRow,  neighborColumn);   //exchange two adjacent blocks in the same row
        return twinBoard;
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board)y;
        return (this.N == that.N) && (deepEquals(this.tiles, that.tiles));
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> neighborQueue = new Queue<Board>();
        //find blank's position
        int blankI = -1;
        int blankJ = -1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (tiles[i][j] == 0)
                {
                    blankI = i;             //initial blank block position
                    blankJ = j;             //initial blank block position
                }   
            }
        
        //vector used to move to 4 directions
        int[] dirI = {1, 0, -1, 0};
        int[] dirJ = {0, 1, 0, -1};
        //get position of the neighbor
        int neighborI = 0;
        int neighborJ = 0;
        for (int k = 0; k < 4; k++)
        {
            neighborI = blankI + dirI[k];
            neighborJ = blankJ + dirJ[k];
            if (isValidIndex(neighborI, neighborJ))
            {
                Board neighborBoard = new Board(this.tiles);
                swap(neighborBoard, blankI, blankJ, neighborI, neighborJ);
                neighborQueue.enqueue(neighborBoard);                
            }
        }
        return neighborQueue;
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }    
 /****************************************************************************
  *  helper function to check index and exchange objections and judge equality  
  ****************************************************************************/
    
    private boolean isValidIndex(int x, int y)   // check the bound of index
    {
        if (0 <= x  && x < N && 0 <= y && y < N)
            return true;
        return false;
            
    }
    
    private void swap(Board board, int blankI, int blankJ, int neighborI, int neighborJ) //exchange blank block with adjacent block
    {
        int temp = board.tiles[blankI][blankJ];
        board.tiles[blankI][blankJ] = board.tiles[neighborI][neighborJ];
        board.tiles[neighborI][neighborJ] = temp;
    }
    

    
    private boolean deepEquals(int[][] a, int[][] a2)
    {
        if (a == a2) 
            return true;
        if (a == null || a2 == null) 
            return false;
        
        for (int i = 0; i < N; i++)
        {
            if (Arrays.equals(a[i], a2[i]))  
                return true;
        }
         return false;   
    }
    
    
    public static void main(String[] args) // unit tests (not graded)
    {
        // command-line argument
        /*String filename = args[0];

        // read in the board specified in the filename
        In in = new In(filename);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        
        Board testBoard = new Board(tiles);
        Board twinBoard = testBoard.twin();*/
    }
}