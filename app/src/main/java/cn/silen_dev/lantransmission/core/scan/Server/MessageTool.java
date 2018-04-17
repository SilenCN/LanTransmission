package cn.silen_dev.lantransmission.core.scan.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MessageTool {
    private ScannerServer scannerServer;
    public MessageTool(ScannerServer scannerServer) {
        this.scannerServer=scannerServer;
    }

    public void processMessage(DatagramPacket datagramPacket){
        byte[] data=datagramPacket.getData();
        String result=new String(data,0,data.length);
        switch (result){
            case ConstValue.HELLO:
                scannerServer.replyHello(new InetSocketAddress(datagramPacket.getAddress(),datagramPacket.getPort()));
                break;
            case ConstValue.HELLO_REPLY:
                scannerServer.find(datagramPacket.getAddress().getHostAddress());
                break;
        }
    }
}
