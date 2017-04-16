package win.qinhang3.javabittorrent.common.peermessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hang on 17/4/15.
 */
public class PeerMessage {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private byte data[];

    public PeerMessage(byte[] data) {
        this.data = data;
    }

    public void execute(Peer peer){
        logger.info("{} get message.length = {}. type = {}", peer, data.length, data[0]);
    }
}
