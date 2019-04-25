package com.tssss.bysj.db;


import android.content.Context;

public class SQLiteFactory {

    private static SQLiteFactory instance;

    private SQLiteFactory() {


    }

    public static synchronized SQLiteFactory getInstance() {
        if (null == instance) {
            instance = new SQLiteFactory();
        }
        return instance;

    }

    public UserDataBase getUserDataBase(Context context, String id) {
        return UserDataBase.getInstance(context, id);

    }
}
