package com.example.yemengshuai.avmoo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yemengshuai on 2016/8/14.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION=1;
    public static final String DB_NAME="AVMOO";
    public static final String TABLE1="tb_start";


    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table if not exists "+TABLE1
        +"(id integer primary key autoincrement, name text," +
                ";");





    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        if (oldVersion<newVersion){
            db.execSQL("drop table if exists"+TABLE1);
            onCreate(db);
        }
    }
}
