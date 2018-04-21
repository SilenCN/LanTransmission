package cn.silen_dev.lantransmission.core.transmission.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import cn.silen_dev.lantransmission.MyApplication;
import cn.silen_dev.lantransmission.core.scan.Server.ScannerServer;
import cn.silen_dev.lantransmission.core.transmission.Client.LanClient;
import cn.silen_dev.lantransmission.model.Equipment;

public class LanServer extends Thread {
    private List<ClientLinkThread> clientLinkThreads;
    private ServerSocket serverSocket;
    private int port;
    private boolean flag=true;
    public static final int DEFAULT_PORT = 2334;
    private MyApplication myApplication;

    private ScannerServer scannerServer;

    public LanServer(MyApplication myApplication) {
        this(DEFAULT_PORT,myApplication);
    }

    public LanServer(int port,MyApplication myApplication) {
        super();
        this.port=port;
        clientLinkThreads=new ArrayList<>();
        this.myApplication=myApplication;
    }


    @Override
    public void run() {
        super.run();
        try {
            serverSocket=new ServerSocket(port);
            while (flag){
                Socket socket=serverSocket.accept();
                ClientLinkThread clientLinkThread=new ClientLinkThread(socket,myApplication);
                clientLinkThread.start();
                clientLinkThreads.add(clientLinkThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startLan() {
        this.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    scannerServer=new ScannerServer(myApplication.getMyEquipmentInfo().getId());
                    scannerServer.setOnScannerFindListener(new ScannerServer.OnScannerFindListener() {
                        @Override
                        public void onFind(String address) {

                            LanClient lanClient=new LanClient(address);
                            lanClient.setOnLanCilentListener(new LanClient.OnLanCilentListener() {
                                @Override
                                public void getHelloReply(Equipment equipment) {
                                    myApplication.addEquipment(equipment);
                                }

                                @Override
                                public void failToLink() {

                                }
                            });
                            lanClient.sendHelo();
                        }
                    });
                    scannerServer.start();
                    while (flag){
                        scannerServer.scan();
                        Thread.sleep(4000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
