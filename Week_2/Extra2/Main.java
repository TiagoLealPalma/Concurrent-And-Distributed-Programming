package Week_2.Extra2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Create the panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.getHSBColor(52,11.8f, 99.6f));
        panel.setPreferredSize(new Dimension(800, 800)); // Use preferred size

        panel.setFocusable(true); // Make sure the panel is focusable
        panel.requestFocusInWindow(); // Request focus so that it can receive key events

        // Create the Button
        JButton button = new JButton("Start");
        frame.add(button, BorderLayout.SOUTH);

        // Sets the frame size before initializing the ball
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Initializes the ball
        Ball ball = new Ball(panel.getWidth() / 2, panel.getHeight() / 2, panel);
        Thread thread = new Thread(ball);

        // Initializes the players
        Player left = new Player(true, panel);
        Player right = new Player(false, panel);
        Thread leftThread = new Thread(left);
        leftThread.start();
        new Thread(right).start();

        Arena arena = new Arena(ball, left, right);
        ball.addObserver(arena);

        panel.add(arena, BorderLayout.CENTER);

        // Make sure to revalidate and repaint the panel after adding the arena
        panel.revalidate();
        panel.repaint();



        /* ----------------------------------------------- Listeners ------------------------------------------------ */

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                thread.start();
                frame.setResizable(false);
                button.setEnabled(false);
            }
        });

        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                ball.setXCoord(panel.getWidth() / 2);
                ball.setYCoord(panel.getHeight() / 2);
                panel.revalidate();
                panel.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            // Starts the movement
            @Override
            public void keyPressed(KeyEvent e) {
                // Upwards movement
                if(e.getKeyCode() == KeyEvent.VK_W) {
                    left.setMoveUp(true);
                }
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    right.setMoveUp(true);
                }

                // Downwards movement
                if(e.getKeyCode() == KeyEvent.VK_S) {
                    left.setMoveDown(true);
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    right.setMoveDown(true);
                }

            }

            // Ends the movement
            @Override
            public void keyReleased(KeyEvent e) {
                // Stop upwards movement
                if(e.getKeyCode() == KeyEvent.VK_W) {
                    left.setMoveUp(false);
                }
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    right.setMoveUp(false);
                }

                // Stop downwards movement
                if(e.getKeyCode() == KeyEvent.VK_S) {
                    left.setMoveDown(false);
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    right.setMoveDown(false);
                }
            }
        });
    }
}
