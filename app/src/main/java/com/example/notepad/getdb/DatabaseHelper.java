package com.example.notepad.getdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author boukyuan
 * 创建数据库db
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        /** version数据库版本号    name：数据库名称    factory:一个可选的游标工厂（通常是 Null） */
        super(context, name, factory, version);
    }

    /**
     * 复写onCreate（）
     * 调用时刻：当数据库第1次创建时调用
     * 作用：创建数据库 表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("执行了onCreate方法");
        //创建数据库1张表
        // 通过execSQL()执行SQL语句（此处创建1名为notepad的表）使用AUTOINCREMENT关键字的自动增加
        String surface = "CREATE TABLE notepad (id INTEGER PRIMARY KEY AUTOINCREMENT,text  VARCHAR (1024),title VARCHAR (1024));";

        db.execSQL(surface);
        // 注：数据库实际上是没被创建 / 打开的（因该方法还没调用）
        // 直到getWritableDatabase() / getReadableDatabase() 第一次被调用时才会进行创建 / 打开
    }

    /**
     * 复写onUpgrade（）
     * 调用时刻：当数据库升级时则自动调用（即 数据库版本 发生变化时）
     * 作用：更新数据库表结构
     * 注：创建SQLiteOpenHelper子类对象时,必须传入一个version参数，该参数 = 当前数据库版本, 若该版本高于之前版本, 就调用onUpgrade()
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db ： 数据库  oldVersion ： 旧版本数据库  newVersion ： 新版本数据库
        String sql = "alter table notepad add phono varchar(64)";
        db.execSQL(sql);
    }
}
