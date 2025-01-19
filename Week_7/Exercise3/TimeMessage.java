package Week_7.Exercise3;

import java.io.Serializable;

public class TimeMessage implements Serializable {
    private long time;

    public TimeMessage(){
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }
}
