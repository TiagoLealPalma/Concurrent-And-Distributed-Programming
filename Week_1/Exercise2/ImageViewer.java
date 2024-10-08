package Week_1.Exercise2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

public class ImageViewer {
    private String path;
    private File[] files;
    private JFrame frame;
    private JLabel currentImageName;
    private JLabel currentImage;
    private int currentFileIndex = 0;


    public ImageViewer(String path) {
        this.path = path;
        files = new File(path).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });


        frame = new JFrame("images");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        currentImageName = new JLabel(files[0].getName()); // Nome da primeira imagem
        currentImage = new JLabel();
        currentImage.setIcon(new ImageIcon(files[0].getAbsolutePath()));

        addFrameContent();
        frame.setVisible(true);
        frame.pack();
    }

    private void addFrameContent() {

        // Labels
        frame.add(currentImageName, BorderLayout.NORTH);
        frame.add(currentImage, BorderLayout.CENTER);

        // Buttons
        JButton leftButton = new JButton("<");
        frame.add(leftButton, BorderLayout.WEST);
        JButton rightButton = new JButton(">");
        frame.add(rightButton, BorderLayout.EAST);
        JButton updateButton = new JButton("Update");
        frame.add(updateButton, BorderLayout.SOUTH);

        // Event Listeners

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!rightButton.isEnabled()) {
                    rightButton.setEnabled(true); // Ativar botão se estiver desativado
                    currentImage.setText("");
                    currentFileIndex++;

                }
                // Verificar se existem imagens por mostrar
                if(currentFileIndex != 0){
                    currentFileIndex--;
                    displayImageAt(currentFileIndex);
                }else {
                    currentImage.setIcon(null);
                    currentImageName.setText("Fim das imagens :(");
                    currentImage.setText("Fim das imagens :(");
                    leftButton.setEnabled(false);
                }
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!leftButton.isEnabled()) {
                    leftButton.setEnabled(true); // Ativar botão se estiver desativado
                    currentImage.setText("");
                    currentFileIndex--;
                }
                // Verificar se existem imagens por mostrar
                if(currentFileIndex < (files.length - 1)){
                    currentFileIndex++;
                    displayImageAt(currentFileIndex);
                }else {
                    currentImage.setIcon(null);
                    currentImageName.setText("Fim das imagens :(");
                    currentImage.setText("Fim das imagens :(");
                    rightButton.setEnabled(false);
                }
            }
        });

        // Começar nova instância para atualizar as imagens
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageViewer newTest = new ImageViewer(path);
            }
        });
    }

    private void displayImageAt(int index){
        currentImageName.setText(files[index].getName());
        currentImage.setIcon(new ImageIcon(files[index].getAbsolutePath()));


    }


    // Run Configurations: Meter Path da diretoria Images (Path: "Week_1/Exercise2/Images")
    public static void main(String[] args) {

        ImageViewer test = new ImageViewer(args[0]);


    }
}
