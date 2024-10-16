package Week_5.Exercise1;

import java.util.ArrayList;
import java.util.List;

public class TextRepository {
	private List<TextChunk> chunks=new ArrayList<>();
	private List<TextChunk> foundChunks=new ArrayList<>();
	private int numChunks;
	private int numReceivedResults;

	public TextRepository(String text, String stringToBeFound, int chunkSize) {
		for(int i=0; i<text.length(); i+=chunkSize) {
			chunks.add(new TextChunk(text.substring(i, (int)Math.min(i+chunkSize,text.length())), 
					i, stringToBeFound));
			numChunks++;	
		}
	}

	public synchronized TextChunk getChunk() {
		// TODO

		return null;
	}

	public synchronized void submitResult(TextChunk chunk) {
		// TODO
	}

	public synchronized List<TextChunk> getAllMatches() throws InterruptedException {
		// TODO
		
		return null;

	}
}
