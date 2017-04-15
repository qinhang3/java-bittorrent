package win.qinhang3.javabittorrent.common.metadata.entity;

import java.util.Arrays;

/**
 * Created by hang on 17/4/7.
 */
public class ByteArrayEntity extends Entity {
    byte[] str;

    public ByteArrayEntity(byte[] str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return Arrays.toString(str);
    }

    public String asString(){
        return new String(str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteArrayEntity that = (ByteArrayEntity) o;

        return Arrays.equals(str, that.str);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(str);
    }
}
