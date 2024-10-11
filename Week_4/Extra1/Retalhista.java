package Week_4.Extra1;

// Assume o papel de consumidor

import java.util.Random;

public class Retalhista extends Thread{
    private int id;
    private int ammountBought = 0;
    private Distribuidor distribuidor;
    private volatile boolean running = true;
    private Random rand = new Random();

    public Retalhista(Distribuidor distribuidor, int id) {
        this.distribuidor = distribuidor;
        this.id = id;
    }

    @Override
    public void run() {
        while(running){
            try{
                sleep(300 + rand.nextInt(1500));
                distribuidor.sellProduct();
                ammountBought++;
            }catch (Exception e){}
        }System.out.println("Produtor " + id + ": Comprei " + ammountBought + " itens");
    }

    public void stopRunning(){
        running = false;
        interrupt();
    }
}
