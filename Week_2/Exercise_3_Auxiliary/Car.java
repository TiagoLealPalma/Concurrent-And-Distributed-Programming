package Week_2.Exercise_3_Auxiliary;

import java.util.Observable;

import static java.lang.Thread.sleep;

public class Car extends Observable implements Runnable {

    private int id;
    private int limit;
    private int position=0;
    private volatile boolean running = true;

    public Car(int id, int limit) {
        super();
        this.id = id;
        this.limit = limit;
    }

    public String getName(){ return "Car nº" + (id + 1); }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void run() {
        try {
            while (position < limit && running) {
                position++;
                setChanged();
                notifyObservers();
                Thread.sleep((long) (Math.random() * 1000) + 1);
            }
        }catch(InterruptedException e) {
            System.out.println(getName() + "was interrupted");
        }
        System.out.println(getName() + " stopped");
    }

    public void stop() {
        running = false;
    }
}
