package Week_7.TestPractice;

public class Barrier {
    private int count;

    public Barrier(int count) {
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {
        count--;
        if(count != 0)
            wait();
        else notifyAll();
    }
}
