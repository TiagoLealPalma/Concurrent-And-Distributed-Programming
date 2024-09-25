package Week_2.Exercise3;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class Track extends JComponent implements Observer{
    private ArrayList<Car> cars;
    private int numCars;
    private int numSteps;
    private int[] carPositions;
    private ImageIcon icon = new ImageIcon("Week_2/Exercise3/azul.gif");
    private boolean raceFinished = false;

    public Track(ArrayList<Car> cars, int numCars, int numSteps) {
        this.cars = cars;
        this.numCars = numCars;
        this.numSteps = numSteps;
        carPositions = new int[numCars];
    }

    private void moveCar(int car, int position) {
        if (car < 0 || car >= numCars)
            throw new IllegalArgumentException("invalid car index: " + car);
        if (position < 0 || position > numSteps)
            throw new IllegalArgumentException("invalid position: " + position);
        carPositions[car] = position;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double deltaY = ((double) getHeight()) / (numCars + 1);
        double deltaX = ((double) getWidth() - icon.getIconWidth()) / numSteps;
        for (int i = 0; i < numCars; i++) {
            g.drawLine(0, (int) (deltaY * (i + 1)), getWidth(),
                    (int) (deltaY * (i + 1)));
            g.drawImage(icon.getImage(), (int) (carPositions[i] * deltaX),
                    (int) (deltaY * (i + 1)) - icon.getIconHeight(), null);
        }
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        Car updatedCar=(Car)arg0;
        moveCar(updatedCar.getId(), updatedCar.getPosition());
        // Redraw everything!
        invalidate();

        if(updatedCar.getPosition() == numSteps && !raceFinished) {
            raceFinished = true;
            for(Car car: cars){
                car.stop();
            }
            JOptionPane.showMessageDialog(this, updatedCar.getName() + " has finished the race");
        }
    }
}
