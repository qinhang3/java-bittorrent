package win.qinhang3.javabittorrent.util;

import win.qinhang3.javabittorrent.common.peermessage.Peer;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by hang on 17/4/15.
 */
public class SocketFactory {
    public static Socket getPeerSocket(Peer peer) throws IOException {
        if (peer.getSocket() != null){
            return peer.getSocket();
        }
        synchronized (peer){
            Socket socket = new Socket(peer.getIp(), peer.getPort());
            peer.setSocket(socket);
            return socket;
        }
    }
}
