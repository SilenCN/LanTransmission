package cn.silen_dev.lantransmission.core.transmission.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LanServer extends Thread {
    private List<ClientLinkThread> clientLinkThreads;
    private ServerSocket serverSocket;
    private int port;
    private boolean flag=true;

    public LanServer(int port) {
        super();
        this.port=port;
        clientLinkThreads=new ArrayList<>();
    }

    @Override
    public void run() {
        super.run();
        try {
            serverSocket=new ServerSocket(port);
            while (flag){
                Socket socket=serverSocket.accept();
                ClientLinkThread clientLinkThread=new ClientLinkThread(socket);
                clientLinkThread.start();
                clientLinkThreads.add(clientLinkThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startLan() {
        this.start();
    }

    public void stopLan() {
        for (ClientLinkThread clientLinkThread:clientLinkThreads){
            clientLinkThread.interrupt();
        }
        this.interrupt();
    }

    public boolean getStatus() {
        return false;
    }
}
