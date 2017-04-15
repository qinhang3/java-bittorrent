package win.qinhang3.javabittorrent.common.peermessage;

import win.qinhang3.javabittorrent.common.metadata.Metadata;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 17/4/15.
 */
public class Peer {
    private String ip;
    private Integer port;
    private Socket socket;
    private Metadata metadata;

    private byte[] remotePeerId;
    private byte[] remoteExtendProtocol;

    public Peer(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {

        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static List<Peer> buildPeers(byte[] bytes) {
        List<Peer> peers = new ArrayList<>();
        for (int i = 0 ;i < bytes.length ; i+=6){
            String ip = String.format("%d.%d.%d.%d",
                    bytes[i] & 0xFF,
                    bytes[i + 1] & 0xFF,
                    bytes[i + 2] & 0xFF,
                    bytes[i + 3] & 0xFF
                    );
            int port = ((bytes[i + 4] & 0xFF) << 8) | (bytes[i + 5] & 0xFF);
            peers.add(new Peer(ip, port));
        }
        return peers;
    }

    @Override
    public String toString() {
        return String.format("Peer[%s:%d]", ip, port);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public byte[] getRemotePeerId() {
        return remotePeerId;
    }

    public void setRemotePeerId(byte[] remotePeerId) {
        this.remotePeerId = remotePeerId;
    }

    public byte[] getRemoteExtendProtocol() {
        return remoteExtendProtocol;
    }

    public void setRemoteExtendProtocol(byte[] remoteExtendProtocol) {
        this.remoteExtendProtocol = remoteExtendProtocol;
    }
}
