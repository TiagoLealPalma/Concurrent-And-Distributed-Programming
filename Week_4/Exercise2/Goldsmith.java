package Week_4.Exercise2;

public class Goldsmith extends Thread{
    private Scale scale;
    private int ingotsMade = 0;

    public Goldsmith(Scale scale){
        this.scale = scale;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            try {
                scale.getGold();
                sleep(3000);
                ingotsMade++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
