package cn.silen_dev.lantransmission.core.transmission.TcpMessage;

import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.model.Equipment;

public class TcpMessage {
    private String token;
    private Equipment equipment;
    private Transmission transmission;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
