package cn.silen_dev.lantransmission.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HTT on 2018/4/20.
 */


public class DataEquipment extends SQLiteOpenHelper {
    //建表
    public static final String CREATE_EQUIPMENT = "create table Equipment(" +
            "id integer primary key," +
            "name text," +
            "ip text," +
            "type integer,"+
            "status integer)";

    private Context mContext;

    //构造方法
    public DataEquipment(Context context,String name,SQLiteDatabase.CursorFactory factory , int version)
    {
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行建表语句
        db.execSQL(CREATE_EQUIPMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
