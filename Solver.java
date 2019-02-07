import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

//Run->Run Cofigurations->Arguments->VM Arguments write there -Xmx2048m
//Setting a threshold generated for the number of nodes in the priority queue as some puzzle configurations take too much time
//and are unsolvable..denoted by counter

public class Solver {
    
    private SearchNode goal;
    
    private class SearchNode
    {
        private int moves;
        private Board board;
        private SearchNode prev;
        
        public SearchNode(Board initial)
        {
            moves=0;
            board=initial;
            prev=null;
        }
        
    }
    
    public Solver(Board initial)
    {
    	
    	int counter=0;
         PriorityOrder order = new PriorityOrder();
         PriorityQueue<SearchNode> pq = new PriorityQueue(order);
         //PriorityQueue<SearchNode> twin_pq = new PriorityQueue(order);
         
         SearchNode node = new SearchNode(initial);
        //SearchNode twin_node = new SearchNode(initial);
        
        pq.add(node);
        //twin_pq.add(twin_node);
        
        SearchNode min = pq.remove();
        //SearchNode twin_min = twin_pq.remove();
        
        while(!min.board.isGoal() /*&& !twin_min.board.isGoal()*/)
        {
        	if(counter++==1500){System.out.println("Taking too much time..Soln not possible"); };
            for(Board b:min.board.neighbours())
            {
            	
                if(min.prev==null || !b.equals(min.prev.board))
                {
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    pq.add(n);
                   
                }
            
            }
          /*
            
            for(Board b:twin_min.board.neighbours())
            {
                if(twin_min.prev==null || !b.equals(twin_min.prev.board))
                {
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    twin_pq.add(n);
                }
            }
            */
            min = pq.remove();
            
            //twin_min = twin_pq.remove();
        }
        
        if(min.board.isGoal()) 
            goal = min;
        else
            goal=null;
            
        
    }
    
    public boolean isSolvable()
    {
        return goal!=null;
    }
    
    public int move()
    {
        if(!isSolvable())
            return -1;
        else
            return goal.moves;
    }
    public class PriorityOrder implements Comparator<SearchNode>
    {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            // TODO Auto-generated method stub
            int pa = o1.board.manhattan() + o1.moves;
            int pb = o2.board.manhattan() + o2.moves;
            if(pa>pb) return 1;
            if(pa<pb) return -1;
            else return 0;
        }
        
        
        
        
    }

    public Iterable<Board> solution()
    {
        if(!isSolvable()) return null;
        Stack<Board> s = new Stack<Board>();
        
        for(SearchNode n =goal;n!=null;n=n.prev)
          //s.push(n.board);
        	board_print(n.board);
        
        return s;
        
    }
    
    public void board_print(Board board)
    {
    	for(int i=0;i<board.board.length;i++)
        {
            if(i%3==0)System.out.println();
            System.out.print(board.board[i]+" ");
        }
        System.out.println("--------------------------------");
    	
    	
    }
    
    public static void main(String[] args)
    {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter initial configuration:");
    int n =3;
    int[][] blocks = new int[n][n];
    for(int i=0;i<n;i++)
        {
        for(int j=0;j<n;j++)
        {
            //System.out.println("Enter for "+i+" "+j);
            blocks[i][j] = sc.nextInt();
            }
    
        }
          
    Board initial = new Board(blocks);
    Solver solver = new Solver(initial);
    
    if(!solver.isSolvable())
        System.out.println("No solution possible");
    else
    {
        System.out.println("Min. no of moves="+solver.move()+"  See from bottom to top0");
        solver.solution();
        
        /*for(Board board:solver.solution())
        {
            for(int i=0;i<board.board.length;i++)
            {
                if(i%3==0)System.out.println();
                System.out.print(board.board[i]+" ");
            }
            System.out.println("--------------------------------");
        }
        */
    }
    
    sc.close();
    
    }
}
