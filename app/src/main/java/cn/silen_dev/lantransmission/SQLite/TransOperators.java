package cn.silen_dev.lantransmission.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.silen_dev.lantransmission.core.transmission.ConstValue;
import cn.silen_dev.lantransmission.core.transmission.Transmission;

/**
 * 传输记录操作类
 */

public class TransOperators implements TransService{

    //建立数据库
    Context context;
    public LanDataBase database=new LanDataBase(context,"LanDataBase.db",null,1);
    SQLiteDatabase db=database.getWritableDatabase();
    Cursor cursor;
    List<Transmission> transList=new ArrayList<>();
    Transmission t;

    @Override
    /*插入一条传输记录*/
    public void insertTrans(Transmission t) {
        String insert="insert into Transmission(type,message,length,time,userId,savePath,sendPath,status,sr)" +
                "values('"+t.getType()+"','"+t.getMessage()+"','"+t.getLength()+"','"+t.getTime()+"','"+t.getUserId()+
                "','"+t.getSavePath()+"','"+t.getSendPath()+"','"+t.getStatus()+"','"+t.getSr()+"')";
        db.execSQL(insert);//返回值为void
    }

    @Override
    public Transmission getTrans(int id) {
        String select="select * from Transmission where id = ?";
        cursor=db.rawQuery(select,new String[]{String.valueOf(id)});
        return get(cursor);
    }

    @Override
    /*根据文件类型返回传输列表*/
    public List<Transmission> getAllTrans(int type) {
        String select="select * from Transmission where type = ?";
        switch (type){
            //图片
            case ConstValue.TRANSMISSION_IMAGE:
                cursor=db.rawQuery(select,new String[]{String.valueOf(type)});
                transList.clear();
                if(cursor.moveToFirst()) {
                    do {
                        transList.add(get(cursor));
                    }while(cursor.moveToNext());
                }
                return transList;
            //视频
            case ConstValue.TRANSMISSION_VIDEO:
                cursor=db.rawQuery(select,new String[]{String.valueOf(type)});
                transList.clear();
                if(cursor.moveToFirst()) {
                    do {
                        transList.add(get(cursor));
                    }while(cursor.moveToNext());
                }
                return transList;
            //文件
            case ConstValue.TRANSMISSION_FILE:
                cursor=db.rawQuery(select,new String[]{String.valueOf(type)});
                transList.clear();
                if(cursor.moveToFirst()) {
                    do {
                        transList.add(get(cursor));
                    }while(cursor.moveToNext());
                }
                return transList;
            //设备
            case ConstValue.TRANSMISSION_DEVICE:
                cursor=db.rawQuery(select,new String[]{String.valueOf(type)});
                transList.clear();
                if(cursor.moveToFirst()) {
                    do {
                        transList.add(get(cursor));
                    }while(cursor.moveToNext());
                }
                return transList;
            //文本
            case ConstValue.TRANSMISSION_TEXT:
                cursor=db.rawQuery(select,new String[]{String.valueOf(type)});
                transList.clear();
                if(cursor.moveToFirst()) {
                    do {
                        transList.add(get(cursor));
                    }while(cursor.moveToNext());
                }
                return transList;
            //粘贴板
            case ConstValue.TRANSMISSION_CLIPBOARD:
                cursor=db.rawQuery(select,new String[]{String.valueOf(type)});
                transList.clear();
                if(cursor.moveToFirst()) {
                    do {
                        transList.add(get(cursor));
                    }while(cursor.moveToNext());
                }
                return transList;
        }
        return transList;
    }

    @Override
    /*返回所有记录*/
    public List<Transmission> getAllTrans() {
        String select="select * from Transmission";
        cursor=db.rawQuery(select,null);
        transList.clear();
        transList.add(get(cursor));
        return transList;
    }

    @Override
    /*更新文件名称*/
    public void updateTransName(int id, String name) {
        String update="update Transmission set name='"+name+"' where id=?";
        db.execSQL(update,new String[]{String.valueOf(id)});
    }

    @Override
    /*更新文件内容*/
    public void updateText(int id, String text) {
        String update="update Equipment set message='"+text+"' where id=?";
        db.execSQL(update,new String[]{String.valueOf(id)});
    }

    @Override
    /*更新文件保存路径*/
    public void updateSavepath(int id, String savePath) {
        String update="update Equipment set savePath='"+savePath+"' where id=?";
        db.execSQL(update,new String[]{String.valueOf(id)});
    }

    @Override
    /*更新文件发送路径*/
    public void updateSendpath(int id, String sendPath) {
        String update="update Equipment set sendPath='"+sendPath+"' where id=?";
        db.execSQL(update,new String[]{String.valueOf(id)});
    }

    @Override
    public void updateSource(int id, int sr) {
        String update="update Equipment set sr='"+sr+"' where id=?";
        db.execSQL(update,new String[]{String.valueOf(id)});
    }

    @Override
    public void deleteTrans(int id) {
        String delete="delete from Transnisison where id = ?";
        db.execSQL(delete,new String[]{String.valueOf(id)});
    }

    public Transmission get(Cursor cursor)
    {
        String name=cursor.getString(cursor.getColumnIndex("name"));
        int type=cursor.getInt(cursor.getColumnIndex("type"));
        String message=cursor.getString(cursor.getColumnIndex("message"));
        int status=cursor.getInt(cursor.getColumnIndex("status"));
        long length=cursor.getInt(cursor.getColumnIndex("length"));
        long time=cursor.getInt(cursor.getColumnIndex("time"));
        int userId=cursor.getInt(cursor.getColumnIndex("userId"));
        String savePath=cursor.getString(cursor.getColumnIndex("savePath"));
        String sendPath=cursor.getString(cursor.getColumnIndex("sendPath"));
        int sr=cursor.getInt(cursor.getColumnIndex("sr"));
        t=new Transmission(type,name,message,length,time,userId,savePath,sendPath,status,sr);
        return t;
    }
}
