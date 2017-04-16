package win.qinhang3.javabittorrent.util;

import org.apache.commons.codec.binary.Hex;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.common.metadata.entity.MapEntity;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Created by hang on 17/4/16.
 */
public class MetadataFactory {
    private static final Map<String, Metadata> map = new ConcurrentHashMap<>();

    public static Metadata getInstance(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] temp = new byte[1024];
        int len;
        while((len = fileInputStream.read(temp)) != -1){
            baos.write(temp, 0,len);
        }

        Metadata metadata = new Metadata();
        metadata.setData(baos.toByteArray());
        metadata.setMapEntity((MapEntity) BDecoding.decoding(metadata.getData(), new AtomicInteger(0)));

        return map.computeIfAbsent(metadata.getInfoHashHex(), s -> {
            metadata.setPeerId(UUID.randomUUID().toString().substring(0,20).getBytes());
            return metadata;
        });
    }

    public static Metadata get(byte[] hashInfo){
        return map.get(Hex.encodeHexString(hashInfo));
    }

}
