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



        // Paint the middle line
        g.setColor(Color.lightGray);
        g.fillRect(getWidth()/2 - 1, 0 , 2, getHeight());

        // Paint the points of which player
        Font scorefont = new Font("Monospaced", Font.BOLD, 80);
        g.setFont(scorefont);
        g.drawString(String.valueOf(left.getPoints()), getWidth()/4, getHeight()/4);
        g.drawString(String.valueOf(right.getPoints()), getWidth()*3/4, getHeight()/4);

        // Paints the sphere
        g.setColor(Color.white);
        g.fillOval(ball.getXCoord()-(ball.getSize()/2), // Top left corner x position
                ball.getYCoord() - (ball.getSize()/2),      // Top left corner y position
                ball.getSize(), ball.getSize());

        // Paints the rectangles
        g.setColor(Color.white);
        g.fillRect(35, left.getPosition(), 15, left.getSize());

        // - 25 offset & - 25 width
        g.fillRect(getWidth()-50, right.getPosition(), 15, right.getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
