package win.qinhang3.javabittorrent.common;

import java.util.Arrays;

/**
 * Created by hang on 17/4/7.
 */
public class StringEntity implements Entity {
    byte[] str;
    public StringEntity(byte[] str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return Arrays.toString(str) + new String(str);
    }
}
