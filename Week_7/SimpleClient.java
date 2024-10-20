package Week_7;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

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
        socket = new Socket(address, EcoServer.PORT); // Abre o socket
        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Configura um buffer, atrás de um reader, atrás da inputStream do socket
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); // Configura um printWriter, atrás de um buffer, atrás de um writer que escreve na outputStream do socket
    }

    private void sendMessages() throws IOException {
        for (int i = 0; i < 10; i++) {
            out.println("ola " + i);
            String str = in.readLine();
            System.out.println(str);
            try{Thread.sleep(3000);}catch(InterruptedException e){}
        }
        out.println("FIM");
    }


    public static void main(String[] args) {
        new SimpleClient().runClient();
    }
}
