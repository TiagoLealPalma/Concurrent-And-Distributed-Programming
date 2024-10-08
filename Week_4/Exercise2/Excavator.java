package Week_4.Exercise2;

import java.util.Random;

public class Excavator extends Thread{
    private Scale scale;
    private Random rand = new Random();

    public Excavator(Scale scale){
        this.scale = scale;
    }

    @Override
    public void run() {

        while(!isInterrupted()) {
            try {
                sleep(500); // Simular a mineração
                scale.addGold((double) ((int) (rand.nextDouble(0, 1) * 1000)) /1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
