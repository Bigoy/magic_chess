package com.tssss.bysj.game.im;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;

import androidx.annotation.NonNull;

public class ChatSendViewHolder extends BaseRvViewHolder<ChatMessage> {
    private ImageView sendAvatorIv;
    private GTextView sendMessageTv;
    private GTextView sendTimeTv;

    public ChatSendViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void instantiateObject() {

    }

    @Override
    public void fillData(ChatMessage data) {
        Glide.with(getContext())
                .load(data.getAvatarFile())
                .into(sendAvatorIv);
        sendMessageTv.setText(data.getMessage());
        sendTimeTv.setText(data.getTime());
    }

    @Override
    protected void findViews() {
        sendAvatorIv = findImageView(R.id.item_chat_send_head_iv);
        sendMessageTv = findGTextView(R.id.item_chat_send_msg_gtv);
        sendTimeTv = findGTextView(R.id.item_chat_send_time_gtv);
    }

    public void setListeners(ChatSendClickListener chatSendClickListener,String accountID) {
       if (null != chatSendClickListener) {
           sendAvatorIv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   chatSendClickListener.lookMyInfo(accountID);

               }
           });

       }
    }


    public interface ChatSendClickListener {
        void lookMyInfo(String accountID);

    }
}
