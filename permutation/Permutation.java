import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int repetitions = Integer.valueOf(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        Iterator<String> iterator = queue.iterator();
        for(int i = 0; i < repetitions; i++) {
            StdOut.println(iterator.next());
        }
    }
}
