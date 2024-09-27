package Week_2.Extra2;

import javax.swing.*;
import java.util.Observable;

public class Player extends Observable implements Runnable{
    private int position;
    private int size;
    private int points = 0;
    private JPanel panel;
    private boolean isLeft;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private volatile boolean running = true;

    Player(boolean isLeft, JPanel panel) {
        this.panel = panel;
        this.isLeft = isLeft;
        size = 100;
        position = panel.getHeight()/2;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void incrementPoints() {
        this.points++;
    }

    public int getSize() { return size; }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) { this.position = position; }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    @Override
    public void run() {
        while(running){
            if(moveUp){
                position -= 5;
                setChanged();
                notifyObservers();
            }

            if(moveDown){
                position += 5;
                setChanged();
                notifyObservers();
            }

            try {
                Thread.sleep(1000/120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
