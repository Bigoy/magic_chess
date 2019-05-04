package com.tssss.bysj.game.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRvAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ChatMessage> messages;
    private ChatSendViewHolder.ChatSendClickListener chatSendClickListener;
    private ChatReceiverViewHolder.OnChatReceiveClickListener chatReceiveClickListener;

    public ChatRvAdapter(Context context, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    public void setChatSendClickListener(ChatSendViewHolder.ChatSendClickListener chatSendClickListener) {
        this.chatSendClickListener = chatSendClickListener;

    }

    public void setChatReceiveClickListener(ChatReceiverViewHolder.OnChatReceiveClickListener chatReceiveClickListener) {
        this.chatReceiveClickListener = chatReceiveClickListener;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == ChatMessage.MESSAGE_ME) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_chat_send, parent, false);
            return new ChatSendViewHolder(itemView);
        } else if (viewType == ChatMessage.MESSAGE_NOT_ME) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_chat_receive, parent, false);
            return new ChatReceiverViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatSendViewHolder) {
            ((ChatSendViewHolder) holder).fillData(messages.get(position));
            ((ChatSendViewHolder) holder).setListeners(chatSendClickListener, UserDataCache.readAccount(Constant.ACCOUNT_ID));
        } else if (holder instanceof ChatReceiverViewHolder) {
            ((ChatReceiverViewHolder) holder).fillData(messages.get(position));
            ((ChatReceiverViewHolder) holder).setListeners(chatReceiveClickListener, messages.get(position).getFromAccountID());

        }
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getMessageFrom();
    }

    @Override
    public int getItemCount() {
        return messages.size();

    }

}
