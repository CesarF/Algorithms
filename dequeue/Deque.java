import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Deque data structure
 * 
 * @author CesarF 5/19/2017
 *
*/
public class Deque <Item> implements Iterable <Item> {
    
	private Node first;
	private Node last;
	private int quantity;
	
    public Deque() {
        // construct an empty deque
    }
    public boolean isEmpty() {
        // is the deque empty?
    	return first == null;
    }
    public int size() {
        // return the number of items on the deque
    	return quantity;
    }
    public void addFirst(Item item) {
        // add the item to the front
    	if (item == null) throw new NullPointerException();
    	Node  newNode = new Node();
    	newNode.item = item;
    	newNode.next = null;
    	if (isEmpty()) {
    		last = newNode;
    		first = last;
    	}
    	else {    	
    		Node oldFirst = first;
    		first = newNode;
    		oldFirst.before = first;
    		first.next = oldFirst;     		
    	}
    	quantity++;
    }
    public void addLast(Item item) {
        // add the item to the end
    	if (item == null) throw new NullPointerException();
    	Node  newNode = new Node();
    	newNode.item = item;
    	newNode.next = null;
    	if (isEmpty()) {
    		last = newNode;
    		first = last;
    	}
    	else {    	
    		Node oldLast = last;
    		last = newNode;
    		oldLast.next = last;
    		last.before = oldLast;
    	}
    	quantity++;
    }
    public Item removeFirst() {
        // remove and return the item from the front
    	if (isEmpty()) throw new NoSuchElementException();
    	Item item = first.item;
    	first = first.next;
    	if (isEmpty()) last = null;
    	else first.before = null;
    	quantity--;
    	return item;
    }
    public Item removeLast() {
        // remove and return the item from the end
    	if (isEmpty()) throw new NoSuchElementException();
    	Item item = last.item;
    	last = last.before;    	
    	if (last == null) first = null;
    	else last.next = null;
    	quantity--;
    	return item;   	
    }
    @Override
	public Iterator <Item> iterator() {
        // return an iterator over items in order from front to end
    	return new NodeIterator();
    }
    public static void main(String[] args) {
        Deque <String> de = new Deque <String>();
        de.addFirst("A");  
        show(de);
        de.addLast("B");
        show(de);
        de.addFirst("C");
        show(de);
        de.addLast("D");
        show(de);
//        System.out.println("\t"+de.removeFirst());
//        show(de);
//        System.out.println("\t"+de.removeFirst());
//        show(de);
//        System.out.println("\t"+de.removeFirst());
//        show(de);
//        System.out.println("\t"+de.removeFirst());
//        show(de);
//        System.out.println("\t"+de.removeLast());
//        show(de);
//        System.out.println("\t"+de.removeLast());
//        show(de);
//        System.out.println("\t"+de.removeLast());
//        show(de);
//        System.out.println("\t"+de.removeLast());
//        show(de);
        System.out.println("\t"+de.removeFirst());
        show(de);
        System.out.println("\t"+de.removeLast());
        show(de);
        System.out.println("\t"+de.removeFirst());
        show(de);
        System.out.println("\t"+de.removeLast());
        show(de);
    }
	private static void show(Deque < String > de) {
		Iterator < String > i = de.iterator();
		System.out.println("-------------------------------");
        while (i.hasNext())
        {
        	String s = i.next();
        	System.out.println(s);
        }
	}
    private class Node {
    	Node next;
    	Node before;
    	Item item;
    }
    
    private class NodeIterator implements Iterator <Item> {
    	
        private Node current = first;
    	
        @Override
		public boolean hasNext() {
            return current != null;
    	}
    	
        @Override
		public void remove() {
            /* not supported */ 
        	throw new UnsupportedOperationException();
    	}
    	
        @Override
		public Item next() {
        	if (!hasNext()) throw new NoSuchElementException();
	        Item item = current.item;
	        current = current.next;
	        return item;
        }
    }
    

}
