package com.tssss.bysj.game.im;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.event.MessageEvent;

@ViewInject(layoutId = R.layout.activity_chat)
public class ChatActivity extends BaseActivity implements OnMenuItemClickListener {
    private ImageButton sendMsg;
    private RecyclerView chatRv;
    private EditText chatMsgEt;
    private GTextView noHistoryTv;

    private Menu menu;
    private List<ChatMessage> messageList;
    private ChatRvAdapter adapter;

    @Override
    protected void findViews() {
        sendMsg = findViewById(R.id.send_msg_ib);
        chatRv = findViewById(R.id.chat_rv);
        chatMsgEt = findViewById(R.id.chat_msg_et);
        noHistoryTv = findViewById(R.id.chat_no_history_tv);
    }

    @Override
    protected void setEventListeners() {
        sendMsg.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        messageList = new ArrayList<>();
        adapter = new ChatRvAdapter(this, messageList);
        if (messageList.size() <= 0) {
            chatRv.setVisibility(View.GONE);
            noHistoryTv.setVisibility(View.VISIBLE);
        } else {
            chatRv.setLayoutManager(new LinearLayoutManager(this));
            chatRv.setAdapter(adapter);
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
        String msg = chatMsgEt.getText().toString();
        if (StringUtil.isBlank(msg)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .desc("提示")
                    .subDesc("发送的消息内容不能为空！")
                    .operationType(AlertDialog.OPERATION_TYPE_OK);
            builder.display();
        } else {
            noHistoryTv.setVisibility(View.GONE);
            chatRv.setVisibility(View.VISIBLE);
            if (null == chatRv.getAdapter()) {
                chatRv.setLayoutManager(new LinearLayoutManager(this));
                chatRv.setAdapter(adapter);
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageFrom(ChatMessage.MESSAGE_SEND);
            chatMessage.setUserAvatar(R.drawable.head_img_test);
            chatMessage.setTime(SystemUtil.getCurrentTime());
            chatMessage.setMessage(msg);
            messageList.add(chatMessage);
            adapter.notifyItemInserted(adapter.getItemCount());
            adapter.notifyDataSetChanged();
            chatRv.smoothScrollToPosition(adapter.getItemCount());
            chatMsgEt.setText("");
        }

    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                menu.dismiss();
                if (messageList.size() != adapter.getItemCount()) {
                    return;
                } else {
                    if (adapter.getItemCount() <= 0) {
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
                                        AnimationUtil.flipView(ChatActivity.this, noHistoryTv);
                                        messageList.clear();
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

    public void onEvent(MessageEvent event) {
        /*Message message = event.getMessage();
        ChatMessage chatMessage = new ChatMessage();
        ContentType contentType = message.getContentType();
        if (contentType == ContentType.text) {
            chatMessage.setTime(String.valueOf(message.getCreateTime()));
            chatMessage.setMessage(message.getContent().getStringExtras());
            JMessageClient.createSingleTextMessage()
        }*/

    }
}
