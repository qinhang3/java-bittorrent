package win.qinhang3.javabittorrent.common.peermessage;

import win.qinhang3.javabittorrent.common.metadata.Metadata;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hang on 17/4/15.
 */
public class Peer {
    private String ip;
    private Integer port;
    private Socket socket;
    private SocketChannel socketChannel;
    private Metadata metadata;

    private byte[] remotePeerId;
    private byte[] remoteExtendProtocol;

    private Integer handShakeStatus = 0; // 1-send 2-receive 3-both

    private ByteBuffer output;
    private ByteBuffer input;

    private Integer nextMessageLength = null;

    public Peer(String ip, Integer port, Metadata metadata) {
        this.ip = ip;
        this.port = port;
        setMetadata(metadata);
    }

    public String getIp() {

        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static List<Peer> buildPeers(byte[] bytes) {
        List<Peer> peers = new ArrayList<>();
        for (int i = 0 ;i < bytes.length ; i+=6){
            String ip = String.format("%d.%d.%d.%d",
                    bytes[i] & 0xFF,
                    bytes[i + 1] & 0xFF,
                    bytes[i + 2] & 0xFF,
                    bytes[i + 3] & 0xFF
                    );
            int port = ((bytes[i + 4] & 0xFF) << 8) | (bytes[i + 5] & 0xFF);
            peers.add(new Peer(ip, port, null));
        }
        return peers;
    }

    @Override
    public String toString() {
        return String.format("Peer[%s:%d]", ip, port);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
        if (metadata != null){
            output = ByteBuffer.allocate(metadata.getPieceLength() + 10);
            input = ByteBuffer.allocate(metadata.getPieceLength() + 10);
        }
    }

    public byte[] getRemotePeerId() {
        return remotePeerId;
    }

    public void setRemotePeerId(byte[] remotePeerId) {
        this.remotePeerId = remotePeerId;
    }

    public byte[] getRemoteExtendProtocol() {
        return remoteExtendProtocol;
    }

    public void setRemoteExtendProtocol(byte[] remoteExtendProtocol) {
        this.remoteExtendProtocol = remoteExtendProtocol;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public Integer getHandShakeStatus() {
        return handShakeStatus;
    }

    public void setHandShakeStatus(Integer handShakeStatus) {
        this.handShakeStatus = handShakeStatus;
    }

    public ByteBuffer getOutput() {
        return output;
    }

    public void setOutput(ByteBuffer output) {
        this.output = output;
    }

    public ByteBuffer getInput() {
        return input;
    }

    public void setInput(ByteBuffer input) {
        this.input = input;
    }

    public ByteBuffer getOutput(Integer handShakeLength) {
        if (output.capacity() < handShakeLength){
            output = ByteBuffer.allocate(handShakeLength);
        }
        return output;
    }

    public ByteBuffer getInput(Integer handShakeLength) {
        if (input.capacity() < handShakeLength){
            input = ByteBuffer.allocate(handShakeLength);
        }
        return input;
    }

    public Integer getNextMessageLength() {
        return nextMessageLength;
    }

    public void setNextMessageLength(Integer nextMessageLength) {
        this.nextMessageLength = nextMessageLength;
    }
}
