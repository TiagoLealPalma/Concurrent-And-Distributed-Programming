package Week_4.Exercise1;

public class Eater extends Thread {
    private volatile boolean running = true;
    private Table table;

    public Eater(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while(running){
            try {
                sleep(1000 + (int)(Math.random()*1000));
                table.eatJavali();
            } catch (InterruptedException e) {
            }
        }
    }

    public void finish(){
        System.out.println(getName() + " finished");
        running = false;
        interrupt();
    }
}
