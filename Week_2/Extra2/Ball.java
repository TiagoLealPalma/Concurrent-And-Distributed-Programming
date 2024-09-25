package Week_2.Extra2;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class Ball extends Observable implements Runnable {
    private int xCoord;
    private int yCoord;
    private int speed;
    private Vector2 direction;
    private volatile boolean running;

    public Ball(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.speed = 100; // Pixels per second
        this.direction = new Vector2(1, 2).scale(speed);
    }

    public int getXCoord() {return this.xCoord;}
    public int getYCoord() {return this.yCoord;}

    private check

    @Override
    public void run() {
        while(running){

        }

    }
}
