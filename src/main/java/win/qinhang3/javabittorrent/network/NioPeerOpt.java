package win.qinhang3.javabittorrent.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.qinhang3.javabittorrent.common.peermessage.Peer;
import win.qinhang3.javabittorrent.util.SocketFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hang on 17/4/16.
 */
@Service
public class NioPeerOpt {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ReadMessage readMessage;
    @Resource
    private WriteMessage writeMessage;

    public void execute(Peer peer) throws IOException, InterruptedException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketFactory.getSocketChannel(peer);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE, peer);

        while(true){
            int select = selector.select();
            if (select == 0){
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()){
                    logger.info("isAcceptable");
//                    readMessage.execute((Peer) selectionKey.attachment());
                    selectionKey.channel().register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }else if (selectionKey.isConnectable()){
                    logger.info("isConnectable");
                    selectionKey.channel().register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    writeMessage.execute((Peer) selectionKey.attachment());
                }else if (selectionKey.isReadable()) {
                    logger.info("isReadable");
                    readMessage.execute((Peer) selectionKey.attachment());
                } else if (selectionKey.isWritable()) {
                    logger.info("isWritable");
                    writeMessage.execute((Peer) selectionKey.attachment());
                }
                iterator.remove();
            }
            Thread.sleep(100L);
        }

    }
}
