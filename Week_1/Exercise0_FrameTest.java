package Week_1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Exercise0_FrameTest {
    private JFrame frame;

    public Exercise0_FrameTest() {
        frame = new JFrame("Test");

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addFrameContent();

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    private void addFrameContent() {
		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(2,2));
        JLabel label = new JLabel("Frame");
        frame.add(label);
        JTextField text = new JTextField("Escreva aqui!");
        frame.add(text);
        JCheckBox check = new JCheckBox("clique aqui para verificar o comportamento");
        frame.add(check);
        JButton button = new JButton("clica aqui!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, check.isSelected() ?
                        "selected" : "not selected");
            }
        });
        frame.add(button);
    }

    public static void main(String[] args) {
        Exercise0_FrameTest window = new Exercise0_FrameTest();
        window.open();
    }
}