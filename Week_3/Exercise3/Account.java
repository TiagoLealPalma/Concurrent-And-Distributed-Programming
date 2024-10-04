package Week_3.Exercise3;

public class Account {
    private double balance;

    public void deposit(double ammount){
        balance += ammount;
    }

    public double getBalance(){
        return balance;
    }

    public synchronized void threadSafeDeposit(double ammount){
        balance += ammount;
    }
}
