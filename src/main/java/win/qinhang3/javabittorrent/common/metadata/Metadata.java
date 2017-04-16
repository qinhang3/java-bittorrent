package win.qinhang3.javabittorrent.common.metadata;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import win.qinhang3.javabittorrent.common.metadata.entity.Entity;
import win.qinhang3.javabittorrent.common.metadata.entity.IntegerEntity;
import win.qinhang3.javabittorrent.util.BDecoding;
import win.qinhang3.javabittorrent.common.metadata.entity.MapEntity;

import java.io.*;
import java.util.Arrays;
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

    public byte[] getInfoHash(){
        Entity info = getMapEntity().asMap().get("info");
        byte[] infoBytes = Arrays.copyOfRange(getData(), info.getStart(), info.getEnd());
        return DigestUtils.sha1(infoBytes);
    }

    public String getInfoHashHex(){
        return Hex.encodeHexString(getInfoHash());
    }

    public int getPieceLength(){
        MapEntity info = (MapEntity)getMapEntity().asMap().get("info");
        IntegerEntity pieceLength = (IntegerEntity)info.asMap().get("piece length");
        return pieceLength.asInt();
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
