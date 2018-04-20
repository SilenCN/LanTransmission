package cn.silen_dev.lantransmission.model;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;

public class Equipment {
    public static final int TYPE_PHONE=0;
    public static final int TYPE_COMPUTER=1;
    public static final int STATUS_ONLINE=0;
    public static final int STATUS_OFFLINE=1;

    private int id;
    private String name;     //设备名称
    private String address; //设备IP地址
    private int port= ConstValue.TCP_PORT;  //设备默认端口号
    private int type;       //设备类型：手机/电脑
    private int status;     //设备状态：在线/不在线

    //构造函数
    public Equipment(String name,String ip,int type,int status,int port){
        this.name=name;
        this.address=ip;
        this.type=type;
        this.status=status;
        this.port=port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type=type;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status=status;
    }

    public int getPort() {  return port;}

    public void setPort(int port) {
        this.port = port;
    }
}
