import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    
    private int N;
    public int[] board;
    
    public Board()
    {
        
    }
    
    public Board(int[][] blocks)
    {
         N = blocks[0].length;
         board = new int[N*N];
         for(int i=0;i<N;i++)
             for(int j=0;j<N;j++)
                 board[i*N+j] = blocks[i][j];
        
        
    }
    
    private Board(int[] board)
    {
        N = (int)Math.sqrt(board.length);
        this.board = new int[board.length];
        for(int i=0;i<board.length;i++)
            this.board[i]=board[i];
        
    }
    
    public int hamming()
    {
        int count=0;
        
        for(int i=0;i<N*N;i++)
            if(board[i]!=i+1 && board[i]!=0)
                count++;
        
        return count;
    }
    
    public boolean isGoal()
    {
        for(int i=0;i<N*N-1;i++)
            if(board[i]!=i+1)
                return false;
        return true;
    }

    
    private Board exch(Board a,int i,int j)
    {
        int temp =a.board[j];
        a.board[j] = a.board[i];
        a.board[i] = temp;
        
        return a;
    }
    
    public Iterable<Board> neighbours()
    {
     int index=0;
     boolean found    =false;
     Board neighbour;
     Queue<Board> q = new LinkedList<Board>();
     
     for(int i=0;i<board.length;i++)
     {
         if(board[i]==0)
         {
             index=i;
             found=true;
             break;
         }
     }
     
     if(!found) return null;
     
     //not present in upper row
     if(index/N !=0)
     {
         neighbour = new Board(board);
         exch(neighbour,index,index-N); //exchange with upper block
         q.add(neighbour);
     }
     
     //not present in lower row
     if(index/N != (N-1))
     {
         neighbour = new Board(board);
         exch(neighbour,index,index+N); //exchange with lower block
         q.add(neighbour);
     }
     
     //not present in leftmost column
     if(index%N !=0)
     {
         neighbour = new Board(board);
         exch(neighbour,index,index-1); //exchange with leftmost block
         q.add(neighbour);
     }
     
     //not present in rightmost column
     if(index%N !=(N-1))
     {
         neighbour = new Board(board);
         exch(neighbour,index,index+1); //exchange with rightmost block
         q.add(neighbour);
     }
     

     return q;
    }
    
    
    public boolean equals(Object y)
    {
        if(y==this) return true;
        if(y==null) return false;
        
        if(y.getClass()!=this.getClass()) return false;
        
        Board that = (Board)y;
        return Arrays.equals(this.board,that.board);
    }
    
    public int manhattan() {               // sum of Manhattan distances between blocks and goal
        int sum = 0;
        for (int i = 0; i < N * N; i++)
            if (board[i] != i + 1 && board[i] != 0)
                sum += manhattan(board[i], i);
        return sum;
    }

    private int manhattan(int goal, int current) {  // return manhattan distance of a misplaced block
        int row, col;                                                // row and column distance from the goal
        row = Math.abs((goal - 1) / N - current / N);              // row difference
        col = Math.abs((goal - 1) % N - current % N);             // column difference
        return row + col;
}
    
}


