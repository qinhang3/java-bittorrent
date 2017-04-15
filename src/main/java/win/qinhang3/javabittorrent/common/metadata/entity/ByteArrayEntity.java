package win.qinhang3.javabittorrent.common.metadata.entity;

import java.util.Arrays;

/**
 * Created by hang on 17/4/7.
 */
public class ByteArrayEntity extends Entity {
    byte[] bytes;

    public ByteArrayEntity(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return Arrays.toString(bytes);
    }

    public String asString(){
        return new String(bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteArrayEntity that = (ByteArrayEntity) o;

        return Arrays.equals(bytes, that.bytes);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
