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

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size;
    public RandomizedQueue() {
       s = (Item[]) new Object[1];
       size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size == s.length)
            resize(2 * size);
        s[size++] = item;

    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    private void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        exch(s, StdRandom.uniform(size), size-1);
        Item item  = s[--size];
        s[size] = null;
        if (size > 0 && size == s.length/4)
            resize(s.length/2);
        return item;
    }
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        return s[StdRandom.uniform(size)];

    }


    public Iterator<Item> iterator() {
            return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        Item[] copy = initial();
        private int count = size;
        private Item current = randomGet();

        private Item[] initial() {
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copy[i] = s[i];
            }
            return copy;
        }
        private Item randomGet() {
            if (count == 0)
                return null;
            exch(copy, StdRandom.uniform(count), count-1);
            Item item  = copy[count-1];
            copy[count-1] = null;
            return item;
        }
        public boolean hasNext() {
            return count > 0;
        }
        public Item next() {
            if (count == 0)
                throw new NoSuchElementException();
            Item item = current;
            count--;
            if (count > 0)
                current = randomGet();
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {
        // you can call it  in permutation.java
    }
}
