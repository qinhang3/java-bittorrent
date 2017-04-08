package win.qinhang3.javabittorrent.common;

/**
 * Created by hang on 17/4/7.
 */
public class IntegerEntity implements Entity {
    int value;

    public IntegerEntity(int i) {
        this.value = i;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
