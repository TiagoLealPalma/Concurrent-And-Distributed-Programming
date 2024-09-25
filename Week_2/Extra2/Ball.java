package Week_2.Extra2;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class Ball extends Observable implements Runnable {
    private int xCoord;
    private int yCoord;
    private int size;
    private int speed;
    private int fps;
    private Vector2 direction;
    private JPanel panel;
    private volatile boolean running = true;

    public Ball(int xCoord, int yCoord, JPanel frame) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.panel = frame;
        speed = 2; // Pixels per second
        size = 20;
        fps = 120;
        this.direction = new Vector2(1, 2).scale(speed);
    }

    public int getXCoord() {return this.xCoord;}
    public int getYCoord() {return this.yCoord;}
    public int getSize() {return this.size;}

    @Override
    public void run() {
        while(running){
            // Handle collision
            if(xCoord+direction.getX() + size >= panel.getWidth() || xCoord + direction.getX() <= 0)
                direction = new Vector2(-(int)direction.getX(), (int)(direction.getY()));

            if(yCoord + direction.getY() >= panel.getHeight() || yCoord + direction.getY() <= 0)
                direction = new Vector2((int)direction.getX(), -(int)(direction.getY()));

            // Set new position
            xCoord += direction.getX();
            yCoord += direction.getY();

            // Update GUI
            setChanged();
            notifyObservers();

            // Set FPS (16 ms = 60 fps)
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException e) {
                System.out.println("Ball interrupted");
            }
        }

    }
}
