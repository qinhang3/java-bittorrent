package win.qinhang3.javabittorrent;

import org.junit.Test;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.network.ConnectTrackers;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by hang on 17/4/15.
 */
public class NetworkTest extends ApplicationTest {
    @Resource
    private ConnectTrackers connectTrackers;

    @Test
    public void testConnetTrackers() throws IOException {
        File file = new File("/Users/hang/Downloads/[Thz.la]snis-882.torrent");
        Metadata metadata = new Metadata(file);
        connectTrackers.execute(metadata);
    }

}
