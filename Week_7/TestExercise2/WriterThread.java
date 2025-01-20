package Week_7.TestExercise2;
import Week_7.TestPractice.BlockReplies;
import Week_7.TestPractice.BlockRequest;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WriterThread extends Thread {
    private CountDownLatch latch;
    private boolean running = true;
    private List<BlockRequest> requests;
    private List<BlockReplies> data;


    public WriterThread(List<Socket> peers, List<BlockRequest> requestsList) {
        latch = new CountDownLatch(requestsList.size());
        requests = requestsList;
        for (Socket s : peers) {
            new Thread(() -> workerThreads(s)).start();
        }

    }

    @Override
    public void run() {

        try {
            latch.await(); // Wait for data to write the file

            try (FileOutputStream fos = new FileOutputStream("./directoryToWrite")) {
                for (BlockReplies reply : data) {
                    fos.write(reply.getData());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized BlockRequest takeRequest() throws InterruptedException {
        return !requests.isEmpty() ? requests.getFirst() : null;
    }

    public synchronized void put(BlockReplies blockReply) throws InterruptedException {
        data.add(blockReply);
        latch.countDown();
    }

    public synchronized void giveBack(BlockRequest blockRequest) throws InterruptedException {
        requests.add(blockRequest);
    }

    // Task to ask and receive threads
    private void workerThreads(Socket socket) {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {

            while (running) {

                BlockRequest request = takeRequest(); // Buscar proximo request
                if (request == null) return; // Significa que está vazio

                try {
                    out.writeObject(request); // Pedir Proximo request
                    socket.setSoTimeout(2000); // Set timeout -> Max 2 segundos de espera por resposta

                    Object message = null;
                    while (!(message instanceof BlockReplies br)) // Esperar para receber mensagem de reply ao pedido;
                        message = in.readObject();

                    put(br);

                } catch (SocketException e) {
                    giveBack(request);
                } catch (IOException e) {
                    giveBack(request);
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
