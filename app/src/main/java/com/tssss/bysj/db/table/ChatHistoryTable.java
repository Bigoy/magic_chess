package com.tssss.bysj.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tssss.bysj.game.im.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatHistoryTable {
    private static ChatHistoryTable instance;
    private static final String TABLE_NAME = "CHAT_HISTORY";

    private SQLiteDatabase userDataBase;

    private ChatHistoryTable(SQLiteDatabase database) {
        this.userDataBase = database;
    }

    public static synchronized ChatHistoryTable getInstance(SQLiteDatabase database) {
        if (null == instance) {
            instance = new ChatHistoryTable(database);

        }
        return instance;
    }


    /**
     * 创建表
     */
    public void createChatHistoryTable() {
        if (null == userDataBase) {
            userDataBase.execSQL("CREATE TABLE IF NOT EXISTS CHAT_HISTORY (ACCOUNT_ID INTEGER PRIMARY KEY AUTOINCREMENT, AVATAR VARCHAR, MESSAGE VARCHAR, TIME VARCHAR, RANK INTEGER, SENDER INTEGER)");
        }

    }

    /**
     * 添加历史记录
     */
    public void addMessage(String id, String message, String time, int sender, String avatar) {
        if (null == userDataBase) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ACCOUNT_ID", id);
            contentValues.put("MESSAGE", message);
            contentValues.put("TIME", time);
            contentValues.put("RANK", count(id) + 1);
            contentValues.put("SENDER", sender);
            contentValues.put("AVATAR", avatar);
            userDataBase.insert(TABLE_NAME, null, contentValues);

        }
    }

    /**
     * 删除一条历史记录
     */
    public void deleteMessage(String id, String message) {
        if (null != userDataBase) {
            userDataBase.delete(TABLE_NAME, "MESSAGE=?", new String[]{message});

        }
    }

    /**
     * 删除所有的历史记录
     */
    public void deleteAllMessages(String id) {
        if (null != userDataBase) {
            userDataBase.delete(TABLE_NAME, "ACCOUNT_ID=?", new String[]{id});

        }
    }

    /**
     * 查询历史记录总数
     */
    public int count(String id) {
        if (null != userDataBase) {
            int count = 0;
            Cursor cursor = userDataBase.query(TABLE_NAME, null, null, null, null, null, null);
            count = cursor.getCount();
            cursor.close();
            return count;
        }
        return 0;
    }

    /**
     * 查询历史记录
     */
    public List<ChatMessage> queryMessages(String id) {
        if (null != userDataBase) {
            List<ChatMessage> conversationList = new ArrayList<>();
            Cursor cursor = userDataBase.query(TABLE_NAME, null, "ACCOUNT_ID=?", new String[]{id}, null, null, "RANK");
            int permit = 10;
            if (cursor.getCount() < 10) {
                permit = cursor.getCount();
            }
            for (int i = 0; i < permit; i++) {
                int messageFrom = cursor.getInt(5);
                String userAvatar = cursor.getString(1);
                String message = cursor.getString(2);
                String time = cursor.getString(3);
                conversationList.add(new ChatMessage(messageFrom, userAvatar, message, time));

            }
            cursor.close();
            return conversationList;
        }
        return null;
    }


}
