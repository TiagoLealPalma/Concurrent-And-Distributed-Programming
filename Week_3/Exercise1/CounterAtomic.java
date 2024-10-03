package Week_3.Exercise1;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomic implements Counter {
    private AtomicInteger number = new AtomicInteger(0);

    @Override
    public int getNumber() {
        int result = number.get();
        return result;
    }

    @Override
    public void increment() {
        number.getAndIncrement();
    }
}
