package Week_3.Exercise1;

public class CounterProtected implements Counter {
    int number = 0;

    public int getNumber() {
        return number;
    }

    @Override
    public void increment() {
        incrementProtected();
    }

    private synchronized void incrementProtected() { // Impede que seja acessado por varias threads simultaneamente.
        number++;
    }
}
