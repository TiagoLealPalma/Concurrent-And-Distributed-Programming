package Week_7.TestPractice;

public class Semaphore {
    private int licenses;

    public Semaphore(int numOfLicenses){
        licenses = numOfLicenses;
    }

    public synchronized void acquire() throws InterruptedException {
        while(licenses == 0)
            wait();
        licenses--;
    }

    public synchronized void release() throws InterruptedException {
        licenses++;
        notifyAll();
    }
}
