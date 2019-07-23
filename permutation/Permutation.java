/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int max = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());

        Iterator<String> iterator = rq.iterator();
        int i = 0;
        while (i < max && iterator.hasNext()) {
            System.out.println(iterator.next());
            i++;
        }

    }
}