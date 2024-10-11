package Week_4.Exercise2;

import java.util.Random;

public class Excavator extends Thread{
    private Scale scale;
    private Random rand = new Random();
    private volatile boolean running = true;
    public Excavator(Scale scale){
        this.scale = scale;
    }

    @Override
    public void run() {
        System.out.println("Excavator started");
        while(running) {
            try {
                sleep(100); // Simular a mineração
                    scale.addGold(rand.nextDouble(0, 1));

            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println( getName() + " interrupted");
    }

    public void stopRunning(){
        interrupt();
        running = false;
    }
}

