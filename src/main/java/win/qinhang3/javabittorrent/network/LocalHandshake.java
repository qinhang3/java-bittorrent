package win.qinhang3.javabittorrent.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.util.SocketFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import static win.qinhang3.javabittorrent.util.SystemProperties.EXTEND_PROTOCOL;
import static win.qinhang3.javabittorrent.util.SystemProperties.MAGIC_STRING;

/**
 * Created by hang on 17/4/16.
 */
@Service
public class LocalHandshake {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Boolean execute(Peer peer) throws IOException {
        if (peer.getMetadata() == null){
            return null;
        }
        if ((peer.getHandShakeStatus() & 1) == 1){
            return true;
        }
        SocketChannel socketChannel = SocketFactory.getSocketChannel(peer);

        Integer handShakeLength = 1 + MAGIC_STRING.length() + EXTEND_PROTOCOL.length + 20 + 20;
        ByteBuffer byteBuffer = peer.getOutput(handShakeLength);
        byteBuffer.clear();
        byteBuffer.put((byte) MAGIC_STRING.length());
        byteBuffer.put(MAGIC_STRING.getBytes());
        byteBuffer.put(EXTEND_PROTOCOL);
        byteBuffer.put(peer.getMetadata().getInfoHash());
        byteBuffer.put(peer.getMetadata().getPeerId());

        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        peer.setHandShakeStatus(peer.getHandShakeStatus() | 1);

        return true;
    }
}
