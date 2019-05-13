package com.tssss.bysj.other.jmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.core.view.GameActivity;
import com.tssss.bysj.game.im.ChatActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

public class JMessageManager {
    private static Handler handler;
    private static AlertDialog.Builder builder;

    public JMessageManager() {
        handler = new Handler();

    }

    public static void registerEvent(BaseActivity context) {
        JMessageClient.registerEventReceiver(context);
    }

    public static void unregisterEvent(BaseActivity context) {
        JMessageClient.unRegisterEventReceiver(context);
    }

    /**
     * 处理通知栏消息事件的标准逻辑
     */
    public static void handlerNotificationEvent(NotificationClickEvent event, Activity activity, boolean handlerChatMsg) {
        Message message = event.getMessage();
        if (null != message) {
            MessageContent content = message.getContent();
            if (null != content) {
                String targetID = message.getFromUser().getUserName();
                // 游戏邀请message
                String extars = content.getStringExtra("game_invitation");
                if (!StringUtil.isBlank(extars)) {
                    /*if ("game_invitation".equals(extars)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                                .subDesc(targetID + " 请求和你来一局游戏")
                                .noDesc("拒绝")
                                .okDesc("可以")
                                .operationListener(new AlertDialog.OnDialogOperationListener() {
                                    @Override
                                    public void ok() {

                                        cn.jpush.im.android.api.model.Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                                        if (mConversation == null) {
                                            mConversation = cn.jpush.im.android.api.model.Conversation.createSingleConversation(targetID, null);
                                        }

                                        //构造message content对象
                                        TextContent textContent = new TextContent("对方同意了你的请求" + "\n" + "准备开始游戏");
                                        textContent.setStringExtra("game_invitation", "ok");

                                        //创建message实体，设置消息发送回调。
                                        final Message message = mConversation.createSendMessage(textContent, targetID);
                                        JMessageClient.sendMessage(message);
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity)
                                                .operationType(AlertDialog.OPERATION_TYPE_NO)
                                                .desc("请等待对方开始");
                                        builder1.display();
                                    }

                                    @Override
                                    public void no() {
                                        cn.jpush.im.android.api.model.Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                                        if (mConversation == null) {
                                            mConversation = Conversation.createSingleConversation(targetID, null);
                                        }

                                        //构造message content对象
                                        TextContent textContent = new TextContent("对方拒绝了你的游戏请求");
                                        textContent.setStringExtra("game_invitation", "no");

                                        //创建message实体，设置消息发送回调。
                                        final Message message = mConversation.createSendMessage(textContent, targetID);
                                        JMessageClient.sendMessage(message);
                                    }
                                });
                        builder.display();

                    } else if ("ok".equals(extars)) {
                        Map<String, String> map = (Map<String, String>) JSON.parse(message.getFromUser().getSignature());
                        Intent gameIntent = new Intent(activity, GameActivity.class);
                        gameIntent.putExtra(Constant.ACCOUNT_ID, map.get(Constant.ACCOUNT_ID));
                        activity.startActivity(gameIntent);
                        cn.jpush.im.android.api.model.Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                        if (mConversation == null) {
                            mConversation = Conversation.createSingleConversation(targetID, null);
                        }

                        //构造message content对象
                        TextContent textContent = new TextContent("");
                        textContent.setStringExtra("game_invitation", "start_game");

                        //创建message实体，设置消息发送回调。
                        final Message message1 = mConversation.createSendMessage(textContent, targetID);
                        JMessageClient.sendMessage(message1);

                    } else if ("no".equals(extars)) {
                        ToastUtil.showToast(activity, "对方拒绝了你的游戏请求", ToastUtil.TOAST_ERROR);
                    } else if ("start_game".equals(extars)) {

                        Map<String, String> map = (Map<String, String>) JSON.parse(message.getFromUser().getSignature());
                        Intent gameIntent = new Intent(activity, GameActivity.class);
                        gameIntent.putExtra(Constant.ACCOUNT_ID, map.get(Constant.ACCOUNT_ID));
                        activity.startActivity(gameIntent);
                    }*/
                } else {
                    // 正常的聊天事件
                    if (handlerChatMsg) {
                        Intent chatIntent = new Intent(activity, ChatActivity.class);
                        chatIntent.putExtra(Constant.ACCOUNT_ID, targetID);
                        activity.startActivity(chatIntent);

                    }
                }
            }
        }

    }


    public static void handlerMessageEvent(MessageEvent event, Activity activity) {
        Message message = event.getMessage();
        if (null != message) {
            MessageContent content = message.getContent();
            if (null != content) {
                String targetID = message.getFromUser().getUserName();
                // 游戏邀请message
                String extars = content.getStringExtra("game_invitation");
                if (!StringUtil.isBlank(extars)) {
                    if (true) {
                        if ("game_invitation".equals(extars)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                                    .subDesc(targetID + " 请求和你来一局游戏")
                                    .noDesc("拒绝")
                                    .okDesc("可以")
                                    .operationListener(new AlertDialog.OnDialogOperationListener() {
                                        @Override
                                        public void ok() {

                                            cn.jpush.im.android.api.model.Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                                            if (mConversation == null) {
                                                mConversation = cn.jpush.im.android.api.model.Conversation.createSingleConversation(targetID, null);
                                            }

                                            //构造message content对象
                                            TextContent textContent = new TextContent("对方同意了你的请求" + "\n" + "准备开始游戏");
                                            textContent.setStringExtra("game_invitation", "ok");

                                            //创建message实体，设置消息发送回调。
                                            final Message message = mConversation.createSendMessage(textContent, targetID);
                                            JMessageClient.sendMessage(message);
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(activity)
                                                    .operationType(AlertDialog.OPERATION_TYPE_SIMPLE)
                                                    .desc("请等待对方开始");
                                            builder1.display();
                                        }

                                        @Override
                                        public void no() {
                                            cn.jpush.im.android.api.model.Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                                            if (mConversation == null) {
                                                mConversation = Conversation.createSingleConversation(targetID, null);
                                            }

                                            //构造message content对象
                                            TextContent textContent = new TextContent("对方拒绝了你的游戏请求");
                                            textContent.setStringExtra("game_invitation", "no");

                                            //创建message实体，设置消息发送回调。
                                            final Message message = mConversation.createSendMessage(textContent, targetID);
                                            JMessageClient.sendMessage(message);
                                        }
                                    });
                            builder.display();

                        } else if ("ok".equals(extars)) {

                            cn.jpush.im.android.api.model.Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                            if (mConversation == null) {
                                mConversation = Conversation.createSingleConversation(targetID, null);
                            }

                            //构造message content对象
                            TextContent textContent = new TextContent("");
                            textContent.setStringExtra("game_invitation", "start_game");

                            //创建message实体，设置消息发送回调。
                            final Message message1 = mConversation.createSendMessage(textContent, targetID);
                            message1.setOnSendCompleteCallback(new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    if (i == 0) {
                                        Map<String, String> map = (Map<String, String>) JSON.parse(message.getFromUser().getSignature());
                                        Intent gameIntent = new Intent(activity, GameActivity.class);
                                        gameIntent.putExtra(Constant.ACCOUNT_ID, map.get(Constant.ACCOUNT_ID));
                                        activity.startActivity(gameIntent);
                                        activity.finish();
                                    }
                                }
                            });
                            JMessageClient.sendMessage(message1);


                        } else if ("no".equals(extars)) {
                            ToastUtil.showToast(activity, "对方拒绝了你的游戏请求", ToastUtil.TOAST_ERROR);
                        } else if ("start_game".equals(extars)) {

                            Map<String, String> map = (Map<String, String>) JSON.parse(message.getFromUser().getSignature());
                            Intent gameIntent = new Intent(activity, GameActivity.class);
                            gameIntent.putExtra(Constant.ACCOUNT_ID, map.get(Constant.ACCOUNT_ID));

                            activity.startActivity(gameIntent);
                            activity.finish();
                        }
                    }
                }
            }
        }
    }

    public static void addFriend(String targetUserAccountID, AddFriendCallBack addFriendCallBack) {
        if (null != addFriendCallBack) {
            addFriendCallBack.requesting();

        }
        ContactManager.sendInvitationRequest(targetUserAccountID,
                Constant.JMESSAGE_APP_KEY,
                UserDataCache.readAccount("id 为 " + Constant.ACCOUNT_ID) + " 的玩家希望和你成为朋友", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            addFriendCallBack.success();


                        } else if (i == 898002) {
                            addFriendCallBack.notUser();


                        } else if (i == 805002) {
                            addFriendCallBack.isFriend();


                        } else if (i == 871317) {
                            addFriendCallBack.fail("不能添加自己哦!");

                        } else {
                            addFriendCallBack.fail(s);
                            Logger.log(i + s);

                        }
                    }
                });
    }

    /**
     * 发送JMessage文本消息 无回调
     */
    public static void sendTextMessage(String targetId, String content) {
        sendTextMessage(targetId, content, null);

    }

    /**
     * 发送JMessage文本消息
     */
    public static void sendTextMessage(String targetId, String content, OnSendCompleteCallBack callBack) {
        //通过username和appkey拿到会话对象，通过指定appkey可以创建一个和跨应用用户的会话对象，从而实现跨应用的消息发送
        Conversation conversation = JMessageClient.getSingleConversation(targetId);
        if (conversation == null) {
            conversation = Conversation.createSingleConversation(targetId);
        }
        //构造message content对象
        TextContent textContent = new TextContent(content);
        //创建message实体，设置消息发送回调。
        final Message message = conversation.createSendMessage(textContent, targetId);
        if (null != callBack) {
            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        callBack.onSuccess();
                    }else {
                        Logger.log("发送文本消息失败：" + i + s);
                        callBack.onFailure(s);
                    }
                }
            });
        }
        MessageSendingOptions options = new MessageSendingOptions();
        options.setShowNotification(false);
        options.setRetainOffline(false);
        JMessageClient.sendMessage(message, options);
    }

    /**
     * 消息发送结果回调接口
     */
    public interface OnSendCompleteCallBack {
        void onSuccess();

        void onFailure(String errorMsg);

    }

    public interface AddFriendCallBack {
        void requesting();

        void success();

        void notUser();

        void fail(String errorMsg);

        void isFriend();

    }
}
