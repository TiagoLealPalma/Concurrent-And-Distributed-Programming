package Week_4.Exercise2;

public class Goldsmith extends Thread{
    private Scale scale;
    private int ingotsMade = 0;
    private volatile boolean running = true;

    public Goldsmith(Scale scale){
        this.scale = scale;
    }

    @Override
    public void run() {
        System.out.println("Goldsmith started");
        while(running){
            scale.getGold();
            // Este if é necessário para garantir que depois de uma exceção ser apanhada (que limpa a flag interrupted)
            // dentro do metodo não continuar a correr o programa.
            if(running){
                try {
                    sleep(3000);
                    ingotsMade++;
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        System.out.println( getName() + " interrupted");
    }

    public String getIngots(){
        return "Ingots made: " +  Integer.toString(ingotsMade);
    }

    public void stopRunning(){
        interrupt();
        running = false;
    }
}
