package Week_7.TestPractice;

import java.io.Serializable;

public class BlockRequest implements Serializable {
    private int index;

    public BlockRequest(int index) {
        this.index=index;
    }

    public int getIndex() {
        return index;
    }
}
