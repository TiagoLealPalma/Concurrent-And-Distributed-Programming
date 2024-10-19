package Week_5.Exercise3;

public class Player extends Thread{
    private int points = 0;
    private final Table table;
    private int guess = 1;
    private volatile boolean running = true;

    public Player(String name, Table table){
        super(name);
        this.table = table;
    }

    public void run(){
        while(running){
            running = table.giveGuess(this, Cards.getRandomCard());
        }
    }


    public int getPoints(){
        return points;
    }

    public void incrementPoints(){
        points++;
    }

    public int getGuess(){
        return guess;
    }

    public void incrementGuess(){
        guess++;
    }

}
