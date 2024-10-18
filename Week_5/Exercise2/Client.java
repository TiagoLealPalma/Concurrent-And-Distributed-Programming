package Week_5.Exercise2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Client extends Thread {
	private final int clientId;
	private final Servidor server;
	private int numSongRequests;
	private List<SongRequest> request;

	public Client(int clienteId, Servidor servidor, int numSongRequests) {
		super("Cliente-" + clienteId);
		this.clientId = clienteId;
		this.server = servidor;
		this.numSongRequests = numSongRequests;
		request = SongRequest.getRandomListOfSongsRequests(numSongRequests);
	}

	public void run() {
		request=server.downloadSongs(request);

        if(request == null) {
			System.out.println("Cliente " + clientId + ": Download was interrupted.");
			return;
		}

		System.out.println("Client " + clientId + ": Finished downloading " + request.size() + " songs.");
		//showData();

	}


	public void showData(){
		for(SongRequest s : request){
			System.out.println(s.getSongTitle() + ": " + Arrays.toString(s.getSongData()));
		}
	}

	public synchronized List<SongRequest> getRequest() {
		return request;
	}

	public synchronized void removeFromRequestList(String SongTitle){
		Iterator<SongRequest> iterator = request.iterator();
		while(iterator.hasNext()){
			String songTitle = iterator.next().getSongTitle();
			if(songTitle.equals(SongTitle)){
				iterator.remove();
			}
		}
	}

	public synchronized boolean isEmpty(){
		return request.isEmpty();
	}
}
