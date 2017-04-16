package win.qinhang3.javabittorrent.network;

import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.common.peermessage.PeerMessage;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by hang on 17/4/16.
 */
@Service
public class WriteMessage {
    @Resource
    private LocalHandshake localHandshake;

    public void execute(Peer peer) throws IOException {
        if ((peer.getHandShakeStatus() & 1) != 1){
            localHandshake.execute(peer);
        } else {
//            readLength(peer);
//            PeerMessage peerMessage = readData(peer);
//            if (peerMessage != null){
//                peerMessage.execute(peer);
//            }
        }
    }
}
