package com.tssss.bysj.game.im;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatListRvAdapter extends RecyclerView.Adapter<ChatListRvAdapter.ChatListViewHolder> {

    private Context context;
    private List<Conversation> chatList;
    private OnChatListItemClickListener listItemClickListener;

    public ChatListRvAdapter(@NonNull Context context,
                             @NonNull List<Conversation> chatList,
                             @NonNull OnChatListItemClickListener listItemClickListener) {
        this.context = context;
        this.chatList = chatList;
        this.listItemClickListener = listItemClickListener;

    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.fillData(chatList.get(position));
        holder.setItemOnClickListener(listItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return null == chatList ? 0 : chatList.size();
    }

    public static class ChatListViewHolder extends BaseRvViewHolder<Conversation> {
        private ImageView avatar;
        private TextView name;
        private TextView lastHistory;
        private LinearLayout container;

        private Handler handler;


        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {
            handler = new Handler();
        }

        @Override
        public void fillData(Conversation data) {
            if (null != data) {
                Role role = data.getRole();
                if (null != role) {
                    String avatarStr = role.getAvatar();
                    name.setText(role.getName());
                    lastHistory.setText(data.getLastChatHistory());
                    if (StringUtil.isBlank(avatarStr)) {
                        Glide.with(getContext())
                                .load(R.drawable.head_img_test)
                                .into(avatar);
                    } else {
                        Glide.with(getContext())
                                .load(avatarStr)
                                .into(avatar);

                    }
                }
            }
        }

        @Override
        protected void findViews() {
            avatar = findImageView(R.id.item_chat_list_avatar);
            name = findTextView(R.id.item_chat_list_name);
            lastHistory = findTextView(R.id.item_chat_list_last_history);
            container = findLinearLayout(R.id.item_chat_list_container);
        }


        public void setItemOnClickListener(OnChatListItemClickListener listener, int position) {
            if (null == container) {
                container = findLinearLayout(R.id.item_chat_list_container);

            }

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        AnimationUtil.startBackgroundColorAnimator(container, 0x00FFFFFF, 0xFFCBCBCB);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listener.onChatListItemClick(v, position);
                            }
                        }, 120);
                    }
                }
            });
        }

    }
}
