package Week_3.Exercise1;


public class CounterNonProtected implements Counter {
    int number = 0;

    public int getNumber() {
        return number;
    }

    @Override
    public void increment() {
        number++;
    }
}
