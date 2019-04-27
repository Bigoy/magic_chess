package com.tssss.bysj.game.im;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.SystemUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

@ViewInject(layoutId = R.layout.activity_chat)
public class ChatActivity extends BaseActivity implements OnMenuItemClickListener,
        IChatContract.IView {
    private ImageButton sendMsg;
    private RecyclerView chatRv;
    private EditText chatMsgEt;
    private GTextView loadingTv;
    private GTextView targetName;

    private Menu menu;
    private List<ChatMessage> chatMessageList;
    private ChatRvAdapter adapter;
    private String targetAccountID;
    private String targetRoleName;
    private ChatPresenter presenter;
    private boolean historyLoaded;
    private Conversation conversation;
    private boolean succeed;

    @Override
    protected void findViews() {
        sendMsg = findViewById(R.id.send_msg_ib);
        chatRv = findViewById(R.id.chat_rv);
        chatMsgEt = findViewById(R.id.chat_msg_et);
        loadingTv = findViewById(R.id.chat_loading_tv);
        targetName = findViewById(R.id.chat_name_gtv);
    }

    @Override
    protected void setEventListeners() {
        sendMsg.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        JMessageClient.registerEventReceiver(this);
        chatMessageList = new ArrayList<>();
        initAdapter();
        chatRv.setLayoutManager(new LinearLayoutManager(this));
        chatRv.setAdapter(this.adapter);
        presenter = new ChatPresenter(this);
        Intent chatIntent = getIntent();
        if (null != chatIntent) {
            this.targetAccountID = chatIntent.getStringExtra(Constant.ACCOUNT_ID);
            JMessageClient.getUserInfo(targetAccountID, new GetUserInfoCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
                        String name = map.get(Constant.ROLE_NICK_NAME);
                        targetName.setText("对方是：" + name);
                    }
                }
            });
            presenter.loadChatHistory(this.targetAccountID);
        }

    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.chat_title;
    }

    @Override
    protected void clickTopBarRight() {
        super.clickTopBarRight();
        List<String> items = new ArrayList<>();
        items.add("清空与TA的聊天记录");
        items.add("查看更多历史记录");
        items.add("加入黑名单");
        menu = new Menu.Builder(this, this)
                .items(items)
                .build();
        menu.display();
    }

    @Override
    protected void clickTopBarCenter() {
        super.clickTopBarCenter();
        chatRv.smoothScrollToPosition(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_msg_ib:
                sendMsg();
                break;
            default:
        }
    }

    private void sendMsg() {
        succeed = false;
        String msg = chatMsgEt.getText().toString();
        if (StringUtil.isBlank(msg)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .desc("提示")
                    .subDesc("发送的消息内容不能为空！")
                    .operationType(AlertDialog.OPERATION_TYPE_OK);
            builder.display();
        } else {

            //通过username和appkey拿到会话对象，通过指定appkey可以创建一个和跨应用用户的会话对象，从而实现跨应用的消息发送
            Conversation mConversation = JMessageClient.getSingleConversation(targetAccountID, null);
            if (mConversation == null) {
                mConversation = Conversation.createSingleConversation(targetAccountID, null);
            }

            //构造message content对象
            TextContent textContent = new TextContent(chatMsgEt.getText().toString());
            textContent.setStringExtra("msg_time", SystemUtil.getCurrentTime());

            //创建message实体，设置消息发送回调。
            final Message message = mConversation.createSendMessage(textContent, targetAccountID);
            message.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i != 0) {
                        ToastUtil.showToast(ChatActivity.this, "发送失败", ToastUtil.TOAST_ERROR);

                    } else {
                        succeed = true;
                        Logger.log("send msg = " + succeed);
                        updateMsg(msg);
                    }
                }
            });

            MessageSendingOptions options = new MessageSendingOptions();
            options.setShowNotification(true);
            options.setRetainOffline(true);
            JMessageClient.sendMessage(message, options);

        }

    }

    private void updateMsg(String msg) {
        if (succeed) {
            loadingTv.setVisibility(View.GONE);
            chatRv.setVisibility(View.VISIBLE);
            if (null == chatRv.getAdapter()) {
                chatRv.setLayoutManager(new LinearLayoutManager(this));
                chatRv.setAdapter(adapter);
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageFrom(ChatMessage.MESSAGE_ME);
            chatMessage.setUserAvatar("");
            chatMessage.setTime(SystemUtil.getCurrentTime());
            chatMessage.setMessage(msg);
            if (this.chatMessageList == null) {
                chatMessageList = new ArrayList<>();

            }
            chatMessageList.add(chatMessage);
            loadingTv.setVisibility(View.GONE);
            chatRv.setVisibility(View.VISIBLE);
//            adapter.notifyItemInserted(adapter.getItemCount());
            adapter.notifyDataSetChanged();
            chatRv.smoothScrollToPosition(adapter.getItemCount());
            chatMsgEt.setText("");
        }
    }

    private void initAdapter() {
        this.adapter = new ChatRvAdapter(this, chatMessageList);
    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                menu.dismiss();
                if (chatMessageList.size() != adapter.getItemCount()) {
                    return;
                } else {
                    if (null == chatMessageList || chatMessageList.size() <= 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .desc("清空聊天记录")
                                .subDesc("记录已经为空")
                                .operationType(AlertDialog.OPERATION_TYPE_OK);
                        builder.display();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .desc("清空聊天记录")
                                .subDesc("记录是唯一让过去看得见的东西！" + "\n" +
                                        "这次删除了就永远没有了！")
                                .okDesc("坚持清空")
                                .operationListener(new AlertDialog.OnDialogOperationListener() {
                                    @Override
                                    public void ok() {
                                        adapter.notifyItemMoved(0, adapter.getItemCount());
                                        menu.dismiss();
                                        chatRv.setVisibility(View.GONE);
                                        loadingTv.setText("记录为空");
                                        AnimationUtil.flipView(ChatActivity.this, loadingTv);
                                        if (null != chatMessageList) {
                                            chatMessageList.clear();
                                        }
                                        JMessageClient.deleteSingleConversation(targetAccountID);
                                    }

                                    @Override
                                    public void no() {

                                    }
                                });
                        builder.display();
                    }

                }
                break;
            case 1:
                menu.dismiss();
                break;
            case 2:
                menu.dismiss();
                break;
            default:
        }
    }

    /**
     * 极光IM 接收在线消息
     */
    public void onEvent(MessageEvent event) {
        Message message = event.getMessage();
        ChatMessage newMessage = new ChatMessage();
        ContentType contentType = message.getContentType();
        if (contentType == ContentType.text) {
            newMessage.setMessageFrom(ChatMessage.MESSAGE_NOT_ME);
            String msgJson = message.getContent().toJson();
            Map<String, String> msgMap = (Map<String, String>) JSON.parse(msgJson);
//            Map<String, String> timeMap = (Map<String, String>) JSON.parse(msgMap.get("extras"));
            newMessage.setMessage(msgMap.get("text"));
            newMessage.setTime(message.getContent().getStringExtra("msg_time"));
            if (chatRv.getAdapter() == null) {
                chatRv.setLayoutManager(new LinearLayoutManager(this));
                chatRv.setAdapter(adapter);
            }
            if (null == chatMessageList) {
                chatMessageList = new ArrayList<>();
            }
            chatMessageList.add(newMessage);
            loadingTv.setVisibility(View.GONE);
            chatRv.setVisibility(View.VISIBLE);
            Logger.log("收到新的消息：" + newMessage.getMessage());
//        adapter.notifyItemChanged(adapter.getItemCount());
            adapter.notifyDataSetChanged();
            chatRv.smoothScrollToPosition(chatMessageList.size());
        }

    }

    @Override
    public void showHistory(List<ChatMessage> messageList) {
        historyLoaded = true;
        if (messageList == null || messageList.size() <= 0) {
            if (chatRv.getVisibility() != View.VISIBLE) {
                loadingTv.setVisibility(View.GONE);
                loadingTv.setText("没有任何聊天记录，快去发点儿什么给对方吧");
                AnimationUtil.flipView(this, loadingTv);
            }

        } else {
            if (chatRv.getVisibility() != View.VISIBLE) {
                loadingTv.setVisibility(View.GONE);
                chatRv.setVisibility(View.VISIBLE);
            }
            int startP = chatMessageList.size();
            this.chatMessageList.addAll(messageList);
            if (null == chatRv.getAdapter()) {
                chatRv.setLayoutManager(new LinearLayoutManager(this));
                chatRv.setAdapter(new ChatRvAdapter(this, chatMessageList));
            }
            adapter.notifyItemRangeInserted(startP, chatMessageList.size());
            adapter.notifyDataSetChanged();
            chatRv.smoothScrollToPosition(chatMessageList.size());
        }
    }

    public void onEvent(NotificationClickEvent event) {
        JMessageManager.handlerNotificationEvent(event, this, false);
    }
}
