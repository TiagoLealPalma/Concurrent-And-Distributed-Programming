package Week_7.TestPractice;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Peer{
    private boolean running = true;


    public Peer(int port){

        new Thread(()->{
            try(ServerSocket ss = new ServerSocket(port);){
                while(running){
                    Socket s = ss.accept();
                    new Thread(()-> handleClient(s)).start();
                }
            } catch (IOException e) {e.printStackTrace();}
        }).start();
    }

    public void handleClient(Socket s) {
        try(ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream();)
        {
            Object message = in.readObject();

            System.out.println(message.toString());
        }catch( IOException | ClassNotFoundException e){e.printStackTrace();}
    }

    public void connect(String addr, int port) throws IOException {
        Socket s = new Socket(addr,port);
        new Thread(()-> handleClient(s)).start();
    }
}


public class WriterThread extends Thread {
    private ArrayList<FileBlockRequestMessage> requests;
    private ArrayList<FileBlockAnswerMessage> answers = new ArrayList();
    private final int BLOCKS_EXPECTED;
    private CountDownLatch latch;

    public WriterThread(ArrayList<FileBlockRequestMessage> requests) {
        this.requests = requests;
        BLOCKS_EXPECTED = requests.size();
        latch = new CountDownLatch(BLOCKS_EXPECTED);

        start();
    }

    @Override
    public void run() {
        long initialTime = System.currentTimeMillis();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream fos = new FileOutputStream(new File("./ficheirosDescarregados"));) {
            while (!answers.isEmpty())
                fos.write(answers.remove(0).data());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - initialTime) + "ms");
    }

    public synchronized void put(FileBlockAnswerMessage block) {
        answers.add(block);
        latch.countDown();
    }
}


public class CountDownLatch{
    private int count;

    public CountDownLatch(int count){
        this.count=count;
    }

    public synchronized void countDown(){
        if(count > 0)
            count--;
        if(count == 0)
            notifyAll();
    }

    public synchronized void await(){
        while(count != 0)
            try{wait();}catch(InterruptedException e){e.printStackTrace();}
    }

}
