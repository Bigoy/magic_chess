package com.tssss.bysj.game.im;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

public class ChatPresenter extends BaseMvpPresenter<IChatContract.IView> implements IChatContract.IPresenter {

    private Handler handler;

    public ChatPresenter(IChatContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadChatHistory(String targetAccountID) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        Conversation conversation = JMessageClient.getSingleConversation(targetAccountID);
        if (null == conversation) {
            conversation = Conversation.createSingleConversation(targetAccountID);
        }
        List<Message> messageList = conversation.getAllMessage();
        for (int i = 0; i < messageList.size(); i++) {
            Message message = messageList.get(i);
            // 过滤游戏邀请message
            Map<String, String> contentMap = (Map<String, String>) JSON.parse(message.getContent().toJson());
            String messageText = contentMap.get("text");
            if (!StringUtil.isBlank(messageText)) {
                ContentType contentType = message.getContentType();
                if (contentType != ContentType.text) {
                    throw new IllegalStateException("不支持的消息类型");
                }
                UserInfo userInfo = message.getFromUser();
                String targetID = userInfo.getUserName();
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessage(messageText);
                if (targetID.equals(targetAccountID)) {
                    chatMessage.setMessageFrom(ChatMessage.MESSAGE_NOT_ME);
                    chatMessage.setAvatarFile(userInfo.getAvatarFile());
                } else {
                    chatMessage.setMessageFrom(ChatMessage.MESSAGE_ME);
                    chatMessage.setAvatarFile(JMessageClient.getMyInfo().getAvatarFile());
                }
                chatMessage.setFromAccountID(targetAccountID);
                chatMessage.setTime(message.getContent().getStringExtra("msg_time"));
                chatMessageList.add(chatMessage);
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().showHistory(chatMessageList);
            }
        });
    }

    @Override
    protected IChatContract.IView getEmptyView() {
        return IChatContract.emptyView;
    }
}
