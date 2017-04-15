package win.qinhang3.javabittorrent;

import org.junit.Test;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.network.ConnectTrackers;
import win.qinhang3.javabittorrent.network.Handshake;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by hang on 17/4/15.
 */
public class NetworkTest extends ApplicationTest {
    @Resource
    private ConnectTrackers connectTrackers;
    @Resource
    private Handshake handshake;

    @Test
    public void testConnetTrackers() throws IOException {
        File file = new File("/Users/hang/Downloads/熊目燒飯@広瀬うみ S-Cute #431 (自購足本無水印).torrent");
        Metadata metadata = new Metadata(file);
        connectTrackers.execute(metadata);
    }

    @Test
    public void testHandShake() throws IOException {
        File file = new File("/Users/hang/Downloads/熊目燒飯@広瀬うみ S-Cute #431 (自購足本無水印).torrent");
        Metadata metadata = new Metadata(file);
        Peer peer = new Peer("192.168.1.184", 13096);
        peer.setMetadata(metadata);
        handshake.execute(peer);
    }

}
