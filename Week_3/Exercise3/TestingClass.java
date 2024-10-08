package Week_3.Exercise3;

import java.util.Scanner;

public class TestingClass {
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

        System.out.println("\nDinheiro Depositado pelas threads: " + summedAmmount );
        System.out.println("Dinheiro na conta: " + account.getBalance() );
        System.out.println("Dinheiro perdido pela má programação do sistema: " + (summedAmmount - account.getBalance()));
        System.out.println("Equivalente a " + Math.floor((summedAmmount - account.getBalance())/1.2) + " pasteis de nata perdidos :(");

        System.out.println("\n\nDigite 'arranje o sistema!' para que o sistema seja arranjado:");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        
        while(!line.equals("arranje o sistema!")){
            System.out.println("Aprenda a escrever!");
            line = scanner.nextLine();
        }
            Account safeAccount = new Account();
            SafeDepositThread[] safeArray = new SafeDepositThread[10];
            double safeSummedAmmount = 0;

            for (int i = 0; i < safeArray.length; i++) {
                safeArray[i] = new SafeDepositThread(safeAccount);
                safeArray[i].start();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (SafeDepositThread thread : safeArray) {
                safeSummedAmmount += thread.terminate();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("\n\nDinheiro Depositado pelas threads: " + safeSummedAmmount );
            System.out.println("Dinheiro na conta: " + safeAccount.getBalance() );
            System.out.println("Dinheiro perdido pela má programação do sistema: " + (safeSummedAmmount - safeAccount.getBalance()));
            System.out.println("Pasteis de nata maximizados :)");


    }
}
