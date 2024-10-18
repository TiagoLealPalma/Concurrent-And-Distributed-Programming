package Week_5.Exercise2;

public class Client extends Thread {
	private int clientId;
	private Servidor server;
	private int numSongRequests;

	public Client(int clienteId, Servidor servidor, int numSongRequests) {
		super("Cliente-" + clienteId);
		this.clientId = clienteId;
		this.server = servidor;
		this.numSongRequests = numSongRequests;
	}

	public void run() {
		// TODO

	}
}
