package cn.silen_dev.lantransmission.core.transmission.Server;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import cn.silen_dev.lantransmission.ConfirmDialogActivity;
import cn.silen_dev.lantransmission.MyApplication;
import cn.silen_dev.lantransmission.SQLite.TransOperators;
import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.TcpMessage.TcpMessage;
import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.dialog.NotificationCome;
import cn.silen_dev.lantransmission.dialog.NotificationUtils;
import cn.silen_dev.lantransmission.dialog.TransConfirmDialogFragment;
import cn.silen_dev.lantransmission.model.Equipment;

public class ClientLinkThread extends Thread {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader reader;
    private Object lock = new Object();
    private String savePath;
    private boolean isRecieve;
    private MyApplication myApplication;
    private TransOperators transOperators;

    private FragmentManager fragmentManager;

    public ClientLinkThread(Socket socket, MyApplication myApplication, FragmentManager fragmentManager) {
        super();
        this.fragmentManager = fragmentManager;
        this.socket = socket;
        this.myApplication = myApplication;
    }

    @Override
    public void run() {
        super.run();
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            line = reader.readLine();
            System.out.println("LanServer:" + line);
            switch (line) {
                case ConstValue.APP:
                    processApp();
                    break;
                default:
                    processWeb();
                    break;
            }

            outputStream.close();
            inputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processApp() throws IOException {
        String line = reader.readLine();
        TcpMessage tcpMessage = new Gson().fromJson(line, TcpMessage.class);
        switch (tcpMessage.getToken()) {
            case ConstValue.HELLO:
                processHello();
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
    }

    private void processWeb() {

    }

    private void processHello() throws IOException {

        TcpMessage tcpMessage = new TcpMessage();
        tcpMessage.setEquipment(myApplication.getMyEquipmentInfo());
        tcpMessage.setToken(ConstValue.REPLY_HELLO);
        PrintWriter writer = new PrintWriter(outputStream);
        // writer.println(ConstValue.APP);
        writer.println(new Gson().toJson(tcpMessage));
        writer.flush();
    }

    private void processReplyHello(TcpMessage tcpMessage) {
        Equipment equipment = tcpMessage.getEquipment();
        equipment.setAddress(socket.getInetAddress().getHostAddress());
        //TODO：正式发现其他设备，需要等待确认
    }

    private void processExit() {
        //TODO:处理其他程序退出
    }

    private void processTransmission(TcpMessage tcpMessage) {

        transOperators = new TransOperators(myApplication.getApplicationContext());

        //TODO:处理传输
        switch (tcpMessage.getTransmission().getType()) {
            case ConstValue.TRANSMISSION_TEXT:
            case ConstValue.TRANSMISSION_CLIPBOARD:
                //TODO:收到文字类数据
                System.out.println(tcpMessage.getTransmission().getMessage());
                NotificationCome notificationCome = new NotificationCome(tcpMessage.getTransmission());
                notificationCome.sendSimplestNotificationWithAction(myApplication.getApplicationContext());

                Transmission transmission = tcpMessage.getTransmission();
                transmission.setTime(System.currentTimeMillis());
                transmission.setSr(ConstValue.RECEIVE);
                transmission.setUserId(tcpMessage.getEquipment().getId());
                transmission.setStatus(ConstValue.STATUS_DONE);
                transOperators.insertTrans(transmission);

                break;
            case ConstValue.TRANSMISSION_FILE:
            case ConstValue.TRANSMISSION_IMAGE:
            case ConstValue.TRANSMISSION_VIDEO:
                fileRecieve(tcpMessage);
                synchronized (lock) {
                    while (true) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    if (isRecieve) {
                        File file = new File(savePath);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            byte[] buffer = new byte[2048];
                            int len;
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        new NotificationUtils(myApplication.getApplicationContext()).sendNotification("传输完成", file.getName());



                    } else {

                    }
                }
                break;
        }
    }

    private void processGetFile(TcpMessage tcpMessage) {
        //TODO:发送广播
    }

    public void fileRecieve(final TcpMessage tcpMessage) {

        ConfirmDialogActivity.onTransmissionConfirmResultListener = new TransConfirmDialogFragment.OnTransmissionConfirmResultListener() {
            @Override
            public void send(String savePath) {
                savePath = savePath + "/" + tcpMessage.getTransmission().getFileName();
                File file;
                while (true) {
                    file = new File(savePath);
                    if (file.exists()) {
                        if (-1 == savePath.lastIndexOf(".")) {
                            savePath = savePath + "(1)";
                        } else {
                            savePath = savePath.substring(0, savePath.lastIndexOf(".")) + "(1)" + savePath.substring(savePath.lastIndexOf("."));
                        }
                    } else {
                        break;
                    }
                }
                tcpMessage.getTransmission().setSavePath(savePath);
                tcpMessage.getTransmission().setStatus(ConstValue.STATUS_ING);
                getCheck(true, tcpMessage.getTransmission().getSavePath());

                Transmission transmission = tcpMessage.getTransmission();
                transmission.setTime(System.currentTimeMillis());
                transmission.setSr(ConstValue.RECEIVE);
                transmission.setUserId(tcpMessage.getEquipment().getId());
                transmission.setStatus(ConstValue.STATUS_DONE);

                transmission.setId((int)transOperators.insertTransWithReturnId(transmission));
                System.out.println(new Gson().toJson(transmission));
                System.out.println(new Gson().toJson(tcpMessage));
            }

            @Override
            public void cancel() {
                tcpMessage.getTransmission().setStatus(ConstValue.STATUS_NONE);
                getCheck(false, null);
                Transmission transmission = tcpMessage.getTransmission();
                transmission.setTime(System.currentTimeMillis());
                transmission.setSr(ConstValue.RECEIVE);
                transmission.setUserId(tcpMessage.getEquipment().getId());
                transmission.setStatus(ConstValue.STATUS_NONE);

                transmission.setId((int)transOperators.insertTransWithReturnId(transmission));

            }
        };

        Intent intent = new Intent();
        intent.putExtra("transmission", tcpMessage.getTransmission());
        intent.putExtra("equipment", tcpMessage.getEquipment());
        intent.setClass(myApplication.getApplicationContext(), ConfirmDialogActivity.class);
        myApplication.getApplicationContext().startActivity(intent);

    }

    public void getCheck(boolean isRecieve, String saveFilePath) {
        this.isRecieve = isRecieve;
        this.savePath = saveFilePath;
        synchronized (lock) {
            lock.notify();
        }
    }
}
