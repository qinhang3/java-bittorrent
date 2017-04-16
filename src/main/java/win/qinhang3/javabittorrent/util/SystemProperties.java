package win.qinhang3.javabittorrent.util;

/**
 * Created by hang on 17/4/15.
 */
public class SystemProperties {

    public static final String MAGIC_STRING = "BitTorrent protocol";
    public static final byte[] EXTEND_PROTOCOL = new byte[]{0,0,0,0,0,0,0,0};

    public static int getPort(){
        return 6881;
    }
}
