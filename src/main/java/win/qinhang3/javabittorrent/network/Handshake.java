package win.qinhang3.javabittorrent.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.util.SocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by hang on 17/4/15.
 */
@Service
public class Handshake {
    private static final String MAGIC_STRING = "BitTorrent protocol";
    private static final byte[] EXTEND_PROTOCOL = new byte[]{0,0,0,0,0,0,0,0};

    private Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Peer peer) throws IOException {
        Socket peerSocket = SocketFactory.getPeerSocket(peer);

        //send handshake
        OutputStream outputStream = peerSocket.getOutputStream();
        outputStream.write((byte)MAGIC_STRING.length());
        outputStream.write(MAGIC_STRING.getBytes());
        outputStream.write(EXTEND_PROTOCOL);
        outputStream.write(peer.getMetadata().getInfoHash());
        outputStream.write(peer.getMetadata().getPeerId());
        outputStream.flush();

        //receive handshake
        InputStream inputStream = peerSocket.getInputStream();
        if (inputStream.read() != MAGIC_STRING.length()){
            logger.info("peer = {}, un support protocol. handshake failed.", peer);
            return false;
        }
        for (int i = 0; i < MAGIC_STRING.length(); i++) {
            if (inputStream.read() != MAGIC_STRING.charAt(i)){
                logger.info("peer = {}, un support protocol. handshake failed.", peer);
                return false;
            }
        }

        byte[] peerExtendProtocol = new byte[8];
        byte[] infoHash = new byte[20];
        byte[] peerId = new byte[20];
        if (inputStream.read(peerExtendProtocol) != 8){
            logger.info("{} un support protocol. handshake failed. read extend protocol failed.", peer);
            return false;
        }
        if (inputStream.read(infoHash) != 20){
            logger.info("{} un support protocol. handshake failed. read info hash failed.", peer);
            return false;
        }
        if (inputStream.read(peerId) != 20){
            logger.info("{} un support protocol. handshake failed. read peerId failed.", peer);
            return false;
        }

        if (!Arrays.equals(infoHash, peer.getMetadata().getInfoHash())){
            logger.info("{} handshake failed. info hash is wrong.", peer);
            return false;
        }

        peer.setRemoteExtendProtocol(peerExtendProtocol);
        peer.setRemotePeerId(peerId);

        logger.info("{} handshake success", peer);

        return true;
    }
}
