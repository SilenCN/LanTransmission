package cn.silen_dev.lantransmission;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import cn.silen_dev.lantransmission.model.Equipment;

public class MyApplication extends Application {
    private List<String> scannerAddressList=new ArrayList<>();
    private List<Equipment> connectEquipments;
    private Equipment myEquipmentInfo;
    private List<OnEquipmentLinstener> onEquipmentLinsteners=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        connectEquipments=new ArrayList<>();
    }

    public void addEquipment(Equipment equipment){
        for (Equipment equip:connectEquipments){
            if (equip.getId()==equipment.getId()){
                return;
            }
        }
        connectEquipments.add(equipment);
        for (OnEquipmentLinstener onEquipmentLinstener:onEquipmentLinsteners){
            if (null!=onEquipmentLinstener){
                onEquipmentLinstener.remove(equipment);
            }
        }
    }
    public void removeEquipment(Equipment equipment){
        for (Equipment equip:connectEquipments){
            if (equip.getId()==equipment.getId()){
                connectEquipments.remove(equip);
                for (OnEquipmentLinstener onEquipmentLinstener:onEquipmentLinsteners){
                    if (null!=onEquipmentLinstener){
                        onEquipmentLinstener.remove(equip);
                    }
                }
                break;
            }
        }
    }

    public Equipment getMyEquipmentInfo() {
        return myEquipmentInfo;
    }

    public void setMyEquipmentInfo(Equipment myEquipmentInfo) {
        this.myEquipmentInfo = myEquipmentInfo;
    }

    public interface OnEquipmentLinstener{
        void add(Equipment equipment);
        void remove(Equipment equipment);
    }
}
