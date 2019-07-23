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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private final Node head;
    private final Node tail;
    private int size;

    public Deque() {
        head = new Node();
        tail = new Node();
        tail.prev = head;
        head.next = tail;
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;

    }
    public int size() {
        return size;
    }
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node node = new Node();
        node.item = item;
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        size++;
    }
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node node = new Node();
        node.item = item;
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();
        Node node = head.next;
        head.next = node.next;
        node.next.prev = head;
        size--;
        return node.item;
    }
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();
        Node node = tail.prev;
        tail.prev = node.prev;
        node.prev.next = tail;
        size--;
        return node.item;

    }
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node current = head.next;
        public boolean hasNext() {
            return current.item != null;
        }
        public Item next() {
            if (current.item == null)
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) {
        // Deque<String> rq = new Deque<String>();
        // rq.addFirst(null);
        // rq.addFirst("b");
        // rq.addFirst("c");
        // rq.addLast("d");
        // rq.removeFirst();
        // System.out.println(rq.size());
        // rq.removeLast();
        // System.out.println(rq.size());
        // // while (!StdIn.isEmpty())
        // //     rq.addFirst(StdIn.readString());
        // Iterator<String> iterator = rq.iterator();
        // while (iterator.hasNext()) {
        //     System.out.println(iterator.next());
        // }
    }
}
