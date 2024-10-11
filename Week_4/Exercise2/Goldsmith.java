package Week_4.Exercise2;

public class Goldsmith extends Thread{
    private Scale scale;
    private double gold = 0;
    private volatile boolean running = true;


    public Goldsmith(Scale scale){
        this.scale = scale;
    }

    @Override
    public void run() {
        System.out.println("Goldsmith started");
        while(running){
            gold += scale.getGold();
            // Este if é necessário para garantir que depois de uma exceção ser apanhada (que limpa a flag interrupted)
            // dentro do metodo não continuar a correr o programa.
            if(running){
                try {
                    sleep(3000);    // Simular que esta a fazer lingote
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        System.out.println( getName() + " interrupted");
    }

    public String getIngots(){
        return "Ingots made: " +  Integer.toString((int) (gold/12.5));
    }

    public void stopRunning(){
        interrupt();
        running = false;
    }
}
