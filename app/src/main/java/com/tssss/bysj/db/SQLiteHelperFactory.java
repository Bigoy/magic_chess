package com.tssss.bysj.db;

import android.content.Context;

import androidx.annotation.NonNull;

public class SQLiteHelperFactory {
    private static SQLiteHelperFactory instance;

    private Context context;

    private SQLiteHelperFactory(Context context) {
        this.context = context;
    }

    public static synchronized SQLiteHelperFactory getInstance(@NonNull Context context) {
        if (null == instance) {
            instance = new SQLiteHelperFactory(context);

        }
        return instance;
    }


    /**
     * 一个用户的数据库
     *
     * @param accountID 账户id
     */
    public UserSQLiteHelper getUserSQLiteHelper(Context context, String accountID) {
        return new UserSQLiteHelper(context, accountID);
    }
}
