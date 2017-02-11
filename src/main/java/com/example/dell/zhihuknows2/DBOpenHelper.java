package com.example.dell.zhihuknows2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2016/6/18.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE_LOVE = "create table Loves("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "summary text,"
            + "questionId text,"
            + "answerId text,"
            + "touxurl text)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
