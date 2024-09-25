package Week_1.Exercise1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class Window {
    private JFrame frame;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Window(){
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
                try {
                    frame.setTitle(title.getText());
                    frame.setSize(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid inputs");
                }
                if(checkBox.isSelected()) frame.setLocationRelativeTo(null);
            }
        });

    }

    private void open(){frame.setVisible(true);}

    public static void main(String[] args) {
        Window test = new Window();
    }
}
