package Week_6.Exercise3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CountingMilThread extends Thread{
    private int count = 0;
    private CyclicBarrier barrier;
    public CountingMilThread(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            count++;
            if(count == 100000) {
                try {
                    System.err.println("Cheguei ao milhão. Acabei!");
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
