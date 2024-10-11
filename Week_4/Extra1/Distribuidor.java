package Week_4.Extra1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

// Assume o papel de recurso partilhado

public class Distribuidor {
    LinkedList<Integer> list = new LinkedList<>();
    JTextField text = new JTextField();
    int size = 0;

    public Distribuidor(JTextField text) {
        this.text = text;
    }

    public synchronized void addProduct(int product){
        if(product < 0 || product > 9) throw new IllegalArgumentException();
        list.add(product);
        size++;
        text.setText(list.toString());
        notifyAll();
    }

    public synchronized void sellProduct() {
        while(size == 0){

            System.out.println(Thread.currentThread().getName() + " waiting for product...");
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }

        }
        System.out.println(Thread.currentThread().getName() + " finished waiting for product.");
       list.removeFirst();
       size--;
        updateList();
    }

    public synchronized void clear(){
        list.clear();
    }
    // Para atualizar o textField é OBRIGATORIO usar o invokeLater(), dado que os Swings Components, não sao thread safe.
    // Assim, sempre que se atualiza algo componente deverá ser feito através da AWT Event Dispatch Thread
    public void updateList(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                text.setText(list.toString());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Distribuidor");
        JTextField list = new JTextField();
        JButton button = new JButton("Stop");
        Distribuidor distribuidor = new Distribuidor(list);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1));
        list.setPreferredSize(new Dimension(400,100));
        button.setSize(new Dimension(400,100));
        frame.add(list);
        frame.add(button);

        // Instanciar as threads
        Fornecedor fornecedor = new Fornecedor(distribuidor);
        Retalhista[] retail = new Retalhista[5];
        fornecedor.start();
        for (int i = 0; i < retail.length; i++) {
            retail[i] = new Retalhista(distribuidor, i+1);
            retail[i].start();
        }


        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fornecedor.stopRunning();
                try {
                    fornecedor.join();
                    for (Retalhista retalhista : retail) {
                        retalhista.stopRunning();
                        retalhista.join();
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
