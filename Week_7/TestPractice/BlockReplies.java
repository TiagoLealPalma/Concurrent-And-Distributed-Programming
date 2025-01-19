package Week_7.TestPractice;

import java.io.Serializable;

public class BlockReplies implements Serializable {
    int index;

   public BlockReplies (int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getData() {
       return 1;
    }
}
