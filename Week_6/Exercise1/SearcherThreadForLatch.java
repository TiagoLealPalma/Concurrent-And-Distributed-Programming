package Week_6.Exercise1;

import java.util.concurrent.CountDownLatch;

class SearcherThreadForLatch extends Thread{
    private String myText;
    private String textToFind;
    private CountDownLatch latch;
    private int result=-1;

    public SearcherThreadForLatch(String myText, String textToFind,
                          CountDownLatch latch) {
        this.myText = myText;
        this.textToFind = textToFind;
        this.latch = latch;
    }

    public String getMyText() {
        return myText;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void run() {
        result=myText.indexOf(textToFind);
        latch.countDown();
       // System.err.println("Latch Thread finishing at: "+System.currentTimeMillis() + "Result = " + result);
    }


}

