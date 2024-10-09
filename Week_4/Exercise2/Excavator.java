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
                    scale.addGold((double) ((int) (rand.nextDouble(0, 1) * 1000)) /1000);

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

