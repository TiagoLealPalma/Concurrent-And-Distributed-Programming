package Week_4.Exercise3;

import java.util.Random;

public class Cliente extends Thread{
    private Random rand = new Random();
    private BlockingQueue<Car> q ;
    private volatile boolean running = true;
    private int counter = 0;

    public Cliente(BlockingQueue<Car> q){
        this.q = q;
    }

    @Override
    public void run() {
        while(running){
            try {
                sleep(100 + (int)(Math.random() * 500));
                q.put(new Car(rand.nextInt(100)));
                counter++;
            } catch (InterruptedException e) {
            }
        }
    }

    public int stopRunning(){
        running = false;
        interrupt();
        System.out.println("Stopped running. Added " + counter + " cars.");
        return counter;
    }
}
