package cn.silen_dev.lantransmission.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HTT on 2018/4/20.
 */

public class DataTransmission extends SQLiteOpenHelper {

    public static final String CREATE_TRANSMISSION="" +
            "create table Transmission("+
            "id integer primary key," +
            "type integer," +
            "message text," +
            "length integer,"+
            "time integer,"+
            "userId integer,"+
            "sendPath text,"+
            "savePath text,"+
            "status integer,"+
            "sr integer)";

    private Context mContext;
    public DataTransmission(Context context, String name, SQLiteDatabase.CursorFactory factory , int version)
    {
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行建表语句
        db.execSQL(CREATE_TRANSMISSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
