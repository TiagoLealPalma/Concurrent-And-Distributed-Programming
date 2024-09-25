package Week_2.Extra1;

public class ThreadA extends Thread {
    private int numbersGenerated = 0;
    private volatile boolean running = true;

    @Override
    public void run() {
        while(running){
            int generatedNumber = (int)(1000 + Math.random() * 8999);
            System.out.println("Thread A: " + generatedNumber);
            numbersGenerated++;
        }
        System.out.println("Thread A stopped.");
    }

    public void stopRunning(){
        running = false;
    }

    public int getNumbersGenerated() {return numbersGenerated;}
}
