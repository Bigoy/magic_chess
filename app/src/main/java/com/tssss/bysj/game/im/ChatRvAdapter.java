package com.tssss.bysj.game.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRvAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ChatMessage> messages;

    public ChatRvAdapter(Context context, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
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
        if (holder instanceof BaseRvViewHolder) {
            BaseRvViewHolder viewHolder = (BaseRvViewHolder) holder;
            viewHolder.fillData(messages.get(position));
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
