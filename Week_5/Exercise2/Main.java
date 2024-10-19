package Week_5.Exercise2;

public class Main {
	private static final int NUM_REPOSITORIOS = 4; // numero de repositorios no servidor

	public static void main(String[] args) throws InterruptedException {
		Servidor servidor = new Servidor(NUM_REPOSITORIOS);
		Client[] clients = new Client[20];

		for (int i = 0; i < 20; i++) {
			clients[i] = new Client(i,servidor,10);
			clients[i].start();
		}

		for (int i = 0; i < 20; i++) {
			clients[i].join();
		}

		Thread.sleep(500);
		servidor.stopServer();
	}

}
