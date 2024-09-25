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
        g.fillOval(ball.getXCoord(), ball.getYCoord(), ball.getSize(), ball.getSize());

        // Paints the rectangles
        g.fillRect(25, left.getPosition(), 25, 100);
        // - 25 offset & - 25 width
        g.fillRect(getWidth()-50, right.getPosition(), 25, 100);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
        System.out.println(left.getPosition());
    }
}
