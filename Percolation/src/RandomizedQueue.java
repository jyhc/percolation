import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
    
    private int index = 0;
    private int size = 1;
    private Item[] queue = (Item[]) new Object[size];
    
//    public RandomizedQueue(){                 // construct an empty randomized queue
//                  
//    }
    
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < index; i++)
            copy[i] = queue[i];
        queue = copy;
        size = capacity;
    }
    
    public boolean isEmpty(){                 // is the queue empty?
        return index == 0;
    }
	public int size(){                        // return the number of items on the queue
	    return index;
	}
	
    public void enqueue(Item item){           // add the item
        if (item == null) 
            throw new java.lang.NullPointerException();
        if (index == size) 
            resize(2 * size);
        queue[index++] = item;   
    }
    
    public Item dequeue(){                    // remove and return a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        StdRandom.shuffle(queue, 0, index);
        Item item = queue[--index];
        queue[index] = null;
        if (index > 0 && index == size/4) resize(size/2);
        return item;        
    }
    
    public Item sample(){                     // return (but do not remove) a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int random = StdRandom.uniform(0,index-1);        
        return queue[random];
    }
    
    public Iterator<Item> iterator(){         // return an independent iterator over items in random order
        
        return new ListIterator(queue);      
        }
    
    private class ListIterator implements Iterator<Item> {
        
        private int current = 0;
        Item[] temp = (Item[]) new Object[index+1];
        
        public ListIterator(Item[] queue){
            for (int i = 0; i < index; i++)
                temp[i] = queue[i];
            StdRandom.shuffle(temp, 0, index);
            //temp[index] = null;
        }
        public boolean hasNext() { 
            return temp[current] != null; 
            }
        
        public void remove() { 
            throw new java.lang.UnsupportedOperationException(); 
            }
        
        public Item next()
        {
            if (temp[current] == null){
                throw new java.util.NoSuchElementException();
            }
            Item item = temp[current];
            current++;
            return item;
        }
    }

//    public static void main(String[] args){   // unit testing (optional)
//       
//        int k = Integer.parseInt(args[0]);
//        In in = new In(args[1]);      // input file
//        RandomizedQueue<String> bag = new RandomizedQueue<String>();
//        while (!in.isEmpty()) {
//            String item = in.readString();
//            bag.enqueue(item);
//            System.out.print(bag.dequeue()+"\n");            
//        }     
                
//        Iterator<String> i = bag.iterator();
        
//        while (i.hasNext()){
//            String s = i.next();
//            System.out.print(bag.dequeue()+"\n");
//        }        
        
//    }
}