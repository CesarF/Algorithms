import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
			
	private boolean isSolvable;
	
	private Stack<Board> solution;
	
	private int moves;
	
	public Solver(Board initial) {
		if (initial == null) throw new java.lang.IllegalArgumentException();
		MinPQ<SearchNode> heap = new MinPQ<SearchNode>(getComparator());
		MinPQ<SearchNode> heapTwin = new MinPQ<SearchNode>(getComparator());
		 // find a solution to the initial board (using the A* algorithm)
		Board goal = initial;
		Board goalTwin = initial.twin();
		
		SearchNode searchGoal = new SearchNode(goal, null, 0);
		SearchNode searchGoalTwin = new SearchNode(goalTwin, null, 0);
		
		heap.insert(searchGoal);		
		heapTwin.insert(searchGoalTwin);
		
		boolean someoneFinish = false;
		boolean myturn = false;
		//System.out.println("\tSearching solution");
		while (!someoneFinish) {	
			if (!myturn) {
				myturn = true;
				searchGoal = heap.delMin();
				if (!searchGoal.searchNode.isGoal()) {
					for (Board bN : searchGoal.searchNode.neighbors()) {						
						if (searchGoal.previous == null || !searchGoal.previous.searchNode.equals(bN)) {
							SearchNode node = new SearchNode(bN, searchGoal, searchGoal.moves + 1);
							heap.insert(node);
						}
							
					}				
				}	
				else {
					someoneFinish = true;
				}					
			}
			else {
				myturn = false;
				searchGoalTwin = heapTwin.delMin();
				if (!searchGoalTwin.searchNode.isGoal()) {
					for (Board board : searchGoalTwin.searchNode.neighbors())
						if (searchGoalTwin.previous == null || !searchGoalTwin.previous.searchNode.equals(board)) {
							SearchNode node = new SearchNode(board, searchGoalTwin, searchGoalTwin.moves + 1);
							heapTwin.insert(node);
						}			
				}	
				else {
					someoneFinish = true;
				}					
			}
					
		}
		if (searchGoalTwin.searchNode.isGoal()) {
			isSolvable = false;
			moves = -1;
			this.solution = null;
		}
		else {
			isSolvable = true;
			this.solution = searchGoal.getIterable();
			moves = searchGoal.lenght();
		}
	}
	
	private class SearchNode {
		
		private Board searchNode;
		private SearchNode previous;
		private int lenght;
		private int priority;
		private int moves;
		
		
		public SearchNode(Board node, SearchNode prev, int m) {
			searchNode = node;
			previous = prev;
			lenght = - 1;
			this.moves = m;
			priority = searchNode.manhattan() + this.moves;
		} 
		
		public Stack<Board> getIterable() {	
			
			Stack<Board> queue = new Stack<Board>();
			SearchNode node = this;
			while (node != null) {
				queue.push(node.searchNode);				
				node = node.previous;	
				lenght++;
			}
			return queue;
		}
		
		public int priority() {
			return priority;
		}
		
		public int lenght() {	
			return lenght;
		}
		
	}	
	
	private Comparator<SearchNode> getComparator(){
		return new Comparator<SearchNode>() {			
			@Override
			public int compare(SearchNode o1, SearchNode o2) {				
				return o1.priority() > o2.priority() ? 1 : 
					o1.priority() < o2.priority() ? -1 : 0;
			}		
		};		
	}
	
    public boolean isSolvable()  {
    	// is the initial board solvable?
    	return isSolvable;
    }
    
    public int moves() {
    	return moves;
    	// min number of moves to solve initial board; -1 if unsolvable
    }
    
    public Iterable<Board> solution() {
    	return solution;
    	// sequence of boards in a shortest solution; null if unsolvable
    	//return solution.getIterable();
    }
    
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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
    }    
}
