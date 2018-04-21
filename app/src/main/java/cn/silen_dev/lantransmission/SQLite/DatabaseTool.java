package cn.silen_dev.lantransmission.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseTool {
    private static SQLiteDatabase sqLiteDatabase;

    public static SQLiteDatabase getSqLiteDatabase(Context context) {

        if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
            return sqLiteDatabase;
        } else {
            synchronized (DatabaseTool.class) {
                if (null != sqLiteDatabase && sqLiteDatabase.isOpen()) {
                    return sqLiteDatabase;
                }else{
                    sqLiteDatabase=new LanDataBase(context).getWritableDatabase();
                    return sqLiteDatabase;
                }
            }
        }
    }
}
