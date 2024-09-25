package Week_2.Extra1;

public class ThreadB extends Thread{
    private volatile boolean running = true;
    private int numbersGenerated = 0;

    @Override
    public void run() {
        while(running){
            int generatedNumber = (int)(1 + Math.random() * 8);
            System.out.println("Thread B: " + generatedNumber);
            numbersGenerated++;
            try {
                sleep(500);
            }catch(InterruptedException e){
                System.out.println("Thread B interrupted while sleeping.");
                return;
            }
        }
        System.out.println("Thread B stopped.");
    }

    public void stopRunning(){
        running = false;
        interrupt();
    }

    public int getNumbersGenerated() {return numbersGenerated;}
}
