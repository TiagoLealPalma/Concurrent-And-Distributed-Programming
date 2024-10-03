package Week_3.Exercise2;

import java.util.Random;

public class TestingThread extends Thread {
    private AbstractQueue queue;
    private int numbersToInsert;
    private Random random = new Random();
    public TestingThread(AbstractQueue queue, int numbersToInsert) {
        this.queue = queue;
        this.numbersToInsert = numbersToInsert;
    }

    public void run() {
        while (!isInterrupted()){
            for (int i = 0; i < numbersToInsert; i++) {
                queue.offer(random.nextInt(1, 10));
            }
            System.out.println(getName() + " terminei!");
            interrupt();
        }
    }

    public static void main(String[] args) {
        AbstractQueue unprotectedQueue = new UnprotectedQueue(30000);
        AbstractQueue AtomicQueue = new AtomicQueue(30000);
        AbstractQueue protectedQueue = new SyncroQueue(30000);


        AbstractQueue queueBeingTested = unprotectedQueue;    // Selecionar qual das queues acima quer testar
                            // I.e "AbstractQueue queue = unprotectedQueue;" -> Testa a proteção atómica da queue


        // Iniciar as Threads
        TestingThread[] threads = new TestingThread[6];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TestingThread(queueBeingTested, 5000);
            threads[i].start();
        }

        // Esperar pelas threads
        try {
            for(TestingThread t : threads) {
                t.join();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // Resultado Final
        System.out.println("Não devem estar presentes zeros na fila\nEstão presentes: " + queueBeingTested.howMany(0) + " zeros.");
    }
}
