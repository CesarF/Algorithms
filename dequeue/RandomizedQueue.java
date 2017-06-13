import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;


/**
 * RandomizedQueue data structure
 * 
 * @author CesarF 5/19/2017
 *
*/
public class RandomizedQueue <Item> implements Iterable <Item> {
	
	private Node first;
	private Node last;
	private int quantity;
	
    public RandomizedQueue() {
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
    public void enqueue(Item item) {
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
  
    public Item dequeue() {
        // remove and return the item from the front
    	if (isEmpty()) throw new NoSuchElementException();
    	int val = StdRandom.uniform(quantity);
    	Item item = null;
    	if (val <= quantity / 2) {
    		Node node = first;
    		int cont = 0;
        	while (node != null && cont <= val) {
        		if(cont == val) {
        			item = node.item;
        			if(node.before == null) {        	
        				first = first.next;
        		    	if(isEmpty()) last = null;
        		    	else first.before = null;
        			}
        			else if(node.next == null) {
        				last = last.before;
        				if(last == null) first = null;
        		    	else last.next = null;
        			}
        			else {
        				Node nxt = node.next;
        				node.before.next = nxt;
        				nxt.before = node.before;
        			}
        			node = null;
        		}
        		else
        			node = node.next;
        		cont ++;
        	}
    	}
    	else {
    		Node node = last;
    		int cont = quantity - 1;
        	while (node != null && cont >= val) {
        		if (cont == val) {
        			item = node.item;
        			if (node.before == null) {
        				first = first.next;
        		    	if (isEmpty()) last = null;
        		    	else first.before = null;
        			}
        			else if (node.next == null) {
        				last = last.before;
        				if (last == null) first = null;
        		    	else last.next = null;
        			}
        			else {
        				Node nxt = node.next;
        				node.before.next = nxt;
        				nxt.before = node.before;
        			}
        			node = null;
        		}
        		else
        			node = node.before;
        		cont --;
        	}
    	}    	
    	quantity--;
    	return item;
    }
    
    public Item sample() {
        // remove and return the item from the front
    	if (isEmpty()) throw new NoSuchElementException();
    	int val = StdRandom.uniform(quantity);
    	Item item = null;
    	if (val <= quantity / 2) {
    		Node node = first;
    		int cont = 0;
        	while (node != null && cont <= val) {
        		if (cont == val) {
        			item = node.item;        			
        		}
        		else
        			node = node.next;
        		cont ++;
        	}
    	}
    	else {
    		Node node = last;
    		int cont = quantity - 1;
        	while (node != null && cont >= val) {
        		if (cont == val) {
        			item = node.item;
        			node = null;
        		}
        		else
        			node = node.before;
        		cont --;
        	}
    	}    	
    	return item;
    }
    
    @Override
	public Iterator <Item> iterator() {
        // return an iterator over items in order from front to end
    	return new NodeIterator();
    }
    public static void main(String[] args) {
    	RandomizedQueue <String> de = new RandomizedQueue <String> ();
        de.enqueue("A");  
        show(de);
        de.enqueue("B");
        show(de);
        de.enqueue("C");
        show(de);
        de.enqueue("D");
        show(de);
        System.out.println("\t"+de.dequeue());
        show(de);
        System.out.println("\t"+de.dequeue());
        show(de);   
        System.out.println("\t"+de.dequeue());
        show(de);
        System.out.println("\t"+de.dequeue());
        show(de);  
    }
    
   	private static void show(RandomizedQueue <String> de) {
   		Iterator <String> i = de.iterator();
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
    	
        private Item[] items;
        private int pos = 0;
        
        @SuppressWarnings("unchecked")
		public NodeIterator() {
			items = (Item[]) new Object[quantity];
			Node n = first;
			for (int i = 0; i < items.length; i++) {
				items[i] = n.item;
				n = n.next;
			}
			StdRandom.shuffle(items);
		}
    	
        @Override
		public boolean hasNext() {
            return pos < items.length;
    	}
    	
        @Override
		public void remove() {
            /* not supported */ 
        	throw new UnsupportedOperationException();
    	}
    	
        @Override
		public Item next() {
        	if (!hasNext()) throw new NoSuchElementException();
	        Item item = items[pos];
	        pos++;
	        return item;
        }
    }
    

}
