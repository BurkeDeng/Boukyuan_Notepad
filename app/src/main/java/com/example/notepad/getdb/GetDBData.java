package com.example.notepad.getdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * @author boukyuan
 * 封装查询数据库db
 */
public class GetDBData {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase dbWritableDatabase;
    String id, text, title;
    ArrayList<ArrayList<String>> arrayList = new ArrayList();

    public ArrayList<ArrayList<String>> getDBData(Context context) {
        dbHelper = new DatabaseHelper(context, "test_notepad.db", null, 1);
        dbWritableDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = dbWritableDatabase.query("notepad", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            ArrayList<String> info = new ArrayList();
            id = cursor.getString(cursor.getColumnIndex("id"));
            text = cursor.getString(cursor.getColumnIndex("text"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            System.out.println("id为 " + id + "标题为  " + text + "内容为 " + title);
            info.add(id);
            info.add(text);
            info.add(title);
            arrayList.add(info);
        }
        cursor.close();
        return arrayList;
    }
}
