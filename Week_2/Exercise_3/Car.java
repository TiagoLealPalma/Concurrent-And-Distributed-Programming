package Week_2.Exercise_3;

import java.util.Observable;

public class Car extends Observable implements Runnable {
    private int id;
    private int limit;
    private int position=0;
    private int carNumber;

    public String getName(){ return "Carro nº" + carNumber; }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public Car(int carNumber, int id, int limit) {
        super();
        this.carNumber = carNumber;
        this.id = id;
        this.limit = limit;
    }


    @Override
    public void run() {
        try {
            // Loop principal da thread usado para decrementar o valor, com um intervalo aleatorio entre 0 e 1 segundo.
            while (position < limit) {
                position++;

                wait((long) (Math.random() * 1000) + 1);
            }
        }catch(InterruptedException e) {
            System.out.println(getName() + "foi interrompida");
        }
        System.out.println(name + " terminou a corrida"); // Quando sai do loop, anuncia que acabou a corrida.
    }
    }
}
