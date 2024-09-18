package Week_1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class Exercise1 {
    private JFrame frame;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Exercise1 (){
        frame = new JFrame("Exercise 1");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        addFrameContent();
        frame.pack();
        open();



    }

    private void addFrameContent(){
        frame.setLayout(new GridLayout(4,2));

        JLabel titleLable = new JLabel("title");
        frame.add(titleLable);
        JTextField title = new JTextField("Hello");
        frame.add(title);
        JLabel widthLable = new JLabel("width");
        frame.add(widthLable);
        JTextField width = new JTextField("insert width");
        frame.add(width);
        JLabel heightLable = new JLabel("height");
        frame.add(heightLable);
        JTextField height = new JTextField("insert height");
        frame.add(height);
        JButton updateButton = new JButton("update");
        frame.add(updateButton);
        JCheckBox checkBox = new JCheckBox("center");
        frame.add(checkBox);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setTitle(title.getText());
                frame.setSize(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
                if(checkBox.isSelected()) frame.setLocationRelativeTo(null);
            }
        });

    }

    private void open(){frame.setVisible(true);}

    public static void main(String[] args) {
        Exercise1 test = new Exercise1();
    }
}
