package Week_6.Exercise3;

import java.util.concurrent.CyclicBarrier;

public class CountingBarriers {

    public static void main(String[] args){

        CountingThreads[] threadsMid = new CountingThreads[5];
        CountingMilThread[] threads = new CountingMilThread[5];
        CyclicBarrier finishBarrier = new CyclicBarrier(10, new Runnable(){
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    threads[i].interrupt();
                    threadsMid[i].interrupt();
                }
                System.out.println("All threads finished.");
            }
        });
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    threads[i] = new CountingMilThread(finishBarrier);
                    threads[i].start();
                }
            }
        });

        for (int i = 0; i < 5; i++) {
            threadsMid[i] = new CountingThreads(cyclicBarrier, finishBarrier);
            threadsMid[i].start();
        }
    }

}
