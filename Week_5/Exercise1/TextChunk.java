package Week_5.Exercise1;

import java.util.ArrayList;
import java.util.List;

public class TextChunk {
	public final String text;
	private final int initialPos;
	public final String stringToBeFound;
	private List<Integer> foundPos = new ArrayList<>();

	public TextChunk(String text, int initialPos, String stringToBeFound) {
		super();
		this.text = text;
		this.initialPos = initialPos;
		this.stringToBeFound = stringToBeFound;
	}

	public int getInitialPos() {
		return initialPos;
	}

	public List<Integer> getFoundPos() {
		return foundPos;
	}

	public void addFoundPos(int pos) {
		foundPos.add(pos);
	}
	@Override
	public String toString() {
		return "TextChunk [text=" + text + ", initialPos=" + initialPos + ", stringToBeFound=" + stringToBeFound
				+ ", foundPos=" + foundPos + "]";
	}
}
