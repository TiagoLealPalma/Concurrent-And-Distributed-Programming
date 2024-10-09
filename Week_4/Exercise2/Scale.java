package Week_4.Exercise2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            try {
                System.out.println(Thread.currentThread().getName() + ": waiting");
                wait();
            } catch (InterruptedException e) {

                return;     // Caso seja interrompida deve sair do metodo para que possa voltar a sua classe e parar de
                            // forma graciosa
            }
        }
        currentWeight += gold;
        weightDisplayer.setText(String.format("%.3f",currentWeight)); // Formata o double dado para manter apenas 3
                                                                // casas decimais
        notifyAll();
    }

    public synchronized void getGold() {
        try {
            while (currentWeight < maxWeight) {
                System.out.println(Thread.currentThread().getName() + ": waiting");
                wait();
            }
            Thread.sleep(800); // Dar espaço para aparecer o valor maximo e simular o tirar do ouro
        } catch (InterruptedException e) {
            return;
        }
        currentWeight -= 12.5;
        weightDisplayer.setText(String.format("%.3f",currentWeight));
        notifyAll();
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Scale");
        frame.setLayout(new GridLayout(2,1));

        JTextField weightDisplayer = new JTextField(String.valueOf(0));
        weightDisplayer.setEditable(false);
        frame.add(weightDisplayer);
        frame.setVisible(true);

        Scale scale = new Scale(weightDisplayer);
        Excavator excavator = new Excavator(scale);
        excavator.start();
        Goldsmith goldsmith = new Goldsmith(scale);
        goldsmith.start();

        JButton stop = new JButton("Stop");
        frame.add(stop);
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    excavator.interrupt();
                    excavator.join();
                    goldsmith.stopRunning();
                    goldsmith.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(goldsmith.getIngots());
            }
        });

        frame.pack();
        frame.setVisible(true);

    }
}
