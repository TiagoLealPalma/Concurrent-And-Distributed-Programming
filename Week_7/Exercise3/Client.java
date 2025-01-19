package Week_7.Exercise3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

    @Override
    public void run(){
        try {
                System.out.println("trying to connect to server");
                 Socket s = new Socket("localhost", 2424);

                 ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(s.getInputStream());


            while(!interrupted()) {
                 Object message = in.readObject();

                 if (message instanceof TimeMessage t) {
                     out.writeObject(new TimeReceivedConfirmation());
                     System.out.println(t.getTime());
                 }
             }





        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
     new Client().start();
    }

}
