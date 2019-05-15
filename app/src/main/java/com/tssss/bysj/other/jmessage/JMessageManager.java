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
import com.tssss.bysj.util.ContextUtil;
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
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

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

    public static void handlerMessageEvent(MessageEvent event) {
        Message message = event.getMessage();
        if (null != message) {
            if (message.getContentType() != ContentType.text) {
                throw new IllegalStateException("接受到不支持的非文本消息");
            }
            TextContent content = (TextContent) message.getContent();
            if (null != content) {
                Map<String, String> extrasMap = content.getStringExtras();
                String textMsgType = extrasMap.get(Constant.MESSAGE_TYPE);
                String msgFrom = message.getFromUser().getUserName();
                TextMessageContent textMessageContent = new TextMessageContent();
                textMessageContent.setTextContent(content);
                textMessageContent.setFrom(msgFrom);
                AbsTextMessageHandler textMessageHandler = null;
                switch (textMsgType) {
                    case "game":
                        textMessageHandler = new GameMessageHandler(textMessageContent);
                        break;
                    case "chat":
                        textMessageHandler = new ChatMessageHandler(textMessageContent);
                        break;
                }
                if (null == textMessageHandler) {
                    throw new NullPointerException("textMessageHandler is null");
                }
                textMessageHandler.handleTextMessage();
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

    public static void sendTextMessage(String targetId, TextContent textContent) {
        sendTextMessage(targetId, textContent, null);
    }

    public static void sendTextMessage(String targetId, TextContent textContent, OnSendCompleteCallBack callBack) {
        //通过username和appkey拿到会话对象，通过指定appkey可以创建一个和跨应用用户的会话对象，从而实现跨应用的消息发送
        Conversation conversation = JMessageClient.getSingleConversation(targetId);
        if (conversation == null) {
            conversation = Conversation.createSingleConversation(targetId);
        }
        //创建message实体，设置消息发送回调。
        final Message message = conversation.createSendMessage(textContent, targetId);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (null != callBack) {
                    if (i == 0) {
                        callBack.onSuccess();
                        String msg = "发送文本消息状态：成功" +
                                "\n" +
                                "文本内容：" +
                                textContent.getText();
                        Logger.log(msg);
                    } else if (i == 899017) {
                        Logger.log("发送文本消息失败：" + i + s);
                        callBack.onFailure("你被拉黑了！");

                    } else {
                        Logger.log("发送文本消息失败：" + i + s);
                        callBack.onFailure(s);
                    }
                }
            }
        });
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
