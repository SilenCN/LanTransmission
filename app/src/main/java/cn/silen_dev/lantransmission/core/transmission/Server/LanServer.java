package cn.silen_dev.lantransmission.core.transmission.Server;

import android.support.v4.app.FragmentManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cn.silen_dev.lantransmission.MyApplication;
import cn.silen_dev.lantransmission.core.scan.Server.ScannerServer;
import cn.silen_dev.lantransmission.core.transmission.Client.LanClient;
import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.model.Equipment;

public class LanServer extends Thread {
    private List<ClientLinkThread> clientLinkThreads;
    private ServerSocket serverSocket;
    private int port;
    private boolean flag = true;
    public static final int DEFAULT_PORT = ConstValue.TCP_PORT;
    private MyApplication myApplication;

    private ScannerServer scannerServer;

    private FragmentManager fragmentManager;

    public LanServer(MyApplication myApplication,FragmentManager fragmentManager) {
        this(DEFAULT_PORT, myApplication,fragmentManager);
    }

    public LanServer(int port, MyApplication myApplication, FragmentManager fragmentManager) {
        super();
        this.port = port;
        clientLinkThreads = new ArrayList<>();
        this.myApplication = myApplication;
        this.fragmentManager=fragmentManager;
    }


    @Override
    public void run() {
        super.run();
        try {
            serverSocket = new ServerSocket(port);
            while (flag) {
                Socket socket = serverSocket.accept();
                ClientLinkThread clientLinkThread = new ClientLinkThread(socket, myApplication,fragmentManager);
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
                    scannerServer = new ScannerServer(myApplication.getMyEquipmentInfo().getId());
                    scannerServer.setOnScannerFindListener(new ScannerServer.OnScannerFindListener() {
                        @Override
                        public void onFind(String address) {
                            if (myApplication.findEquipment(address) == null) {
                                LanClient lanClient = new LanClient(address);
                                lanClient.setOnLanCilentListener(new LanClient.OnLanCilentListener() {
                                    @Override
                                    public void getHelloReply(Equipment equipment) {
                                        myApplication.addEquipment(equipment);
                                    }

                                    @Override
                                    public void failToLink() {

                                    }
                                });
                                lanClient.sendHello();
                            }
                        }
                    });
                    scannerServer.start();
                    while (flag) {
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
        for (ClientLinkThread clientLinkThread : clientLinkThreads) {
            clientLinkThread.interrupt();
        }
        this.interrupt();
    }

    public boolean getStatus() {
        return false;
    }
}
