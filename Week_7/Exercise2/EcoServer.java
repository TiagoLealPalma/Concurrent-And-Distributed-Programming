package Week_7.Exercise2;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EcoServer extends Thread{
    private BufferedReader in;
    private PrintWriter out;
    public static final int PORT = 8080;
    private boolean running = true;

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {throw new RuntimeException(e);}

        while (running) {
            try{
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket.getInetAddress().getHostAddress());

                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            while(running){

                                String message = in.readLine();
                                if(message != null) {
                                    out.println(message);
                                    System.out.println(message);
                                }
                            }
                        } catch (IOException e) {throw new RuntimeException(e);}
                    }
                }).start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        EcoServer server = new EcoServer();
        server.start();
    }
}
