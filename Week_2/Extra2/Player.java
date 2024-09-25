package Week_2.Extra2;

import javax.swing.*;
import java.util.Observable;

public class Player extends Observable implements Runnable{
    private int position;
    private JPanel panel;
    private boolean isLeft;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private volatile boolean running = true;

    Player(boolean isLeft, JPanel panel) {
        this.panel = panel;
        this.isLeft = isLeft;
        position = 0;
    }

    public int getPosition() {
        return position;
    }

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
