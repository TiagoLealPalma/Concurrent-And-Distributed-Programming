package Week_4.Exercise3;

import java.util.Random;

public class Taker extends Thread{
    private Random rand = new Random();
    private BlockingQueue<Car> q ;
    private volatile boolean running = true;
    private int counter = 0;

    public Taker(BlockingQueue<Car> q){
        this.q = q;
    }

    @Override
    public void run() {
        while(running){
            try {
                sleep(300 + (int)(Math.random() * 500));
                q.take();
                counter++;
            } catch (InterruptedException e) {
            }
        }
    }

    public int stopRunning(){
        running = false;
        interrupt();
        System.out.println("Stopped running. Removed " + counter + " cars.");
        return counter;
    }
}
