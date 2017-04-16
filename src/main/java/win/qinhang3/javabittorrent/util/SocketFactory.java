package win.qinhang3.javabittorrent.util;

import win.qinhang3.javabittorrent.common.peermessage.Peer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

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

    public static SocketChannel getSocketChannel(Peer peer) throws IOException {
        if (peer.getSocketChannel() != null){
            return peer.getSocketChannel();
        }
        synchronized (peer){
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(peer.getIp(), peer.getPort()));
            socketChannel.configureBlocking(false);
            peer.setSocketChannel(socketChannel);
            return socketChannel;
        }
    }
}
