package Week_2.Extra2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Arena extends JComponent implements Observer {
    private Ball ball;

    Arena(Ball ball) {
        this.ball = ball;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.white);
        g.fillOval(ball.getXCoord(), ball.getYCoord(), ball.getSize(), ball.getSize());
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
