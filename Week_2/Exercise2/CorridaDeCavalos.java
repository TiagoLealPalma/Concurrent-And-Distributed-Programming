package Week_2.Exercise2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CorridaDeCavalos {
    private JFrame frame;
    private Cavalo cavalo1, cavalo2, cavalo3;

    private class Cavalo extends Thread{
        private int distance = 30; // Distancia até ao final da corrida
        private JTextField textField;
        private String name;

        public Cavalo(JTextField textField, String name){
            this.textField = textField;
            this.name = name;
        }

        public void run(){
            try {
                // Loop principal da thread usado para decrementar o valor, com um intervalo aleatorio entre 0 e 1 segundo.
                while (distance > 0) {
                    distance--;
                    textField.setText(String.valueOf(distance));
                    sleep((long) (Math.random() * 1000) + 1);
                }
            }catch(InterruptedException e) {
                System.out.println(getName() + "foi interrompida");
            }
            System.out.println(name + " terminou a corrida"); // Quando sai do loop, anuncia que acabou a corrida.
        }
    }

    public CorridaDeCavalos(){
        frame = new JFrame("Corrida de cavalos");
        frame.setSize(200,100);
        frame.setLocationRelativeTo(null); // Centra a janela
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel textPanel = new JPanel();
        JTextField firstSlot = new JTextField("30");
        firstSlot.setEditable(false);
        JTextField secondSlot = new JTextField("30");
        secondSlot.setEditable(false);
        JTextField thirdSlot = new JTextField("30");
        thirdSlot.setEditable(false);

        cavalo1 = new Cavalo(firstSlot, "Cavalo 1");
        cavalo2 = new Cavalo(secondSlot, "Cavalo 2");
        cavalo3 = new Cavalo(thirdSlot, "Cavalo 3" );

        textPanel.setLayout(new FlowLayout());
        textPanel.add(firstSlot);
        textPanel.add(secondSlot);
        textPanel.add(thirdSlot);

        frame.setLayout(new BorderLayout());
        frame.add(textPanel, BorderLayout.CENTER);
        JButton startButton = new JButton("Start");
        frame.add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cavalo1.start();
                cavalo2.start();
                cavalo3.start();
            }
        });

        open();
    }

    private void open(){
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CorridaDeCavalos();
    }
}
