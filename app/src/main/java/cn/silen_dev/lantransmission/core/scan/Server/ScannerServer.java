package cn.silen_dev.lantransmission.core.scan.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ScannerServer extends Thread {
    private static final int SCANNER_SERVER_PORT = 234;
    private static final int PACKET_LENGTH = 10*1024;

    private DatagramSocket datagramSocket;

    private OnScannerFindListener onScannerFindListener;
    private MessageTool messageTool;
    public ScannerServer() throws SocketException {
        super();
        datagramSocket=new DatagramSocket(SCANNER_SERVER_PORT);
        messageTool=new MessageTool(this);
    }

    @Override
    public void run() {
        super.run();
        byte[] buffer=new byte[PACKET_LENGTH];
        while (true){
            DatagramPacket packet=new DatagramPacket(buffer,PACKET_LENGTH);
            try {
                datagramSocket.receive(packet);
                messageTool.processMessage(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void scan(){
        InetSocketAddress inetAddress=new InetSocketAddress("255.255.255.255",SCANNER_SERVER_PORT);
        send(inetAddress,ConstValue.HELLO);
    }

    public void replyHello(InetSocketAddress inetSocketAddress){
        send(inetSocketAddress,ConstValue.HELLO_REPLY);
    }


    private void send(InetSocketAddress address,String message){
        byte[] buffer=message.getBytes();
        try {
            datagramSocket.send(new DatagramPacket(buffer,buffer.length,address));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnScannerFindListener(OnScannerFindListener onScannerFindListener) {
        this.onScannerFindListener = onScannerFindListener;
    }

    public interface OnScannerFindListener{
        void onFind(String address);
    }

    public void find(String address){
        System.out.println(address);
        if (null!=onScannerFindListener){
            onScannerFindListener.onFind(address);
        }
    }
}
