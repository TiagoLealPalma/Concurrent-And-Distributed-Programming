package Week_7.TestPractice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Communications {
    private boolean running = true;
    public volatile boolean downloading = false;
    private ArrayList<BlockReplies> replies;
    private SharedWriter writer;
    private List<ObjectOutputStream> outs = new ArrayList<>();
    private ExecutorService threadpool;


    public Communications(){
        ExecutorService threadpool = Executors.newFixedThreadPool(10);

        new Thread(()->{
            try(ServerSocket ss = new ServerSocket(6969)){

                while(running) {
                    Socket s = ss.accept();
                    threadpool.submit(() -> clientHandler(s));
                }

            }catch(IOException e){}
        }).start();

        writer = new SharedWriter(100, this);

    }

    private void clientHandler(Socket s) {
        try(ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());){

            synchronized (outs){
            outs.add(s.getPort(), out);} // Guardar o canal de saida para enviar mensagem da Main Thread,
                                            // para iniciar o processo de download

            while(running){
                Object message = in.readObject(); // Recebe a mensagem
                s.setSoTimeout(0); // Tira o tempo maximo


                if(message instanceof BlockRequest br){ // Se receber pedido devolve resposta
                    out.writeObject(getReplies(br.getIndex()));

                }else if(message instanceof BlockReplies br){ // Se receber resposta mete no recurso partilhado
                    if(br.getIndex() == -1) continue;
                    writer.put(br);
                }

                if(downloading){ // Se estiver a fazer download, pede o proximo bloco ao recurso partilhado
                    out.writeObject(writer.take());
                    s.setSoTimeout(2000); // Mete tempo de resposta maximo de 2 segundos
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void connect(String addr, int port){
        new Thread(()-> {
            try {
                clientHandler(new Socket(addr,port));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private synchronized BlockReplies getReplies(int index) {
        return replies.size() > index ? replies.get(index) : new BlockReplies(-1);
    }

    private void startDownload() throws IOException {
        downloading = true;
        for (ObjectOutputStream out : outs) {
            out.writeObject(writer.take());
        }
    }

}
