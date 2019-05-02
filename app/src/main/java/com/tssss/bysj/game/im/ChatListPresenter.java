package com.tssss.bysj.game.im;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

public class ChatListPresenter extends BaseMvpPresenter<IChatListContract.IView>
        implements IChatListContract.IPresenter {

    private boolean cancelLoad;
    private Handler handler;
    private Context context;

    public ChatListPresenter(Context context, IChatListContract.IView view) {
        super(view);
        this.context = context;
        handler = new Handler();
    }

    @Override
    public void loadChatList(String id) {
        List<com.tssss.bysj.game.im.Conversation> conversations = new ArrayList<>();
        List<Conversation> conversationList = JMessageClient.getConversationList();
        if (null == conversationList || conversationList.size() <= 0) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().showNullList();
                }
            });
        } else {
            for (int i = 0; i < conversationList.size(); i++) {
                com.tssss.bysj.game.im.Conversation conversation = new com.tssss.bysj.game.im.Conversation();
                Conversation conversationJG = conversationList.get(i);
                conversation.setAvatar(conversationJG.getAvatarFile());
                conversation.setId(conversationJG.getId());
                Message lastMsg = conversationJG.getLatestMessage();
                if (null == lastMsg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().showNullList();
                        }
                    });
                } else {
                    JMessageClient.getUserInfo(conversationJG.getId(), new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            Logger.log(i + s);
                            if (i == 0) {
                                Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
                                conversation.setName(map.get(Constant.ROLE_NICK_NAME));
                                conversations.add(conversation);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        getView().showChatList(conversations);
                                    }
                                });
                            }
                        }
                    });
                }


            }
        }


    }


    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        cancelLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelLoad = true;
    }

    @Override
    public void destroyView() {
        super.destroyView();
        cancelLoad = true;
    }


    @Override
    protected IChatListContract.IView getEmptyView() {
        return IChatListContract.emptyView;
    }
}
