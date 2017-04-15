package win.qinhang3.javabittorrent.common.metadata.entity;

/**
 * Created by hang on 17/4/7.
 */
public abstract class Entity {
    public int start;
    public int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
