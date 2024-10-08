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
                sleep(1000); // Simular a mineração
                scale.addGold(rand.nextDouble(0, 1));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
