package Week_6.Exercise3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CountingThreads extends Thread{
    private int count = 0;
    private CyclicBarrier midBarrier;
    private CyclicBarrier finnishBarrier;


    public CountingThreads(CyclicBarrier midBarrier, CyclicBarrier finishBarrier){
        this.midBarrier=midBarrier;
        this.finnishBarrier=finishBarrier;
    }
    @Override
    public void run(){
        while(!isInterrupted()){
            count++;
            //System.out.println(Thread.currentThread().getName() + " " +count);
            if(count == 1000) {
                try {
                    System.err.println("Cheguei aos mil. Vou esperar!");
                    midBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
            if(count==100000) {
                try {
                    System.err.println("Cheguei ao milhão.");
                    finnishBarrier.await();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
