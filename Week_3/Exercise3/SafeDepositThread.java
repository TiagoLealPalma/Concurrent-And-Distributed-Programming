package Week_3.Exercise3;

import java.util.Random;

public class SafeDepositThread extends Thread {
    private double summedAmmount = 0;
    private Account account;
    private volatile boolean running = true;
    private Random rand = new Random();

    public SafeDepositThread(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        while (running) {
            int deposit = rand.nextInt(100);
            account.threadSafeDeposit(deposit);
            summedAmmount += deposit;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " depositou: " + summedAmmount + "paus!");
    }

    public double terminate() {
        running = false;
        return summedAmmount;
    }
}