package Week_2.Extra2;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

public class Ball extends Observable implements Runnable {
    private int xCoord, yCoord;
    private final int size;
    private final int radius;
    private int speed;
    private final int fps;
    private Random rand = new Random();
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
        radius = size/2;
        fps = 120;
        this.direction = new Vector2(speed, 0);
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

    public void setPosition(Position position){
        xCoord = position.xCoord;
        yCoord = position.yCoord;
    }

    @Override
    public void run() {

        while(running){

            handleWallCollision();
            handlePlayerCollision();

            // Set new position after collisions are checked
            xCoord += (int) direction.getX();
            yCoord += (int) direction.getY();

            // Update GUI
            setChanged();
            notifyObservers();

            // Set refresh rate
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException e) {
                System.out.println("Ball interrupted");
            }
        }

    }

    private void handleWallCollision() {
        // Handles side wall collision
        if(xCoord + radius >= panel.getWidth()){
            leftPlayer.incrementPoints();
            setPosition(new Position(panel.getWidth()/2, panel.getHeight()/2));
            speed = 2;
            direction = new Vector2(speed, calculateDirY((int)rand.nextInt(0,100)));

        }

        if(xCoord - radius <= 0){
            rightPlayer.incrementPoints();
            setPosition(new Position(panel.getWidth()/2, panel.getHeight()/2));
            speed = -2;
            direction = new Vector2(speed, calculateDirY((int)rand.nextInt(0,100)));
        }


        // Handles top and bottom wall collision
        if(yCoord + radius >= panel.getHeight() || yCoord + radius <= 0)
            direction = new Vector2((int)direction.getX(), -(int)(direction.getY()));
    }

    private void handlePlayerCollision() {
        // Get the boundaries from each player
        int leftPlayerBound = 50;
        int rightPlayerBound = panel.getWidth() - 50;
        int speedIncrement = 1;
        if(Math.abs(direction.getX()) > 7 ) speedIncrement = 0;

        // Left player collision (15 pixels x redundancy)
        if(xCoord - radius <= leftPlayerBound && xCoord - radius >= leftPlayerBound - 15){
            if(yCoord + radius >= leftPlayer.getPosition() &&
                    yCoord - radius <= (leftPlayer.getPosition() + leftPlayer.getSize())){
                int impactPos = Math.min(100, Math.max(0, (yCoord - leftPlayer.getPosition())));


                direction = new Vector2(-(int)(direction.getX()-speedIncrement), calculateDirY(impactPos));
            }
        }

        // Right player collision (15 pixels x redundancy)
        if(xCoord + radius >= rightPlayerBound && xCoord + radius <= rightPlayerBound + 15){
            if(yCoord + radius >= rightPlayer.getPosition() &&
                    yCoord - radius <= (rightPlayer.getPosition() + rightPlayer.getSize())){
                int impactPos = Math.min(100, Math.max(0, (yCoord - rightPlayer.getPosition())));
                direction = new Vector2(-(int)(direction.getX()+speedIncrement), calculateDirY(impactPos));
            }
        }
    }

    // Input must be a value clamped between 0 and 100
    private int calculateDirY(int impactPosition){
        return (int)(0.1 * impactPosition - 5);
    }

    private void stop(){
        running = false;
    }
}
