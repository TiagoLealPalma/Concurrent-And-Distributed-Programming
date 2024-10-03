package Week_3.Exercise1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterWithLock implements Counter {
    private int number;
    private Lock lock = new ReentrantLock();


    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void increment() {
        lock.lock();
        try{
            number++;
        }finally {
            lock.unlock();
        }
    }
}
