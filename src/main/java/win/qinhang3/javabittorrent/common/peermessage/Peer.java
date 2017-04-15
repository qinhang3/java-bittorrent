package win.qinhang3.javabittorrent.common.peermessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 17/4/15.
 */
public class Peer {
    private String ip;
    private Integer port;

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
}
