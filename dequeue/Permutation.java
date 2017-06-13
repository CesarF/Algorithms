
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * Permutation initial point class
 * 
 * @author CesarF 5/19/2017
 *
*/
public class Permutation {
	
	public static void main(String[] args) {
		RandomizedQueue <String> que = new RandomizedQueue <String>();
		while (!StdIn.isEmpty()) {
			String txt = StdIn.readString();
			que.enqueue(txt);
		}		

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(que.dequeue());
        }
        
	}

}
