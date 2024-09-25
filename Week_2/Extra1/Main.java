package Week_2.Extra1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Threads A & B");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        JButton stopButton = new JButton("Stop");

        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();

        threadA.start();
        threadB.start();


        stopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(false);
                stopButton.setText("Stoped");
                threadA.stopRunning();
                threadB.stopRunning();

                try {
                    threadA.join(); // Garante que a main Thread não prossegue sem a confirmação de que as threads estão paradas
                    threadB.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Thread A generated: " + threadA.getNumbersGenerated() + " numbers.");
                System.out.println("Thread B generated: " + threadB.getNumbersGenerated() + " numbers.");
            }
        });

        frame.add(stopButton);
        frame.setVisible(true);
    }
}
