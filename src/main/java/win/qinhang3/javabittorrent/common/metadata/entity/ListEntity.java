package win.qinhang3.javabittorrent.common.metadata.entity;

import java.util.List;

/**
 * Created by hang on 17/4/7.
 */
public class ListEntity extends Entity {
    private List<Entity> entities;

    public ListEntity(List<Entity> list) {
        this.entities = list;
    }

    @Override
    public String toString() {
        return entities.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListEntity that = (ListEntity) o;

        return entities != null ? entities.equals(that.entities) : that.entities == null;

    }

    @Override
    public int hashCode() {
        return entities != null ? entities.hashCode() : 0;
    }
}
