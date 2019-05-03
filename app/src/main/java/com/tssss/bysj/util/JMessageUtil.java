package com.tssss.bysj.util;

import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.im.android.api.model.UserInfo;

public class JMessageUtil {

    public static GameRole invertUserInfoToGameRole(UserInfo userInfo) {
        try {
            JSONObject jsonObject = new JSONObject(userInfo.getSignature());
            return new GameRole(
                    new User(userInfo.getUserName(), null),
                    userInfo.getAvatar(),
                    jsonObject.getString(Constant.ROLE_NICK_NAME),
                    jsonObject.getString(Constant.ROLE_SEX),
                    jsonObject.getString(Constant.ROLE_SIGNATURE),
                    jsonObject.getString(Constant.ROLE_LEVEL),
                    jsonObject.getInt(Constant.ROLE_EXP),
                    jsonObject.getInt(Constant.ROLE_SCORE)
            );
        } catch (JSONException e) {
            return null;
        }
    }

  /*  public static ChatMessage invertMessageToChatMessage(Message message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message.getContent().getStringExtra(Constant.JMESSAGE_KEY));
        return new ChatMessage()
    }*/
}
