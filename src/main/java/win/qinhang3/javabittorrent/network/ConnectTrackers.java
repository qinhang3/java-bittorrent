package win.qinhang3.javabittorrent.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.common.metadata.entity.ByteArrayEntity;
import win.qinhang3.javabittorrent.common.metadata.entity.Entity;

/**
 * Created by hang on 17/4/15.
 */
@Service
public class ConnectTrackers {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void execute(Metadata metadata){
        Entity entity = metadata.getMapEntity().asMap().get("announce");
        String announceUrl = ((ByteArrayEntity) entity).asString();
        logger.info("get announce [{}]", announceUrl);






    }

}
