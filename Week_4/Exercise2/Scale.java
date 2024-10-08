package Week_4.Exercise2;

import javax.swing.*;
import java.util.Scanner;

public class Scale {
    private double maxWeight = 12.5;
    private double currentWeight = 0;
    private JTextField weightDisplayer = new JTextField(String.valueOf(0));
    private JTextField ingotsMade = new JTextField(String.valueOf(0));


    public Scale(JTextField weightDisplayer) {
        this.weightDisplayer = weightDisplayer;
    }

    public synchronized void addGold(double gold) throws InterruptedException {
        while (currentWeight >= maxWeight) {
            wait();
        }
        currentWeight += gold;
        weightDisplayer.setText(String.valueOf(currentWeight));
        notifyAll();
    }

    public synchronized void getGold() throws InterruptedException {
        while (currentWeight < maxWeight) {
            wait();
        }
        currentWeight = 0;
        weightDisplayer.setText(String.valueOf(currentWeight));
        notifyAll();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Scale");
    }
}
