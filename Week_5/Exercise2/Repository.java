package Week_5.Exercise2;

import java.util.List;
import java.util.Objects;

public class Repository extends Thread {
	private Servidor server;
	private List<SongRequest> listOfTitles;
	private SongRequest requestToHandle;
	private volatile boolean running = true;

	public Repository(int id, Servidor servidor) {
		super("Repo-" + id);
		this.server = servidor;
		
		listOfTitles = SongRequest.getCompleteListOfSongs();
	}

	@Override
	public void run() {
		while(running){
			requestToHandle=server.getSongRequest();
			// Repo was interrupted while on wait()
			if(requestToHandle == null) {
				running = false;
				System.out.println("Repo " + getName() + " has stopped running");
			}
			// Handling of the request
			else{
				// Get song data
				for(SongRequest request : listOfTitles){
					if(Objects.equals(request.getSongTitle(), requestToHandle.getSongTitle())) {
						int result = -1;
                        if (( result = server.uploadSong(request)) != -1)
							System.out.println(getName()+": " + request.getSongTitle() + " was successfully uploaded to" + result + " client" + (result > 0 ? "s" : null));
                        else
                            System.out.println(getName() + ": Error occurred on upload of " + request.getSongTitle());

                    }
				}
			}

		}
	}


}
