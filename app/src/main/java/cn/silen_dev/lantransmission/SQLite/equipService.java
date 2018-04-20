package cn.silen_dev.lantransmission.SQLite;

import java.util.List;

import cn.silen_dev.lantransmission.core.transmission.Transmission;
import cn.silen_dev.lantransmission.model.Equipment;

/**
 * Created by HTT on 2018/4/20.
 */

public interface equipService {

    void insertEquipment(Equipment e);  //插入一条设备信息

    Equipment getEquipment(int id); //获取一个设备信息

    List<Equipment> getAllEquipment();//获取所有设备的信息

    void deleteEuipment(int id);//删除一条设备信息

}
