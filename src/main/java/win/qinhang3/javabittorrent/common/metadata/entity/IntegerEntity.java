package win.qinhang3.javabittorrent.common.metadata.entity;

/**
 * Created by hang on 17/4/7.
 */
public class IntegerEntity extends Entity {
    int value;

    public IntegerEntity(int i) {
        this.value = i;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegerEntity that = (IntegerEntity) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
