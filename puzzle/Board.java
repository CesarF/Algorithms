import edu.princeton.cs.algs4.Stack;

/**
 * Board -> 8 puzzle project
 * 
 * @author CesarF 5/19/2017
 *
*/
public class Board {
	
	private final int[][] blocks;
	
	private final int n;
		
	private int zeroi;
	
	private int zeroj;
	
	private int hamming;
	
	private int manhattan;
		
    public Board(int[][] blocks) {
    	if (blocks == null) throw new IllegalArgumentException();
    	this.n = blocks.length;
    	this.blocks = blocks;
    	this.hamming = 0;
    	this.zeroi = 0;
		this.zeroj = 0;
		this.manhattan = 0;
    	for (int i = 0, k = 0; i < blocks.length; i++)
			for (int j = 0; j < blocks[0].length; j++, k++)	{
				if (blocks[i][j] != 0 && blocks[i][j] != k + 1)
    				this.hamming++;
				if (blocks[i][j] == 0) {
					this.zeroi = i;
					this.zeroj = j;
				}
				if (blocks[i][j] != 0)
    				this.manhattan += getDistance(k, blocks[i][j] - 1, n);
			}
    }
    
    public int dimension() {
        // board dimension n
    	return this.n;
    }
    
    public int hamming() {
        // number of blocks out of place
    	return this.hamming;
    }
    
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
    	return this.manhattan;
    }
    
    private static int getFloor(int pos, int n) {
    	int floor = 1;
    	int m = n;
    	while (pos >= m) {
    		floor++;
    		m = n * floor;
    	}    	
    	return floor;
    }
    
    private static int getDistance (int a, int b, int n) {
    	int af = getFloor(a, n);
    	int bf = getFloor(b, n);
    	int c = n - ((af * n) - a);
    	int d = n - ((bf * n) - b);
    	return Math.abs(c - d) + Math.abs(af - bf);
    }
    
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
    	Board twin = null;
		int [][] matriz = cloneBlocks(blocks);	    	
    	for (int i = 0; i < n; i++)
			if (blocks [i][0] != 0 && blocks [i][1] != 0) {
				int temp = matriz[i][0];
				matriz[i][0] = matriz[i][1];
				matriz[i][1] = temp;					
				twin = new Board(matriz);
				break;
			}	    	
    	
    	return twin;
    	
    }
//    
    public boolean equals(Object y) {
        // does this board equal y?
    	 if (y == this) return true;
         if (y == null) return false;
         if (y.getClass() != this.getClass()) return false;
         Board that = (Board) y;
         if (n != that.dimension()) return false;
         
         for (int i = 0; i < blocks.length; i++)
        	for (int j = 0; j < blocks.length; j++)			
        		if (blocks[i][j] != that.blocks[i][j])
        			return false;
		 
         return true;
    }
   
    
    public Iterable<Board> neighbors() {
        // all neighboring boards
    	Stack<Board> neighbors = new Stack<Board>();
    	if (zeroi > 0) {
    		int[][] newBlocks = cloneBlocks(blocks);
    		int temp = newBlocks[zeroi][zeroj];
    		newBlocks[zeroi][zeroj] = newBlocks[zeroi - 1][zeroj];
    		newBlocks[zeroi - 1][zeroj] = temp;
    		Board b = new Board(newBlocks);
    		neighbors.push(b);
    	}
    	if (zeroi < n - 1) {
    		int[][] newBlocks = cloneBlocks(blocks);
    		int temp = newBlocks[zeroi][zeroj];
    		newBlocks[zeroi][zeroj] = newBlocks[zeroi + 1][zeroj];
    		newBlocks[zeroi + 1][zeroj] = temp;
    		Board b = new Board(newBlocks);    		
    		neighbors.push(b);
    	}
    	if (zeroj > 0) {
    		int[][] newBlocks = cloneBlocks(blocks);
    		int temp = newBlocks[zeroi][zeroj];
    		newBlocks[zeroi][zeroj] = newBlocks[zeroi][zeroj - 1];
    		newBlocks[zeroi][zeroj - 1] = temp;
    		Board b = new Board(newBlocks);
    		neighbors.push(b);
    	}
    	if (zeroj < n - 1) {
    		int[][] newBlocks = cloneBlocks(blocks);
    		int temp = newBlocks[zeroi][zeroj];
    		newBlocks[zeroi][zeroj] = newBlocks[zeroi][zeroj + 1];
    		newBlocks[zeroi][zeroj + 1] = temp;
    		Board b = new Board(newBlocks);
    		neighbors.push(b);
    	} 	
    	return neighbors;
    }
    
    private int[][] cloneBlocks(int[][] m) {
    	int[][] n = new int[m.length][m[0].length];
    	for (int i = 0; i < n.length; i++) {
			for (int j = 0; j < n[0].length; j++) {
				n[i][j] = m[i][j];
			}
		}
    	return n;
    }
    
    public String toString() {
        // string representation of this board (in the output format specified below)
    	StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            s.append(String.format("%2d ", blocks[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
    }
    
    public static void main(String[] args) {
    	
    	int[][] matriz = new int[][] {
				{ 1,  4,  3 },
		        { 0,  5,  6 },
		        { 7,  2,  8 }};
    	int m = 0;
    	for (int i = 0, k = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz[0].length; j++, k++)	{
				if (matriz[i][j] != 0)
    				m += getDistance(k, matriz[i][j] - 1, 3);
			}
    	System.out.println(m);
    }
}
