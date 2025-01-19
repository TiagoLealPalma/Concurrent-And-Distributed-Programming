package Week_7.TestPractice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class Communications {
    private boolean running = true;
    private ArrayList<BlockReplies>

    public Communications(){
        new Thread(()->{
            try(ServerSocket ss = new ServerSocket(2424)){

                while(running) {
                    Socket s = ss.accept();
                    new Thread(() -> clientHandler(s)).start();
                }

            }catch(IOException e){}
        }).start();
    }

    private void clientHandler(Socket s) {
        try(ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());){

            while(running){
                Object message = in.readObject();

                if(message instanceof BlockRequest br){

                }
            }



        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
