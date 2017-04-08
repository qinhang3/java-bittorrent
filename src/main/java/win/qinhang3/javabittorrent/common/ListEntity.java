package win.qinhang3.javabittorrent.common;

import java.util.List;

/**
 * Created by hang on 17/4/7.
 */
public class ListEntity implements Entity {
    private List<Entity> entities;

    public ListEntity(List<Entity> list) {
        this.entities = list;
    }

    @Override
    public String toString() {
        return entities.toString();
    }
}
