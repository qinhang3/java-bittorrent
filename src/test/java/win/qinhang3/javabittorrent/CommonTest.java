package win.qinhang3.javabittorrent;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hang on 17/4/16.
 */
public class CommonTest {
    @Test
    public void test(){
        Map<String, Integer> map = new HashMap<>();
        map.put(Hex.encodeHexString(new byte[]{0,0}), 1);
        System.out.println(Hex.encodeHexString(new byte[]{0,0}));
        System.out.println(map.get(Hex.encodeHexString(new byte[]{0,0})));
    }
}
