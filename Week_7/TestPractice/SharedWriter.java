package Week_7.TestPractice;

import java.util.ArrayList;

public class SharedWriter {
    private Communications communications;
    private int expectedBlocks;
    private ArrayList<BlockRequest> requests = new ArrayList<>();
    private ArrayList<BlockReplies> replies = new ArrayList<>();

    public SharedWriter(int expectedBlocks, Communications communications) {
        this.expectedBlocks = expectedBlocks;
        for (int i = 0; i < expectedBlocks; i++) {
            requests.add(new BlockRequest(i));
        }
    }

    public synchronized void put(BlockReplies br) {
        replies.add(br);
        if (replies.size() == expectedBlocks) {
            communications.downloading = false;
        }
    }

    public synchronized BlockRequest take() {
        return !requests.isEmpty() ? requests.remove(0) : new BlockRequest(-1);
    }
}
