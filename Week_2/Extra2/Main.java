package Week_2.Extra2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Create the Button
        JButton button = new JButton("Start");
        frame.add(button, BorderLayout.SOUTH);

        // Set the frame size before initializing the ball
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Now initialize the ball with the correct coordinates
        Ball ball = new Ball(panel.getWidth() / 2, panel.getHeight() / 2, panel);
        Thread thread = new Thread(ball);

        Arena arena = new Arena(ball);
        ball.addObserver(arena);

        panel.add(arena, BorderLayout.CENTER);

        // Make sure to revalidate and repaint the panel after adding the arena
        panel.revalidate();
        panel.repaint();

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                thread.start();
            }
        });
    }
}
