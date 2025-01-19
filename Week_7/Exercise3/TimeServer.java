package Week_7.Exercise3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TimeServer {
    private volatile boolean running = true;
    private ReentrantLock lock = new ReentrantLock();
    private Condition updateTime = lock.newCondition();

    public TimeServer(){
        // Abrir o servidor
        new Thread(()->{
            try (ServerSocket ss = new ServerSocket(2424)){
            System.out.println("Server started");
                while(running) {
                    Socket clientSocket = ss.accept();
                    handleClient(clientSocket);
                }
            }catch(RuntimeException e){
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // Serviço de update a cada minuto
        new Thread(this :: timeUpdate).start();
    }

    private void handleClient(Socket clientSocket) {
        new Thread(()->{
            try {
                System.out.println(clientSocket.getInetAddress().getHostAddress());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                while(running){
                    lock.lock();
                    try {
                        updateTime.await();
                    }finally {
                        lock.unlock();
                    }

                    out.writeObject(new TimeMessage());
                    out.flush();
                    clientSocket.setSoTimeout(2000);
                    Object message = in.readObject();

                    if(!(message instanceof TimeReceivedConfirmation)){
                        throw new SocketTimeoutException();
                    }
                    clientSocket.setSoTimeout(0);
                }

            }  catch (SocketTimeoutException e) {
                try {
                    System.err.println("Failed To receive confirmation from client. Shutting down...");
                    clientSocket.close();
                } catch (IOException ex) {throw new RuntimeException(ex);}
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void timeUpdate(){
        while(running) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.lock();
            try {
                updateTime.signalAll();
            }finally { lock.unlock(); }
        }
    }

    private void stop(){
        running = false;
        lock.lock();
        try{updateTime.signalAll();} finally { lock.unlock(); }
    }


    public static void main(String[] args) throws InterruptedException {
        new TimeServer();
    }
}
