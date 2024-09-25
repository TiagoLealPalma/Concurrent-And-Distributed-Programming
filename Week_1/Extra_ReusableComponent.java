package Week_1;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Extra_ReusableComponent {
    private JFrame frame;
    private boolean crossTurn;
    

    public Extra_ReusableComponent(String name, int rows, int cols) {
        crossTurn = true;

        frame = new JFrame(name);
        frame.setSize(cols*50 + cols, rows*50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(rows, cols));
       

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JLabel label = new JLabel();
                Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
                label.setBorder(border);
                label.setSize(new Dimension(50,50));
                label.setFont(new Font("Arial", Font.BOLD, 42));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                frame.add(label);

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(crossTurn) label.setText("X");
                        else label.setText("O");

                        crossTurn = !crossTurn;
                    }
                });
            }
        }
    }

    public void open(){
        frame.setVisible(true);
    }


    public static void main(String[] args) {

        Extra_ReusableComponent test = new Extra_ReusableComponent("teste", 3,3);
        test.open();
    }
}
