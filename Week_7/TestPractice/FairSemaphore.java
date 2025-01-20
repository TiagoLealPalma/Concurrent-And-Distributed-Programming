package Week_7.TestPractice;

import java.util.ArrayList;

public class FairSemaphore {
    private int licenses = 0;
    private ArrayList<Thread> threads;

    public FairSemaphore(int numLicenses){
        this.licenses = numLicenses;
        threads = new ArrayList<>();
    }

    public synchronized void acquire() throws InterruptedException {
        while(licenses <= 0 || Thread.currentThread() != threads.getFirst()){
            if(!threads.contains(Thread.currentThread())) threads.add(Thread.currentThread());
            wait();
        }
        if(threads.contains(Thread.currentThread())) threads.remove(threads.indexOf(Thread.currentThread()));
        licenses--;
    }

    public synchronized void release() throws InterruptedException {
        licenses++;
        notifyAll();
    }
}
