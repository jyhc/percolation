import java.util.Iterator;

import edu.princeton.cs.algs4.In;

public class Deque<Item> implements Iterable<Item> {
	
	private Node first, last;
	private int size = 0;
	
	private class Node {
		Item item;
		Node next;
		Node previous;
	}
	
//	public Deque(){                           // construct an empty deque
//	}
	
	public boolean isEmpty(){                 // is the deque empty?
		return size == 0;
	}
	
	public int size(){// return the number of items on the deque
		return size;
	}
	
	public void addFirst(Item item){       // add the item to the front
		if (item == null) 
			throw new java.lang.NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.previous = null;
		first.next = oldfirst;
		if (isEmpty()) 
			last = first;
		else 
			oldfirst.previous = first;
		size++;
	}
	
	public void addLast(Item item){           // add the item to the end
		if (item == null) 
			throw new java.lang.NullPointerException();
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.previous = oldlast;
		if (isEmpty()) 
			first = last;
		else 
			oldlast.next = last;
		size++;
	}
	
	public Item removeFirst(){                // remove and return the item from the front
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = first.item;
		if (first.next == null){
		    first = null;
		    size--;
		    return item;
		}
		else {
		first = first.next;
		first.previous = null;
		size--;
		return item;
		}
	}	
	
	public Item removeLast(){                 // remove and return the item from the end
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = last.item;
		if (last.previous == null){
		    last = null;
		    size--;
		    return item;
		}
		else {
		last = last.previous;		
		last.next = null;
		size--;
		return item;
		}
	}
	
    public Iterator<Item> iterator(){         // return an iterator over items in order from front to end
        return new ListIterator();		
        }
	
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
		
        public boolean hasNext() { 
            return current.next != null; 
			}
		
		public void remove() { 
			throw new java.lang.UnsupportedOperationException(); 
			}
		
		public Item next()
		{
			if (current.next == null){
				throw new java.util.NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
//	public static void main(String[] args){   // unit testing (optional)
//        int k = Integer.parseInt(args[0]);
//        In in = new In(args[1]);      // input file
//        Deque<String> deck = new Deque<String>();
//        while (!in.isEmpty()) {
//            String item = in.readString();
//            deck.addLast(item);
//            System.out.print(deck.removeFirst()+"\n");            
//        }     
                
        //Iterator<String> i = deck.iterator();
        
//        while (!deck.isEmpty()){            
//            System.out.print(deck.removeFirst()+"\n");            
//        }  
//	}
}

