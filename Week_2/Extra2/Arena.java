package Week_2.Extra2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Arena extends JComponent implements Observer {
    private Ball ball;
    private Player left;
    private Player right;

    Arena(Ball ball, Player left, Player right) {

        this.ball = ball;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        // Paints the sphere
        g.setColor(Color.white);
        g.fillOval(ball.getXCoord()-(ball.getSize()/2), // Top left corner x position
                ball.getYCoord() - (ball.getSize()/2),      // Top left corner y position
                ball.getSize(), ball.getSize());

        // g.drawLine(getWidth()-50, 0 , getWidth()-50, getHeight());
        // g.drawLine(0, getHeight()-1 , getWidth(), getHeight()-1);

        // Paints the rectangles
        g.fillRect(25, left.getPosition(), 25, left.getSize());
        // - 25 offset & - 25 width
        g.fillRect(getWidth()-50, right.getPosition(), 25, right.getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
