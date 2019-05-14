package com.tssss.bysj.other.jmessage;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.core.view.GameActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.other.jmessage.callback.ILoginCallBack;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.IntentUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

@SuppressWarnings("unchecked")
public class JMessageManager {

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
                String extra = content.getStringExtra("game_invitation");
                if (StringUtil.isBlank(extra)) {
                    IntentUtil.startChatIntent(activity, targetID);
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
                    switch (extars) {
                        case "game_invitation":
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                                    .subDesc(targetID + " 请求和你来一局游戏")
                                    .noDesc("拒绝")
                                    .okDesc("可以")
                                    .operationListener(new AlertDialog.OnDialogOperationListener() {
                                        @Override
                                        public void ok() {

                                            Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
                                            if (mConversation == null) {
                                                mConversation = Conversation.createSingleConversation(targetID, null);
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
                                            Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
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

                            break;
                        case "ok":

                            Conversation mConversation = JMessageClient.getSingleConversation(targetID, null);
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


                            break;
                        case "no":
                            ToastUtil.showToast(activity, "对方拒绝了你的游戏请求", ToastUtil.TOAST_ERROR);
                            break;
                        case "start_game":

                            Map<String, String> map = (Map<String, String>) JSON.parse(message.getFromUser().getSignature());
                            Intent gameIntent = new Intent(activity, GameActivity.class);
                            gameIntent.putExtra(Constant.ACCOUNT_ID, map.get(Constant.ACCOUNT_ID));

                            activity.startActivity(gameIntent);
                            activity.finish();
                            break;
                    }
                }
            }
        }
    }

    public static void addFriend(String targetUserAccountID, AddFriendCallBack addFriendCallBack) {
        if (null != addFriendCallBack) {
            addFriendCallBack.requesting();
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
                        Logger.log("发送文本消息成功" + "\n" + content);
                    } else if (i == 899017) {
                        Logger.log("发送文本消息失败：" + i + s);
                        callBack.onFailure("你被拉黑了！");

                    } else {
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

    public static void addUserToBlackList(String accountID, IAddBlackListCallBack callBack) {
        List<String> accountIDList = new ArrayList<>();
        accountIDList.add(accountID);
        addUsersToBlackList(accountIDList, callBack);
    }

    public static void addUsersToBlackList(List<String> accountIDList, IAddBlackListCallBack callBack) {
        JMessageClient.addUsersToBlacklist(accountIDList, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (null != callBack) {
                    if (i == 0) {
                        callBack.onSuccess();
                    } else {
                        String errorMsg = "加入失败，请重试！";
                        callBack.onError(errorMsg);
                        Logger.log("加入黑名单失败: " + s);
                    }
                }
            }
        });
    }

    public static void login(String accountID, String password, ILoginCallBack callBack) {
        JMessageClient.login(accountID, password, new RequestCallback<List<DeviceInfo>>() {
            @Override
            public void gotResult(int i, String s, List<DeviceInfo> deviceInfos) {
                if (i == 0) {
                    loginSuccess(callBack);

                } else if (i == 871105 || i == 898002 || i == 801003) {
                    // 用户不存在
                    register(accountID, password, callBack);

                } else if (i == 871304 || i == 801004) {
                    callBack.onPasswordError();

                }
            }
        });
    }

    private static void loginSuccess(@NonNull ILoginCallBack callBack) {
        // 保存用户输入的账户和密码 账户与密码匹配
        UserInfo userInfo = JMessageClient.getMyInfo();
        String roleInfo = userInfo.getSignature();
        if (StringUtil.isBlank(roleInfo)) {
            callBack.onNullRole();
        } else {
            GameRole myRole = JMessageHelper.toGameRole(userInfo);
            callBack.onLoginSuccess(myRole);
        }

    }

    private static void loginFailed(@NonNull ILoginCallBack callBack) {
        callBack.onLoginFailed("登录失败");
    }

    private static void register(String accountID, String password, ILoginCallBack callBack) {
        JMessageClient.register(accountID, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    JMessageClient.login(accountID, password, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                loginSuccess(callBack);

                            } else {
                                loginFailed(callBack);

                            }
                        }
                    });

                } else {
                    loginFailed(callBack);

                }
            }
        });
    }

    public interface IAddBlackListCallBack {
        void onSuccess();

        void onError(String errorMsg);
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
