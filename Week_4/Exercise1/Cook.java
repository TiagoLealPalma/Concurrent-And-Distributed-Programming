package Week_4.Exercise1;

public class Cook extends Thread {
    private volatile boolean running = true;
    private Table table;

    public Cook(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while(running){
            try {
                sleep(700 + (int)(Math.random() * 1000));
                table.setJavaliOnTable(getName());
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
