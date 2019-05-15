package com.tssss.bysj.other.jmessage;


import com.tssss.bysj.other.Constant;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.content.TextContent;

public class TextContentFactory {
    public static TextContent gameInvitation(String text) {
        TextContent textContent = new TextContent(text);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "game_invitation");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent grantGameInvitation(String text) {
        TextContent textContent = new TextContent(text);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "game_invitation_grant");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent denyGameInvitation(String text) {
        TextContent textContent = new TextContent(text);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "game_invitation_deny");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent notifyAdversityStartGame() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "start_game");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent notFirst() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "not_first");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent chat(String text) {
        TextContent textContent = new TextContent(text);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "chat");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent chessmanPosition(String position) {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "chessman_position");
        map.put("chessman_position", position);
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent win() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "win");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent lose() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "lose");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent peace() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "peace");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent peaceGrant() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "peace_grant");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent peaceDeny() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "peace_deny");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent stepBack() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "step_back");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent urge() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "being_urge");
        textContent.setExtras(map);
        return textContent;
    }

    public static TextContent turn() {
        TextContent textContent = new TextContent("");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.MESSAGE_TYPE, "game");
        map.put(Constant.MESSAGE_GAME_OPERATION, "turn");
        textContent.setExtras(map);
        return textContent;
    }
}
