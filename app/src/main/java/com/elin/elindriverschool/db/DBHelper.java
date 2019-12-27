package com.elin.elindriverschool.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 17535 on 2018/4/9.
 */

public class DBHelper extends SQLiteOpenHelper {

    //***数据库名称
    private static  final String DATABASE_NAME = "elinJX.db";
    //数据库版本号
    private static final int DATABASE_VERSION=1;
    //创建表,用户信息表
    public static final  String TABLE_CONTACTINFO="contact_info";
    //创建用户信息表，建表语句
    public static final String TABLE_CITYINFO="city_info";
    public static final String TABLE_PROVINCEINFO="province_info";

    private static final String CREATE_USERINFO_SQL="CREATE TABLE "
            + TABLE_CONTACTINFO
            + " (_id Integer primary key autoincrement,"
            + " uid integer,"
            + " nickname text,"
            + " avatar_url text,"
            + " username text,"
            + " account text,"
            + " password text);";

    private static final String TABEL_WEATHERINFO = "weather_info";

    private static final String CREATE_WEATHER_SQL="CREATE TABLE "
            + TABEL_WEATHERINFO
            + " (_id Integer primary key autoincrement,"
            + " cityid integer,"
            + " weather text,"
            + " degree text);";


    public DBHelper (Context context)
    {
        this(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERINFO_SQL);
        db.execSQL(CREATE_WEATHER_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTINFO);
            db.execSQL("DROP TABLE IF EXISTS "+ TABEL_WEATHERINFO);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CITYINFO);
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PROVINCEINFO);
            onCreate(db);

        }
    }
}
