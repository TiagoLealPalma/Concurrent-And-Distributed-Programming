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
    private Player leftPlayer, rightPlayer;

    public Ball(int xCoord, int yCoord, JPanel frame, Player leftPlayer, Player rightPlayer) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.panel = frame;
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
        speed = 2; // Pixels per update (fps * speed = pixels per second)
        size = 20;
        fps = 120;
        this.direction = new Vector2(1, 2).scale(speed);
    }

    private class Position{
        private int xCoord;
        private int yCoord;

        Position(int xCoord, int yCoord) {
            this.xCoord = xCoord;
            this.yCoord = yCoord;
        }

        public Position plus (Vector2 vector){
            xCoord += (int) vector.getX();
            yCoord += (int) vector.getY();
            return this;
        }
    }

    public int getXCoord() {return this.xCoord;}
    public void setXCoord(int xCoord) {this.xCoord = xCoord;}
    public int getYCoord() {return this.yCoord;}
    public void setYCoord(int yCoord) {this.yCoord = yCoord;}
    public int getSize() {return this.size;}

    @Override
    public void run() {

        while(running){
             Position nextPosition = new Position(xCoord + size/2, yCoord + size/2).plus(direction); // Creates the new position so it can
                                                                                    // be tested for collision

            // Handles wall collision
            if(xCoord + direction.getX() + size >= panel.getWidth() || xCoord + direction.getX() <= 0)
                direction = new Vector2(-(int)direction.getX(), (int)(direction.getY()));

            if(yCoord + direction.getY() >= panel.getHeight() || yCoord + direction.getY() <= 0)
                direction = new Vector2((int)direction.getX(), -(int)(direction.getY()));

            // Handle player collision
            int leftPlayerWidth = 50;
            int rightPlayerWidth = panel.getWidth() - 50;

            // Left player collision (15 pixels x redundancy)
            if(nextPosition.xCoord <= leftPlayerWidth && nextPosition.xCoord >= leftPlayerWidth - 15){
                if(nextPosition.yCoord >= leftPlayer.getPosition() &&
                        nextPosition.yCoord <= (leftPlayer.getPosition() + leftPlayer.getSize())){
                    direction = new Vector2(-(int)direction.getX(), (int)(direction.getY()));
                }
            }
            // Right player collision (15 pixels x redundancy)
            if(nextPosition.xCoord >= rightPlayerWidth && nextPosition.xCoord <= rightPlayerWidth + 15){
                if(nextPosition.yCoord >= rightPlayer.getPosition() &&
                        nextPosition.yCoord <= (rightPlayer.getPosition() + rightPlayer.getSize())){
                    direction = new Vector2(-(int)direction.getX(), (int)(direction.getY()));
                }
            }


            // Set new position
            xCoord += (int) direction.getX();
            yCoord += (int) direction.getY();

            // Update GUI
            setChanged();
            notifyObservers();

            // Set FPS
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException e) {
                System.out.println("Ball interrupted");
            }
        }

    }
}
