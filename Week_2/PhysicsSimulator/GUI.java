package Week_2.PhysicsSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JPanel implements ActionListener {

    private Timer timer;
    private ArrayList<Ball> balls;
    public PhysicsEngine engine;
    private JPanel panel;
    private final int fps = 120;
    private long lastUpdatedTime;
    private Dimension mousePointer;

    public GUI(JPanel panel){
        this.panel = panel;
        balls = new ArrayList<>();
        balls.add(new Ball(100, 123, 10));
        balls.add(new Ball(200, 100, 10));
        balls.add(new Ball(300, 100, 10));
        balls.add(new Ball(400, 100, 10));
        balls.add(new Ball(500, 100, 10));
    }

    public void start(){
        engine = new PhysicsEngine(panel, fps);
        lastUpdatedTime = System.nanoTime();
        timer = new Timer(1000/fps, this);
        timer.start();
    }

    public void setEngineCursor(boolean value, Dimension cursorCoords){
        engine.setMagnetic(value, cursorCoords);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long now = System.nanoTime();
        double deltaTime = (now - lastUpdatedTime) /1e9;
        lastUpdatedTime = now;

        for (Ball ball : balls){
            engine.update(ball, deltaTime);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int ballCounter = 0;

        Graphics2D g2d = (Graphics2D) g;
        // Paint with Centered coords
        for (Ball ball : balls) {
            g.fillOval((int) (ball.getX() - ball.getRadius()), (int) (ball.getY() - ball.getRadius()),
                    (int) ball.getRadius() * 2, (int) ball.getRadius() * 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Ball " + String.valueOf(ballCounter) + ": x = " + String.valueOf((int)ball.getX()) + " y = " + String.valueOf((int)ball.getY()), ballCounter*300 + 50 , 30);
            ballCounter++;
        }
        g.drawString("Height:  " + String.valueOf(panel.getHeight()), 50 , 60);
        g.drawString("Width:  " + String.valueOf(panel.getWidth()), 250 , 60);
        g.drawString("Radius:  " + String.valueOf(balls.get(0).getRadius()), 50 , 90);

    }

    public static void main(String[] args) {
        // Frame
        JFrame frame = new JFrame("Physics Simulator");
        frame.setLayout(new BorderLayout());

        // Panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1300, 700));
        panel.setLayout(new BorderLayout());

        // Button
        JButton startButton = new JButton("Start");

        // Add stuff
        frame.add(startButton, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);  // Add the panel to the frame

        // Adjust sizes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Create the GUI instance before the listener, so we can access its engine
        GUI gui = new GUI(panel);

        // MouseListener added directly to panel
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {

                gui.engine.setMagnetic(true, new Dimension(e.getX(), e.getY()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gui.engine.setMagnetic(false, new Dimension(0, 0));
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        // Listener for the start button (So the GUI can start with proper sizes)
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.add(gui, BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
                gui.start();
            }
        });
    }

}
