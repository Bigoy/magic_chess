package com.tssss.bysj.game.im;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

public class ChatPresenter extends BaseMvpPresenter<IChatContract.IView> implements IChatContract.IPresenter {

    private Handler handler;

    public ChatPresenter(IChatContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadChatHistory(String targetAccountID) {
       /* // 从本地数据库加载聊天历史记录
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getEmptyView().showHistory(null);
            }
        }, 1000);*/
        List<ChatMessage> chatMessageList = new ArrayList<>();
        Conversation conversation = JMessageClient.getSingleConversation(targetAccountID);
        if (null == conversation) {
            conversation = Conversation.createSingleConversation(targetAccountID);
        }
        List<Message> messageList = conversation.getAllMessage();
        for (int i = 0; i < messageList.size(); i++) {
            Message message = messageList.get(i);

            // 过滤游戏邀请message
            if (StringUtil.isBlank(message.getContent().getStringExtra("game_invitation"))) {
                ChatMessage chatMessage = new ChatMessage();
                String msgJson = message.toJson();
                Map<String, String> msgMap = (Map<String, String>) JSON.parse(msgJson);
                Map<String, String> contentMap = (Map<String, String>) JSON.parse(message.getContent().toJson());
                chatMessage.setMessage(contentMap.get("text"));
                if (msgMap.get("from_id").equals(targetAccountID)) {
                    chatMessage.setMessageFrom(ChatMessage.MESSAGE_NOT_ME);
                } else {
                    chatMessage.setMessageFrom(ChatMessage.MESSAGE_ME);
                }
                chatMessage.setTime(message.getContent().getStringExtra("msg_time"));
//            chatMessage.setUserAvatar(message.getFromUser().getAvatarStr());
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
