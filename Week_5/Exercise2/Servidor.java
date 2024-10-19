package Week_5.Exercise2;
import Week_4.Exercise3.Cliente;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor {

	private volatile boolean running = true;
	private List<Repository> repoList = new LinkedList<>();
	private List<SongRequest> waitlist = new LinkedList<>();
	private Map<Object , List<SongRequest>>inProgress = new ConcurrentHashMap<>();

	public Servidor(int numRepositorios) {

		// Criar e inicia os repositorios
		for (int i = 0; i != numRepositorios; i++) {
			Repository repo = new Repository(i, this);
			repoList.add(repo);
		}
		for (Repository repo : repoList) {
			repo.start();
		}

		// TODO
	}
	// Client calls this method in order to download songs
	public synchronized List<SongRequest> downloadSongs(List<SongRequest> songs) {
		// Get Client ID, so a request from client A isn't confused with an equal request from client B and cause problems
		Object requestId = Thread.currentThread();
		// Adds the request to a wait list and a progress list
		waitlist.addAll(songs);
		inProgress.put(requestId, new LinkedList<>());
		notifyAll();

		// Waits for the new inProgress list with the song info to be the same size as the request
		while(!songs.isEmpty()) {
			try {
				wait();
				if(!running) return null;
			} catch (InterruptedException e) {
				return null;
			}
		}
		// Remove from progress map
		return inProgress.remove(requestId);
	}

	public synchronized SongRequest getSongRequest() {
		if(!running) return null;
		SongRequest requestToReturn = null;
		while(waitlist.isEmpty()) {
			try {
				wait();
				if (!running) return null;
			} catch (InterruptedException e) {
				return null;
			}
		}

		requestToReturn = waitlist.remove(0);

		// Remove all occurrences of this song from the waitlist since this thread is going to handle these request
		// for multiple clients.
		// This is the most important optimization because it prevents the repos from getting a request that was already
		// fulfilled by an earlier repo
		Iterator<SongRequest> it = waitlist.iterator();
		while(it.hasNext()) {
			SongRequest request = it.next();
			if(request.getSongTitle().equals(requestToReturn.getSongTitle()))
				it.remove();
		}

		return requestToReturn;
	}

	public synchronized int uploadSong(SongRequest song) {
		int clientCounter = 0;
		// Run through the Lists of request, searching for requests for the song passed in the argument
		// This will fulfill multiple request from different clients, so it can spare some overhead from other threads
		for(Map.Entry<Object , List<SongRequest>> entry : inProgress.entrySet()){
			Client client = (Client)entry.getKey();
			if(client == null) return -1;

			// Check if the song is requested and create iterator to safely remove the processed songs from the list.
			Iterator<SongRequest> iterator = client.getRequest().iterator();
			while(iterator.hasNext()){
				SongRequest request = iterator.next();
				if (song.getSongTitle().equals(request.getSongTitle())) {
					iterator.remove();
					entry.getValue().add(song);
					clientCounter++;
				}
			}
			if(client.isEmpty())
				notifyAll();
		}
		while(waitlist.contains(song)) waitlist.remove(song); // Clean the request for this song fulfilled by this method
		return clientCounter;
	}

	public void stopServer() {
		for (Repository repo : repoList) {
			repo.interrupt();
		}
	}

}
