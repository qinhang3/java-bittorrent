package win.qinhang3.javabittorrent;

import org.junit.Test;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.network.ConnectTrackers;
import win.qinhang3.javabittorrent.network.Handshake;
import win.qinhang3.javabittorrent.network.LocalHandshake;
import win.qinhang3.javabittorrent.network.NioPeerOpt;
import win.qinhang3.javabittorrent.util.MetadataFactory;

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
    @Resource
    private LocalHandshake localHandshake;
    @Resource
    private NioPeerOpt nioPeerOpt;

    @Test
    public void testConnetTrackers() throws IOException {
        File file = new File("/Users/hang/Downloads/test001.torrent");
        Metadata metadata = MetadataFactory.getInstance(file);
        connectTrackers.execute(metadata);
    }

    @Test
    public void testHandShake() throws IOException {
        File file = new File("/Users/hang/Downloads/test001.torrent");
        Metadata metadata = MetadataFactory.getInstance(file);
        Peer peer = new Peer("192.168.1.184", 13096, metadata);
        peer.setMetadata(metadata);
        handshake.execute(peer);
    }

    @Test
    public void nioTest() throws IOException {
        File file = new File("/Users/hang/Downloads/test001.torrent");
        Metadata metadata = MetadataFactory.getInstance(file);
        Peer peer = new Peer("192.168.1.184", 13096, metadata);
        peer.setMetadata(metadata);

        localHandshake.execute(peer);
    }

    @Test
    public void nioSelectorTest() throws IOException, InterruptedException {
        File file = new File("/Users/hang/Downloads/test001.torrent");
        Metadata metadata = MetadataFactory.getInstance(file);
        Peer peer = new Peer("192.168.1.184", 13096, metadata);
        peer.setMetadata(metadata);
        nioPeerOpt.execute(peer);
    }

}
