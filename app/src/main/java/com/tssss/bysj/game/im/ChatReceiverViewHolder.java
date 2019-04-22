package com.tssss.bysj.game.im;

import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;

import androidx.annotation.NonNull;

public class ChatReceiverViewHolder extends BaseRvViewHolder<ChatMessage> {
    private ImageView receiverAvatorIv;
    private GTextView receiverMessageTv;
    private GTextView receiverTimeTv;

    public ChatReceiverViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void instantiateObject() {

    }

    @Override
    public void fillData(ChatMessage data) {
        receiverAvatorIv.setImageResource(data.getUserAvatar());
        receiverMessageTv.setText(data.getMessage());
        receiverTimeTv.setText(data.getTime());
    }

    @Override
    protected void findViews() {
        receiverAvatorIv = findImageView(R.id.item_chat_receive_head_iv);
        receiverMessageTv = findGTextView(R.id.item_chat_receive_msg_gtv);
        receiverTimeTv = findGTextView(R.id.item_chat_receive_time_gtv);
    }
}
