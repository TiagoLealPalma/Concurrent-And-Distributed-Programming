package Week_4.Extra2;

import java.util.Random;

public class Client extends Thread{
    private int id;
    private BarberShop barberShop;
    private volatile boolean running = true;
    private Random rand = new Random();
    private boolean reguado = false;

    public Client(int id, BarberShop barberShop) {
        this.id = id;
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
            while(!barberShop.ChairsAvailable(this) && running){ // Tenta entrar na barbearia
                try{
                    sleep(rand.nextInt(3000,10000));
                } catch(InterruptedException e){System.out.println("Cliente nº:" + id +" Não cheguei a entrar na barbearia!");}
            }
            if(!barberShop.waitingForCut(this)){
                System.out.println("Cut was interrupted");
            }
            System.out.println("Cliente nº" + id + ": Estou na régua");

    }

    public boolean getReguado(){
        return reguado;
    }

    public void setReguado(boolean reguado){
        this.reguado = reguado;
    }

    public void stopRunning(){
        running = false;
        interrupt();
    }

    public String getClientId(){
        return String.valueOf(id);
    }
}
