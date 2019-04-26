package com.tssss.bysj.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.game.im.Conversation;

import java.util.ArrayList;
import java.util.List;

public class ChatListTable {

    private static ChatListTable instance;
    private static final String TABLE_NAME = "CHAT_LIST";

    private SQLiteDatabase chatListSQLiteDataBase;

    private ChatListTable(SQLiteDatabase sqLiteDatabase) {
        this.chatListSQLiteDataBase = sqLiteDatabase;
    }

    public static ChatListTable getInstance(SQLiteDatabase sqLiteDatabase) {
        if (null == instance) {
            instance = new ChatListTable(sqLiteDatabase);
        }
        return instance;
    }


    public void createChatListTable() {
        if (null != chatListSQLiteDataBase) {
            chatListSQLiteDataBase.execSQL("CREATE TABLE IF NOT EXISTS CHAT_LIST (ACCOUNT_ID INTEGER PRIMARY KEY, AVATAR VARCHAR, NAME VARCHAR, LAST_MESSAGE VARCHAR)");
        }
    }

    /**
     * 插入一条会话
     */
    public void addChatList(String accountID, String avatar, String name, String lastMessage) {
        if (null != chatListSQLiteDataBase) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("ACCOUNT_ID", Integer.valueOf(accountID));
            contentValues.put("AVATAR", avatar);
            contentValues.put("NAME", name);
            contentValues.put("LAST_MESSAGE", lastMessage);
            chatListSQLiteDataBase.insert(TABLE_NAME, null, contentValues);

        }
    }

    /**
     * 更新最新的消息
     */
    public void updateLastMessage(String accountID, String lastMessage) {
        if (null != chatListSQLiteDataBase) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("LAST_MESSAGE", lastMessage);
            chatListSQLiteDataBase.update(TABLE_NAME, contentValues, "ACCOUNT_ID=?", new String[]{accountID});

        }
    }

    /**
     * 删除所有最近聊天列表
     */
    public void deleteChatList(String accountID) {
        if (null != chatListSQLiteDataBase) {
            chatListSQLiteDataBase.execSQL("DELETE FROM CHAT_LIST");
        }
    }

    /**
     * 查询
     */
    public List<Conversation> queryChatList() {
        if (null != chatListSQLiteDataBase) {
            List<Conversation> conversationList = new ArrayList<>();
            Cursor cursor = chatListSQLiteDataBase.query(TABLE_NAME, null, "ACCOUNT_ID", null, null, null, null);
            int permit = 10;
            if (cursor.getCount() < 10) {
                permit = cursor.getCount();
            }
            for (int i = 0; i < permit; i++) {
                String accountID = String.valueOf(cursor.getInt(0));
                String avatar = cursor.getString(1);
                String name = cursor.getString(2);
                String lastMessage = cursor.getString(3);

//                conversationList.add(new Conversation(new GameRole(accountID, avatar, name, null, null), lastMessage));

            }
            cursor.close();
            return conversationList;
        }
        return null;

    }

    /**
     * 查询 id 是否存在
     */
    public boolean isConversionExsist(String id) {
        if (null != chatListSQLiteDataBase) {
            Cursor cursor = chatListSQLiteDataBase.query(TABLE_NAME, null, "ACCOUNT_ID", new String[]{id}, null, null, null);
            if (cursor.getCount() > 0) {
                return true;

            }
            cursor.close();

        }
        return false;
    }
}
