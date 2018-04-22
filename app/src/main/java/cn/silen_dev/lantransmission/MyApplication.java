package cn.silen_dev.lantransmission;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import cn.silen_dev.lantransmission.model.Equipment;

public class MyApplication extends Application {
    private List<String> scannerAddressList=new ArrayList<>();
    private List<Equipment> connectEquipments;
    private Equipment myEquipmentInfo;
    private List<OnEquipmentLinstener> onEquipmentLinsteners=new ArrayList<>();
    public static String myIpAddress;
    private SharedPreferences sharedPreferences;

    public static String BRODCAST_ADDRESS=null;

    @Override
    public void onCreate() {
        super.onCreate();
        connectEquipments=new ArrayList<>();
        myEquipmentInfo=new Equipment();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!sharedPreferences.contains("myId")){
            sharedPreferences.edit().putInt("myId",(int)(Math.random()*2000000000));
        }
        myEquipmentInfo.setId(sharedPreferences.getInt("myId",(int)(Math.random()*2000000000)));
        myEquipmentInfo.setName(sharedPreferences.getString("setting_user_name","Lan"));
    }

    public boolean addEquipment(Equipment equipment){
        for (Equipment equip:connectEquipments){
            if (equip.getId()==equipment.getId()){
                return false;
            }
        }
        connectEquipments.add(equipment);
        for (OnEquipmentLinstener onEquipmentLinstener:onEquipmentLinsteners){
            if (null!=onEquipmentLinstener){
                onEquipmentLinstener.add(equipment);
            }
        }
        return true;
    }

    public Equipment findEquipment(int id){
        for (Equipment equipment:connectEquipments){
            if (equipment.getId()==id){
                return equipment;
            }
        }
        return null;
    }
    public Equipment findEquipment(String address){
        for (Equipment equipment:connectEquipments){
            if (equipment.getAddress().equals(address)){
                return equipment;
            }
        }
        return null;
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

    public List<Equipment> getConnectEquipments() {
        return connectEquipments;
    }

    public Equipment getMyEquipmentInfo() {
        myEquipmentInfo.setAddress(myIpAddress);
        return myEquipmentInfo;
    }

    public void setMyEquipmentInfo(Equipment myEquipmentInfo) {
        this.myEquipmentInfo = myEquipmentInfo;
    }

    public interface OnEquipmentLinstener{
        void add(Equipment equipment);
        void remove(Equipment equipment);
    }

    public void addEquipmentLinstener(OnEquipmentLinstener onEquipmentLinstener){
        if (null!=onEquipmentLinstener){
            onEquipmentLinsteners.add(onEquipmentLinstener);
        }
    }
}
