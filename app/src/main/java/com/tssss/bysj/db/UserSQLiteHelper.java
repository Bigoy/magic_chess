package com.tssss.bysj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class UserSQLiteHelper extends SQLiteOpenHelper {
    private String id;
    private String dbName;


    public UserSQLiteHelper(Context context, @NonNull String id) {
        super(context, id + "_db", null, 1);
        this.id = id;
        dbName = id + "_db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (ID VERCHAR PRIMARY KEY, PASSWORD VERCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
