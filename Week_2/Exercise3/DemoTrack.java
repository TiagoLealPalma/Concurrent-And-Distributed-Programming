package Week_2.Exercise3;

import javax.swing.JFrame;
import java.util.ArrayList;

public class DemoTrack {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo Track");
        // Iniciar uma lista de carros para manter uma referencia a todos os objetos
        ArrayList<Car> cars = new ArrayList<>();

        // Iniciar os carros
        Car c0 = new Car(0, 100);
        Car c1 = new Car(1, 100);
        Car c2 = new Car(2, 100);
        cars.add(c0);
        cars.add(c1);
        cars.add(c2);

        // Iniciar a pista
        Track track = new Track(cars, 3, 10);

        // Iniciar as threads e passa como argumento os carros
        // (que implementam a classe runanble, indicando assim
        // que as threads devem correr o metodo de run
        // overriden na classe Car)
        Thread t0 = new Thread(c0);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);

        t0.start();
        t1.start();
        t2.start();

        // Adicionar o observer (Classe Track) às classes Observable (Classe Car)
        c1.addObserver(track);
        c2.addObserver(track);
        c0.addObserver(track);

        // Adicionar a track (Graphic Component) ao frame
        frame.add(track);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
