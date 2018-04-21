package cn.silen_dev.lantransmission.core.scan.Server;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import cn.silen_dev.lantransmission.MyApplication;

public class MessageTool {
    private ScannerServer scannerServer;
    private int uId;
    public MessageTool(ScannerServer scannerServer,int uId) {
        this.scannerServer=scannerServer;
        this.uId=uId;
    }


    public void processMessage(DatagramPacket datagramPacket){
        byte[] data=datagramPacket.getData();
        String result=new String(data,0,datagramPacket.getLength());
        System.out.println(result);
        int index=0;
        if ((index=result.indexOf(":"))!=-1){
            if (result.substring(index+1).equals(uId+"")){
                MyApplication.myIpAddress=datagramPacket.getAddress().getHostAddress();
                return;
            }
            result=result.substring(0,index);
        }

        switch (result){
            case ConstValue.HELLO:
                System.out.println("Hello");
                scannerServer.replyHello(new InetSocketAddress(datagramPacket.getAddress(),datagramPacket.getPort()));
                break;
            case ConstValue.HELLO_REPLY:
                scannerServer.find(datagramPacket.getAddress().getHostAddress());
                break;
        }
    }
}
