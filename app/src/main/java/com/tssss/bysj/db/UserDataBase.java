package com.tssss.bysj.db;

import android.content.Context;

import com.tssss.bysj.db.table.ChatHistoryTable;
import com.tssss.bysj.db.table.ChatListTable;

public class UserDataBase {
    private static UserDataBase instance;

    private Context context;
    private String id;

    private UserDataBase(Context context, String id) {
        this.context = context;
        this.id = id;
    }

    public static synchronized UserDataBase getInstance(Context context, String id) {
        if (null == instance) {
            instance = new UserDataBase(context, id);

        }
        return instance;
    }


    public ChatHistoryTable getHistoryTable() {
        return ChatHistoryTable.getInstance(new UserSQLiteHelper(context, id).getWritableDatabase());
    }

    public ChatListTable getChatListTable() {
        return ChatListTable.getInstance(new UserSQLiteHelper(context, id).getWritableDatabase());
    }
}
