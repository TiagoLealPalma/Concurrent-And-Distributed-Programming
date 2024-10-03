package Week_3.Exercise1;

public class IncrementerThread extends Thread {
    private Counter counter;
    public IncrementerThread(Counter counter){
        this.counter = counter;
    }
    public void run(){
        while(!isInterrupted()) {
            for (int i = 0; i < 100000; i++) { // Em vez de 1000, será 100 000 para
                                                // ilustrar corretamente o problema.
                counter.increment();
            }
            System.out.println(getName() + " finished");
            interrupt();
            return;
        }
        System.out.println(getName() + " interrupted");
    }


    public static void main(String[] args) {
        // A interface criada não é necessária, serve apenas para facilitar o testar desta classe main.

        CounterNonProtected unprotected = new CounterNonProtected();
        CounterProtected syncronized = new CounterProtected();
        CounterWithLock locked = new CounterWithLock();
        CounterAtomic atomic = new CounterAtomic();

        Counter counterBeingTested = unprotected; // Selecionar qual dos counters acima quer testar
                                // I.e "Counter counter = locked;" -> testa a proteção por locks explicitos

        // Inicializa-se a thread atribuindo o counter que se pretende estudar, escolhido acima, como argumento.
        IncrementerThread[] threads = new IncrementerThread[4];
        for(int i = 0; i < threads.length; i++){
            threads[i] = new IncrementerThread(counterBeingTested);
            threads[i].start();
        }
        try {
            for(IncrementerThread thread : threads){
                thread.join();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Valor teórico esperado = 400000 \nValor obtido = " + counterBeingTested.getNumber());
    }
}
