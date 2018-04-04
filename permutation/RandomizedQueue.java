import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items = null;
    private int size;
    private int index;
    private Random random;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
        index = 0;
        random = new Random();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if(item == null)
            throw new IllegalArgumentException("invalid item");

        if(size < items.length) {
            items[size++] = item;
        }
        else {
            resize();
            enqueue(item);
        }
    }
    private void resize() {
        Item[] newArray = (Item[]) new Object[2 * items.length];
        for(int i = 0; i < items.length; i++) {
            newArray[i] = items[i];
        }
        items = newArray;
    }

    public Item sample() {
        validateSize();
        int index = random.nextInt(size);
        return items[index];
    }

    public Item dequeue() {
        validateSize();
        int index = random.nextInt(size);
        if(index != --size) {
            exchange(index, size);
        }
        Item item = items[size];
        items[size] = null;
        return item;
    }

    private void validateSize(){
        if(size == 0)
            throw new NoSuchElementException("empty RandomizedQueue");

    }

    private void exchange(int i, int j) {
        Item aux = items[i];
        items[i] = items[j];
        items[j] = aux;
    }



    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item>{
        Random random;
        Item[] array = null;
        int length;
        QueueIterator() {
            length = size;
            random = new Random();
            array = (Item[]) new Object[size];

            for(int i = 0; i < size; i++) {
                array[i] = items[i];
            }
        }


        @Override
        public boolean hasNext() {
            return length != 0;
        }

        @Override
        public Item next() {
            if(length == 0){
                throw new NoSuchElementException("empty iterator");
            }
            int index = random.nextInt(length--);
            if(index != length) {
                Item aux = array[index];
                array[index] = array[length];
                array[length] = aux;
            }
            Item value = array[length];
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
