package Week_5.Exercise3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table {
    // UI
    private JFrame frame;
    private JTextArea gameLog;
    private JLabel label;
    private JButton stopButton;

    // Game
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private int iteration = 1;
    private String currentCard = null;
    private boolean gameRunning = true;

    public Table() {
        // Initialize the frame
        frame = new JFrame("Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Create the label for the card on the table
        label = new JLabel("Table Card: [ ]", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(label, BorderLayout.NORTH);

        // Create the JTextArea for the game log
        gameLog = new JTextArea(15, 30);
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create the stop button
        stopButton = new JButton("Stop Game");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });
        frame.add(stopButton, BorderLayout.SOUTH);

        // Set visible
        frame.setVisible(true);
    }

    // ------------------------------- Game Logic -----------------------------------------------------------------

    public synchronized boolean setNewCard(String randomCard) {
        while(currentCard != null){
            try {
                wait();
            } catch (InterruptedException e) {return false;}
        }

        iteration++;
        currentPlayer = players.get(iteration%players.size());


        currentCard = randomCard;
        updateTableCard(currentCard);
        notifyAll();
        return gameRunning;
    }


    public synchronized boolean giveGuess(Player player, String guess){
        if(!players.contains(player)) players.add(player);

        while(currentCard == null || !currentPlayer.equals(player)){
            try {
                wait();
            } catch (InterruptedException e) {return false;}
        }

        logGameMessage("["+ player.getName() + ", guess #"+player.getGuess()+"] Player guess = [ "+ guess +" ]  Card = [ "+currentCard+" ]");
        player.incrementGuess();
        if(guess.equals(currentCard)) player.incrementPoints();
        currentCard = null;
        notifyAll();
        return gameRunning;
    }

    private void stopGame() {
        gameRunning = false;
        stopButton.setEnabled(false); // Disable the button after stopping the game
        logGameMessage("Game stopped.");
        for(Player player : players){
            logGameMessage(player.getName() + ": " + player.getPoints() + " points from " + player.getGuess() + " guesses");
        }
    }




    // ------------------------- UI -------------------------------------------------------------------------------

    public void updateTableCard(String card) {
        label.setText("Table Card: [" + card + "]");
    }


    public void logGameMessage(String message) {
        SwingUtilities.invokeLater(()->{
            gameLog.append(message + "\n");
        });
    }


    public static void main(String[] args) {
        // Create and show the UI
        Table table = new Table();
        Distributor d = new Distributor(table);
        Player[] players = new Player[5];

        d.start();
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Player " + String.valueOf(i), table);
            players[i].start();
        }
    }
}
