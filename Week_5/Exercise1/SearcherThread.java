package Week_5.Exercise1;


class SearcherThread extends Thread {
	private TextRepository textRepository;
	private volatile boolean running = true;

	public SearcherThread(TextRepository textRepository) {
		this.textRepository = textRepository;
	}

	@Override
	public void run() {
		while(running){
			TextChunk chunk = textRepository.getChunk();
			if(chunk != null){
				String text = chunk.toString();
				String toFind = chunk.stringToBeFound;
				int foundPosition = 0;
				while(text.contains(toFind)) {
					foundPosition += text.indexOf(toFind);
					chunk.addFoundPos(foundPosition);
					text = chunk.text.substring(Math.min(foundPosition + toFind.length(), chunk.text.length()));
					System.out.println(getName() + ": Encontrei na posição " + (chunk.getInitialPos() + foundPosition));
				}
			}
			else{
				System.out.println("No chunks left to analyze, " + getName() +" is shutting down");
				running = false;
				return;
			}
			textRepository.submitResult(chunk);
			System.out.println(getName() + ": Chunk analysis completed.");
		}
	}
}
