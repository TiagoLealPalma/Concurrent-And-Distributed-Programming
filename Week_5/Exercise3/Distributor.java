package Week_5.Exercise3;

public class Distributor extends Thread{
    private boolean running = true;
    private final Table table;


    public Distributor(Table table) {
        this.table = table;
    }

    @Override
    public void run() {
        while(running){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
            running = table.setNewCard(Cards.getRandomCard());
        }
    }
}
