package cn.silen_dev.lantransmission.core.transmission.Client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.Server.LanServer;
import cn.silen_dev.lantransmission.core.transmission.TcpMessage.TcpMessage;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.model.Equipment;

public class LanClient extends Thread {
    String address;
    Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintWriter printWriter;
    private BufferedReader reader;
    private OnLanCilentListener onLanCilentListener;
    private TcpMessage tcpMessage;
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
        link();
        printWriter.println(new Gson().toJson(tcpMessage));
        printWriter.flush();
        switch (tcpMessage.getTransmission().getType()){
            case ConstValue.TRANSMISSION_FILE:
            case ConstValue.TRANSMISSION_IMAGE:
            case ConstValue.TRANSMISSION_VIDEO:

                File file=new File(tcpMessage.getTransmission().getSendPath());

                try {
                    FileInputStream fileInputStream=new FileInputStream(file);
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case ConstValue.TRANSMISSION_TEXT:
            case ConstValue.TRANSMISSION_CLIPBOARD:
                break;
        }
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
    public void sendTransmisstion(Transmission transmission,Equipment equipment){

        tcpMessage=new TcpMessage();
        tcpMessage.setToken(ConstValue.TRANSMISSION);
        tcpMessage.setTransmission(transmission);
        tcpMessage.setEquipment(equipment);
        start();
    }

    public void sendHello(){
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
