package Week_4.Exercise3;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class BlockingQueue<T> {
    private Queue<T> queue = new LinkedList<>();
    private int capacity = -1;

    public BlockingQueue(int capacity) {
        if(capacity < 0)
            throw new IllegalArgumentException();

        this.capacity = capacity;
    }

    public BlockingQueue(){}

    public synchronized void put (T t) throws InterruptedException {
        while (size() == capacity) {
            System.out.println("Queue is full: " + Thread.currentThread().getName() + " is waiting");
            wait();
        }
        queue.add(t);
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (size() == 0) {
            System.out.println("Queue is empty: " + Thread.currentThread().getName() + " is waiting");
            wait();
        }

        T t = queue.poll();
        if(capacity != -1) notifyAll();
        return t;
    }

    public int size(){
        return queue.size();
    }

    public synchronized void clear(){
        queue.clear();
    }


    public static void main(String[] args) {
        BlockingQueue<Car> q = new BlockingQueue<Car>(40);
        int clienteToAdd = 10;
        int takersToAdd = 5;
        Cliente[] cars = new Cliente[clienteToAdd];
        Taker[] takers = new Taker[takersToAdd];

        int sumAdd = 0;
        int sumRemove = 0;


        for (int i = 0; i < clienteToAdd; i++) {
            cars[i] = new Cliente(q);
            cars[i].start();
        }

        for (int i = 0; i < takersToAdd; i++) {
            takers[i] = new Taker(q);
            takers[i].start();
        }

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (Cliente c : cars) {
            sumAdd += c.stopRunning();
        }
        for (Taker t : takers) {
            sumRemove += t.stopRunning();
        }



        System.out.println("Total cars added: " + sumAdd);
        System.out.println("Total cars removed: " + sumRemove);
        System.out.println("Total cars in queue (Teoric value): " + (sumAdd - sumRemove));
        System.out.println("Queue size: " + q.size());
    }
}
