package cn.silen_dev.lantransmission.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.silen_dev.lantransmission.model.Equipment;

/**
 * Created by HTT on 2018/4/20.
 * 对数据库表equipment进行增删改差操作
 */

public class EquipOperators implements equipService {
    //建立数据库
    Context context;
    SQLiteDatabase db;
    List<Equipment> equipList_1;
    Equipment e;
    Cursor cursor;

    public EquipOperators(Context context) {
        super();
        this.context = context;
        db = DatabaseTool.getSqLiteDatabase(context);
    }

    @Override
    /*插入一条设备信息*/
    public void insertEquipment(Equipment e) {
        String insert = "insert into Equipment(name,ip,type,status,port) values('" + e.getName() + "','" +
                e.getAddress() + "','" + e.getType() + "','" + e.getStatus() + "','" +
                e.getPort() + "')";
        db.execSQL(insert);//返回值为void
    }

    public long insertEquipWithReturnId(Equipment t){
        ContentValues contentValue=new ContentValues();
        contentValue.put("name",t.getName());
        contentValue.put("ip",t.getAddress());
        contentValue.put("type",t.getType());
        contentValue.put("status",t.getStatus());
        contentValue.put("port",t.getPort());
        return db.insert("Transmission", null, contentValue);
    }


    @Override
    /*查询设备信息*/
    public Equipment getEquipment(int id) {
        String select = "select * from Equipment where id ="+id;
        cursor = db.rawQuery(select, null);
        return get(cursor);
    }

    @Override
    /*获取所有信息*/
    public List<Equipment> getAllEquipment() {
        String select = "select * from Equipment";
        cursor = db.rawQuery(select, null);
        List<Equipment> equipList=new ArrayList<>();
        equipList.clear();
        if (cursor.moveToFirst()) {
            do {
                equipList.add(get(cursor));
            } while (cursor.moveToNext());
        }
        return equipList;
    }

    @Override
    /*更细设备名称*/
    public void updateEquipmentName(int id, String name) {
        String update = "update Equipment set name='" + name + "' where id="+id;
        db.execSQL(update, null);
    }

    @Override
    /*更新设备状态*/
    public void updateEquipmentStatus(int id, int status) {
        String update = "update Equipment set status=" + status + " where id="+id;
        db.execSQL(update, null);
    }

    @Override
    /*删除一条设备记录*/
    public void deleteEuipment(int id) {
        String delete = "delete from Equipment where id ="+id;
        db.execSQL(delete,null);
    }

    public Equipment get(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String ip = cursor.getString(cursor.getColumnIndex("address"));
        int type = cursor.getInt(cursor.getColumnIndex("type"));
        int status = cursor.getInt(cursor.getColumnIndex("status"));
        int port = cursor.getInt(cursor.getColumnIndex("port"));
        e = new Equipment(name, ip, type, status, port);
        return e;
    }
}
