package Week_4.Extra2;

import Week_4.Exercise3.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class BarberShop {
    private ArrayList<Client> chairs = new ArrayList(3);
    private boolean barberAvailable = true;
    private JTextField textField = new JTextField();

    public BarberShop(JTextField textField) {this.textField  = textField;}

    public synchronized boolean ChairsAvailable(Client client) {
        if(chairs.size() == 3){
            return false;
        }
        chairs.add(client);
        draw();
        notifyAll(); // Notificar o barbeiro
        return true;
    }

    public synchronized boolean waitingForCut(Client client){
        while(!client.getReguado()){
            try {
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }return true;
    }

    public synchronized Client getClient(){
        while(chairs.isEmpty()){
            try {
                System.out.println("Waiting for client to come in");
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        Client client = chairs.remove(0);
        client.setReguado(true);
        notifyAll();
        draw();
        return client;
    }

    private void draw(){
        String d = "  |";
        int drawn = 0;
        for (Client c : chairs) {
            d += "\uD83D\uDD34";
            drawn++;
        }
        for (int i = 0; i < 3-drawn; i++) {
            d+="\uD83D\uDFE2";
        }
        d+="| ";
        String finalD = d;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textField.setText(finalD);
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(2,1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Barber Shop");

        JTextField textField = new JTextField();
        textField.setEditable(false);
        JButton stop = new JButton("Stop");

        frame.add(textField);
        frame.add(stop);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BarberShop barberShop = new BarberShop(textField);
        Client[] clients = new Client[10];
        Barber barber = new Barber(barberShop);
        barber.start();
        for (int i = 0; i < 10; i++) {
            clients[i] = new Client(i, barberShop);
            clients[i].start();
        }

        stop.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                barber.stopRunning();
                try {
                    barber.join();
                    for(Client c : clients){
                        c.stopRunning();
                        c.join();
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }));

    }
}
