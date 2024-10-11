package Week_4.Extra1;

// Assume o papel de produtor

import java.util.Random;

public class Fornecedor extends Thread{
    private Random rand = new Random();
    private Distribuidor distribuidor;
    private volatile boolean running = true;

    public Fornecedor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    @Override
    public void run() {
        while(running){
            try{
                sleep(rand.nextInt(400));
                distribuidor.addProduct(rand.nextInt(0,9));
            } catch (InterruptedException e) {}
        }System.out.println( "Produtor: Terminei!");
    }


    public void stopRunning(){
        running = false;
        interrupt();
    }
}
