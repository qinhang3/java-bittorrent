package win.qinhang3.javabittorrent.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.common.peermessage.PeerMessage;
import win.qinhang3.javabittorrent.util.SocketFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by hang on 17/4/16.
 */
@Service
public class ReadMessage {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RemoteHandshake remoteHandshake;

    public void execute(Peer peer) throws IOException {
        if ((peer.getHandShakeStatus() & 2) != 2){
            remoteHandshake.execute(peer);
        } else {
            if (readLength(peer)) {
                PeerMessage peerMessage = readData(peer);
                if (peerMessage != null) {
                    peerMessage.execute(peer);
                }
            }
        }
    }

    private boolean readLength(Peer peer) throws IOException {
        if (peer.getNextMessageLength() != null){
            return true;
        }

        SocketChannel socketChannel = SocketFactory.getSocketChannel(peer);
        ByteBuffer byteBuffer = peer.getInput(4);
        byteBuffer.compact();
        byteBuffer.limit(4);
        socketChannel.read(byteBuffer);
        byteBuffer.flip();

        if (byteBuffer.limit() < 4){
            return false;
        } else {
            peer.setNextMessageLength(byteBuffer.getInt());
            return true;
        }
    }

    public PeerMessage readData(Peer peer) throws IOException {
        if (peer.getNextMessageLength() == null){
            logger.error("unknow length of data in this {}", peer);
            throw new IOException("unknow length of data");
        }

        SocketChannel socketChannel = SocketFactory.getSocketChannel(peer);
        ByteBuffer byteBuffer = peer.getInput(peer.getNextMessageLength());
        byteBuffer.compact();
        byteBuffer.limit(peer.getNextMessageLength());
        socketChannel.read(byteBuffer);
        byteBuffer.flip();

        if (byteBuffer.limit() < peer.getNextMessageLength()){
            return null;
        } else {
            byte[] data = new byte[peer.getNextMessageLength()];
            byteBuffer.get(data);
            PeerMessage peerMessage = new PeerMessage(data);
            peer.setNextMessageLength(null);
            return peerMessage;
        }
    }
}
