package win.qinhang3.javabittorrent.common.metadata.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hang on 17/4/7.
 */
public class MapEntity extends Entity {
    private Map<Entity, Entity> map;
    private Map<String, Entity> asMap;

    public MapEntity(Map<Entity, Entity> map) {
        this.map = map;
        asMap = new ConcurrentHashMap<>();
        map.entrySet().forEach(entry -> {
            asMap.put(((ByteArrayEntity)entry.getKey()).asString(), entry.getValue());
        });
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntity mapEntity = (MapEntity) o;

        return map != null ? map.equals(mapEntity.map) : mapEntity.map == null;

    }

    @Override
    public int hashCode() {
        return map != null ? map.hashCode() : 0;
    }

    public Map<String, Entity> asMap(){
        return asMap;
    }
}


