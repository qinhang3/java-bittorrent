package win.qinhang3.javabittorrent.common.metadata;

import win.qinhang3.javabittorrent.util.BDecoding;
import win.qinhang3.javabittorrent.common.metadata.entity.MapEntity;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hang on 17/4/15.
 */
public class Metadata {
    private byte[] data;
    private MapEntity mapEntity;
    private File file;
    private byte[] peerId;

    public Metadata(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] temp = new byte[1024];
        int len;
        while((len = fileInputStream.read(temp)) != -1){
            baos.write(temp, 0,len);
        }
        data = baos.toByteArray();
        mapEntity = (MapEntity) BDecoding.decoding(data, new AtomicInteger(0));
        peerId = UUID.randomUUID().toString().substring(0,20).getBytes();
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public MapEntity getMapEntity() {
        return mapEntity;
    }

    public void setMapEntity(MapEntity mapEntity) {
        this.mapEntity = mapEntity;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public byte[] getPeerId() {
        return peerId;
    }

    public void setPeerId(byte[] peerId) {
        this.peerId = peerId;
    }
}
