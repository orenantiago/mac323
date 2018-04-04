import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{

    private class Node {
        private Item value;
        private Node before;
        private Node next;

        public Node(Item value) {
            if(value == null)
                throw new IllegalArgumentException("invalid item");

            this.value = value;
            before = null;
            next = null;
        }
    }

    public Node first;
    private Node last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if(isEmpty()) {
            initializeDeque(item);
        }
        else {
            Node newNode = new Node(item);
            newNode.next = first;
            first.before = newNode;
            first = newNode;
            size++;
        }
    }

    public void addLast(Item item) {
        if(isEmpty()) {
            initializeDeque(item);
        }
        else {
            Node newLast = new Node(item);
            newLast.before = last;
            last.next = newLast;
            last = newLast;
            size++;
        }
    }
    private void initializeDeque(Item item) {
        this.first = new Node(item);
        this.last = first;
        size++;
    }

    public Item removeFirst() {
        validateSize();
        size--;
        Node oldFirst = this.first;
        first = oldFirst.next;
        if(size == 0) {
            first = null;
        }
        return oldFirst.value;
    }

    public Item removeLast() {
        validateSize();
        size--;

        Node oldLast = this.last;
        last = oldLast.before;
        if(size == 0) {
            first = null;
        }
        return oldLast.value;
    }

    private void validateSize() {
        if(size == 0) {
            throw new NoSuchElementException("empty deque");
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{
        private Node current = first;
        DequeIterator() {
            current = first;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(current == null) {
                throw new NoSuchElementException("empty iterator");
            }
            Item value = current.value;
            current = current.next;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
