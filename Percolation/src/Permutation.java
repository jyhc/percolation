import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
   public static void main(String[] args){
       int k = Integer.parseInt(args[0]);
       //In in = new In(args[2]);      // input file
       RandomizedQueue<String> bag = new RandomizedQueue<String>();
       while (!StdIn.isEmpty()) {
           String item = StdIn.readString();
           bag.enqueue(item);
           //System.out.print("test\n");
       }
       Iterator<String> i = bag.iterator();
       for (int j = 0; j < k ; j++){
           String s = i.next();
           System.out.print(s+"\n");
       }
       //while (i.hasNext())
       //{        
       //}       
   }
}
