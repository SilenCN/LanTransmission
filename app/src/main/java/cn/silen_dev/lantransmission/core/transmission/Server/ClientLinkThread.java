package cn.silen_dev.lantransmission.core.transmission.Server;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.TcpMessage.TcpMessage;
import cn.silen_dev.lantransmission.model.Equipment;

public class ClientLinkThread extends Thread {
    private Socket socket;

    public ClientLinkThread(Socket socket) {
        super();
        this.socket=socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            line=reader.readLine();
            TcpMessage tcpMessage= new Gson().fromJson(line,TcpMessage.class);
            switch (tcpMessage.getToken()){
                case ConstValue.HELLO:
                    processHello(outputStream);
                    break;
                case ConstValue.REPLY_HELLO:
                    processReplyHello(tcpMessage);
                    break;
                case ConstValue.EXIT:
                    processExit();
                    break;
                case ConstValue.TRANSMISSION:
                    processTransmission(tcpMessage);
                    break;
                case ConstValue.GET_FILE:
                    processGetFile(tcpMessage);
                    break;

            }
            outputStream.close();
            inputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processHello(OutputStream outputStream) throws IOException {
        //TODO:响应hello，需要补充本机信息
        Equipment equipment=new Equipment();
        equipment.setId(1);
        equipment.setName("测试");
        TcpMessage tcpMessage=new TcpMessage();
        tcpMessage.setEquipment(equipment);
        tcpMessage.setToken(ConstValue.REPLY_HELLO);
        PrintWriter writer=new PrintWriter(outputStream);
        writer.println(new Gson().toJson(tcpMessage));
    }

    private void processReplyHello(TcpMessage tcpMessage){
        Equipment equipment=tcpMessage.getEquipment();
        equipment.setAddress(socket.getInetAddress().getHostAddress());
        //TODO：正式发现其他设备，需要等待确认
    }
    private void processExit(){
        //TODO:处理其他程序退出
    }
    private void processTransmission(TcpMessage tcpMessage){
        //TODO:处理传输
    }
    private void processGetFile(TcpMessage tcpMessage){
        //TODO:发送广播
    }
}
