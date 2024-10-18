package Week_5.Exercise2;

public class Main {
	private static final int NUM_REPOSITORIOS = 4; // numero de repositorios no servidor

	public static void main(String[] args) throws InterruptedException {
		Servidor servidor = new Servidor(NUM_REPOSITORIOS);

		Client client1 = new Client(0, servidor, 4);
		Client client2 = new Client(1, servidor, 8);
		Client client3 = new Client(2, servidor, 13);
		client1.start();
		client2.start();
		client3.start();

		client1.join();
		client2.join();
		client3.join();
		Thread.sleep(500);
		servidor.stopServer();
	}

}
