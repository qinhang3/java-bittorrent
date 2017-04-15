package win.qinhang3.javabittorrent.network;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.metadata.Metadata;
import win.qinhang3.javabittorrent.common.metadata.entity.ByteArrayEntity;
import win.qinhang3.javabittorrent.common.metadata.entity.Entity;
import win.qinhang3.javabittorrent.common.metadata.entity.MapEntity;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.util.BDecoding;
import win.qinhang3.javabittorrent.util.SystemProperties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hang on 17/4/15.
 */
@Service
public class ConnectTrackers {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public List<Peer> execute(Metadata metadata) throws IOException {
        Entity entity = metadata.getMapEntity().asMap().get("announce");
        String announceUrl = ((ByteArrayEntity) entity).asString();
        logger.info("get announce [{}]", announceUrl);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        URI uri = null;
        try {
            uri = new URIBuilder(announceUrl)
                    .addParameter("peer_id", new String(metadata.getPeerId()))
                    .addParameter("port", String.valueOf(SystemProperties.getPort()))
                    .addParameter("uploaded", "0")
                    .addParameter("left", "0")
                    .addParameter("downloaded", "0").build();
        } catch (URISyntaxException e) {
            logger.info("build query announce failed.",e);
            throw new RuntimeException(e);
        }

        String url = uri.toString()+"&info_hash=" + new String(new URLCodec().encode(metadata.getInfoHash()));

        logger.info("query announce url : {}", url);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        logger.info("query announce response status : {}", httpResponse.getStatusLine());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        httpResponse.getEntity().writeTo(baos);

        Entity responseEntry = BDecoding.decoding(baos.toByteArray(), new AtomicInteger());
        logger.info("announce response : {}", ((MapEntity) responseEntry).asMap());

        Map<String, Entity> responseMap = ((MapEntity) responseEntry).asMap();
        if (responseMap.containsKey("failure reason")){
            String failureReason = ((ByteArrayEntity) responseMap.get("failure reason")).asString();
            logger.warn("query announce failed. failure reason = {}", failureReason);
            throw new RuntimeException(failureReason);
        } else {
            ByteArrayEntity byteArrayEntity = (ByteArrayEntity) responseMap.get("peers");
            byte[] bytes = byteArrayEntity.getBytes();
            List<Peer> peers = Peer.buildPeers(bytes);
            logger.info("query announce succeed. get {} peers.", peers.size());
            return peers;
        }
    }
}
