package win.qinhang3.javabittorrent.common;

import java.util.Map;

/**
 * Created by hang on 17/4/7.
 */
public class MapEntity implements Entity{
    private Map<Entity, Entity> map;
    public MapEntity(Map<Entity, Entity> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}


