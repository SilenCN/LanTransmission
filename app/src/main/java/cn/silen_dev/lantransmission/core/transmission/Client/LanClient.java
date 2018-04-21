package cn.silen_dev.lantransmission.core.transmission.Client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.Server.LanServer;
import cn.silen_dev.lantransmission.core.transmission.TcpMessage.TcpMessage;
import cn.silen_dev.lantransmission.model.Equipment;

public class LanClient extends Thread {
    String address;
    Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintWriter printWriter;
    private BufferedReader reader;
    private OnLanCilentListener onLanCilentListener;
    public LanClient(String address) {
        this.address = address;

    }
    private void link(){
        try {
            socket=new Socket(address, LanServer.DEFAULT_PORT);

            inputStream=socket.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            outputStream=socket.getOutputStream();
            printWriter=new PrintWriter(outputStream);
            printWriter.println(ConstValue.APP);
            printWriter.flush();
        } catch (IOException e) {
            if (onLanCilentListener!=null){
                onLanCilentListener.failToLink();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    public void sendHelo(){
        System.out.println("LanClient:sendHello");
        link();

        TcpMessage tcpMessage=new TcpMessage();
        tcpMessage.setToken(ConstValue.HELLO);
        printWriter.println(new Gson().toJson(tcpMessage));
        printWriter.flush();
        try {
            String result=reader.readLine();
            System.out.println("Lan:"+result);
            TcpMessage tcpMessage1=(TcpMessage)new Gson().fromJson(result,TcpMessage.class);
            Equipment equipment = tcpMessage1.getEquipment();
            equipment.setAddress(socket.getInetAddress().getHostAddress());
            if (null!=onLanCilentListener){
                onLanCilentListener.getHelloReply(equipment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setOnLanCilentListener(OnLanCilentListener onLanCilentListener) {
        this.onLanCilentListener = onLanCilentListener;
    }

    public interface OnLanCilentListener{
        void getHelloReply(Equipment equipment);
        void failToLink();
    }

}
