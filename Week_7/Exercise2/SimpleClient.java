package Week_7.Exercise2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class SimpleClient {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String name;

    public SimpleClient(String name){
        this.name = name;
    }

    public void runClient(){
        try{
            connectToServer();
            sendMessages();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            // a fechar
        }
    }

    private void connectToServer() throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        socket = new Socket(address, 8080); // Abre o socket
        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Configura um buffer, atrás de um reader, atrás da inputStream do socket
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); // Configura um printWriter, atrás de um buffer, atrás de um writer que escreve na outputStream do socket
    }

    private void sendMessages() throws IOException {
        String message = "";

        while(true) {
            out.println(name + ": calica");
            try{sleep(200);} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



    }


    public static void main(String[] args) {
        new Thread(() -> new SimpleClient("joao").runClient()).start();
        new Thread(() -> new SimpleClient("Ricardo").runClient()).start();
    }
}
