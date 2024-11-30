package Week_7;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EcoServer {
    private BufferedReader in;
    private PrintWriter out;
    public static final int PORT = 8080;

    public void startServing() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        try{
            Socket socket = serverSocket.accept();
            try{
                doConnections(socket);
                server();

            }finally {socket.close();}
        }finally {serverSocket.close();}
    }

    public void doConnections(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public void server() throws IOException {
        out.println("Server is running");
        while(true){
            String str = in.readLine();
            if(str.equals("Fim")) break;
            System.out.println("Eco: " + str);
            out.println("Recieved: " + str);
        }
    }

    public static void main(String[] args) throws IOException {
        EcoServer server = new EcoServer();
        server.startServing();
    }
}
