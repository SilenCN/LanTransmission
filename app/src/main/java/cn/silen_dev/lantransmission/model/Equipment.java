package cn.silen_dev.lantransmission.model;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;

public class Equipment {
    private String address;
    private int port= ConstValue.TCP_PORT;
    private String name;
    private int id;

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
