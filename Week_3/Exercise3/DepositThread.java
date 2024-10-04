package Week_3.Exercise3;

import java.util.Random;
import java.util.Scanner;

public class DepositThread extends Thread{
    private double summedAmmount = 0;
    private Account account;
    private volatile boolean running = true;
    private Random rand = new Random();

    public DepositThread(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        while(running){
            int deposit = rand.nextInt(100);
            account.deposit(deposit);
            summedAmmount += deposit;
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " depositou: " + summedAmmount + "paus!");
    }

    public double terminate(){
        running = false;
        return summedAmmount;
    }


    public static void main(String[] args) {
        Account account = new Account();
        DepositThread[] array = new DepositThread[10];
        double summedAmmount = 0;

        for (int i = 0; i < array.length; i++) {
            array[i] = new DepositThread(account);
            array[i].start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (DepositThread thread : array) {
            summedAmmount += thread.terminate();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Dinheiro Depositado pelas threads: " + summedAmmount );
        System.out.println("Dinheiro na conta: " + account.getBalance() );
        System.out.println("Dinheiro perdido pela má programação do sistema: " + (summedAmmount - account.getBalance()));
        System.out.println("Equivalente a " + Math.floor((summedAmmount - account.getBalance())/1.2) + " pasteis de nata perdidos :(");

        System.out.println("Digite 'arranje o sistema!' para que o sistema seja arranjado:");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if(line.equals("arranje o sistema!")){
            Account account2 = new Account();
            DepositThread[] array2 = new DepositThread[10];
            double summedAmmount2 = 0;

            for (int i = 0; i < array2.length; i++) {
                array2[i] = new DepositThread(account2);
                array2[i].start();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (DepositThread thread : array2) {
                summedAmmount2 += thread.terminate();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Dinheiro Depositado pelas threads: " + summedAmmount );
            System.out.println("Dinheiro na conta: " + account.getBalance() );
            System.out.println("Dinheiro perdido pela má programação do sistema: " + (summedAmmount - account.getBalance()));
            System.out.println("Pasteis de nata maximizados");

        }
    }
}
