package Week_2;

public class Exercise1_NameThread extends Thread{
    private int identifier;

    public Exercise1_NameThread(int identifier){
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void run() { // Processo a correr aquando se dá o inicio da Thread
        try {
            for (int i = 0; i < 10; i++) {
                // Verificar se a Thread foi interrompida ou se pode continuar a correr
                if(Thread.interrupted()){
                    System.out.println("Thread "+ getIdentifier() +" interrupted");
                    return;
                }

                System.out.println("Indentifier: " + getIdentifier()); // Dar print ao nome da Thread
                // Adormecer a Thread de 1 a 2 segundos de forma aleatória
                    int sleepTime = ((int)(Math.random()*2) + 1); // Tempo em seconds
                    System.out.println("Sleeping for " + sleepTime + " seconds (" + getIdentifier() + ")");
                    sleep(sleepTime*1000L); // Transformar em milissegundos
            }
        } catch (InterruptedException e) {
            System.out.println("Thread "+ getIdentifier() +" interrupted");
        }
    }











    public static void main(String[] args) {
        // Primeiro Teste
        System.out.println("Primeiro teste:");
        Exercise1_NameThread thread = new Exercise1_NameThread(1);
        Exercise1_NameThread thread2 = new Exercise1_NameThread(2);
        thread.start();
        thread2.start();

        // Esperar que ambas as Threads terminem
        try { // Encapsular com try/catch para que se possa apanhar algum erro, sem que o programa encerre
            thread.join(); // Thread 1 termine
            thread2.join(); // Thread 2 termine
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Teste 1 acabou");

        // Segundo Teste
        System.out.println("Segundo teste:");
        Exercise1_NameThread secondThread = new Exercise1_NameThread(1);
        Exercise1_NameThread secondThread2 = new Exercise1_NameThread(2);
        secondThread.start();
        secondThread2.start();

        // Esperar que ambas as Threads terminem ou que passem 4 segundos
        long maxWaitTime = 4000;
        try {
            long startTime = System.currentTimeMillis();                // Apanhar o tempo de inicio da espera
            secondThread.join(maxWaitTime);                             // Thread 1 termine ou passe 4 segundos
            secondThread.interrupt();

            long timeLeft = maxWaitTime -                               // Subtrair ao tempo maximo de
                    (System.currentTimeMillis() - startTime);           // espera o tempo que a primeira thread demorou
                                                                        // (no caso de ter sido menos de 4 segundos)

            System.out.println("Tempo restante de espera: " + timeLeft);
            if(timeLeft > 0) { // Se ainda houver tempo restante
                secondThread2.join(timeLeft); // Se houver tempo restante, espera que a thread termine ou que esse tempo passe
                secondThread2.interrupt();
            }else secondThread2.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Teste 2 acabou");
    }
}
