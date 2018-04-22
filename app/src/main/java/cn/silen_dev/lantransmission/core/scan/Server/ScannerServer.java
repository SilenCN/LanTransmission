package cn.silen_dev.lantransmission.core.scan.Server;

import android.content.SharedPreferences;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Handler;

public class ScannerServer extends Thread {
    private static final int SCANNER_SERVER_PORT = 2333;
    private static final int PACKET_LENGTH = 1024;


    private DatagramSocket datagramSocket;

    private OnScannerFindListener onScannerFindListener;
    private MessageTool messageTool;
    private int uId;
    public ScannerServer(int uId) throws SocketException {
        super();
        this.uId=uId;
        datagramSocket=new DatagramSocket(SCANNER_SERVER_PORT);
        messageTool=new MessageTool(this,uId);
    }

    @Override
    public void run() {
        super.run();
        byte[] buffer=new byte[PACKET_LENGTH];
        while (true){
            DatagramPacket packet=new DatagramPacket(buffer,0,buffer.length);
            try {
                datagramSocket.receive(packet);
                messageTool.processMessage(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void scan(){
        System.out.println("scan");
        InetSocketAddress inetAddress=new InetSocketAddress("192.168.43.255",SCANNER_SERVER_PORT);
        send(inetAddress,ConstValue.HELLO+":"+uId);
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
