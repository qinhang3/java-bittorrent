package win.qinhang3.javabittorrent.network;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.util.MetadataFactory;
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
public class RemoteHandshake {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Boolean execute(Peer peer) throws IOException {
        SocketChannel socketChannel = SocketFactory.getSocketChannel(peer);
        Integer handShakeLength = 1 + MAGIC_STRING.length() + EXTEND_PROTOCOL.length + 20 + 20;
        ByteBuffer byteBuffer = peer.getInput(handShakeLength);

        byteBuffer.limit(handShakeLength);
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        if (handShakeLength != byteBuffer.limit()){
            byteBuffer.compact();
            return null;
        }

        if (byteBuffer.get() != MAGIC_STRING.length()){
            logger.info("peer = {}, un support protocol. handshake failed.", peer);
            return false;
        }
        for (int i = 0; i < MAGIC_STRING.length(); i++) {
            if (byteBuffer.get() != MAGIC_STRING.charAt(i)){
                logger.info("peer = {}, un support protocol. handshake failed.", peer);
                return false;
            }
        }

        byte[] peerExtendProtocol = new byte[8];
        byte[] infoHash = new byte[20];
        byte[] peerId = new byte[20];
        byteBuffer.get(peerExtendProtocol);
        byteBuffer.get(infoHash);
        byteBuffer.get(peerId);
//        byteBuffer.clear();

        if (peer.getMetadata() != null) {
            if (!Arrays.equals(infoHash, peer.getMetadata().getInfoHash())) {
                logger.info("{} handshake failed. info hash is wrong.", peer);
                return false;
            }

            peer.setRemoteExtendProtocol(peerExtendProtocol);
            peer.setRemotePeerId(peerId);
            peer.setHandShakeStatus(peer.getHandShakeStatus() | 2);

            logger.info("{} handshake success", peer);
            return true;
        } else {
            Metadata metadata = MetadataFactory.get(infoHash);
            if (metadata == null){
                logger.warn("no such metadata which infohash = {}", Hex.encodeHexString(infoHash));
                throw new IOException("no such metadata.");
            }
            peer.setMetadata(metadata);
            peer.setHandShakeStatus(peer.getHandShakeStatus() | 2);
            return true;
        }
    }
}
