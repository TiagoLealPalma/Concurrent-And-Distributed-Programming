package Week_2.Extra1;

public class ThreadA extends Thread {
    private int numbersGenerated = 0;

    @Override
    public void run() {
        while(!interrupted()){
            double generatedNumber = 1000 + Math.random() * 8999;
            System.out.println(generatedNumber);
            numbersGenerated++;
        }
    }

    public int getNumbersGenerated() {return numbersGenerated;}
}
