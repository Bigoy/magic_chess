package com.tssss.bysj.game.im;

import android.content.Intent;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.activity_chat_list)
public class ChatListActivity extends BaseActivity implements IChatListContract.IView,
        OnChatListItemClickListener {

    private GTextView loading;
    private RecyclerView listRv;
    private ChatListPresenter presenter;

    private List<Conversation> chatList;


    @Override
    protected void findViews() {
        loading = findViewById(R.id.chat_list_loading);
        listRv = findViewById(R.id.chat_list_rv);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void afterBindView() {
        presenter = new ChatListPresenter(this, this);
        presenter.loadChatList(UserDataCache.readAccount(Constant.ACCOUNT_ID));
    }

    @Override
    public void showNullList() {
        loading.setVisibility(View.GONE);
        loading.setText("还没有会话");
        AnimationUtil.flipView(this, loading);
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.chat_list_title;
    }

    @Override
    public void showChatList(List<Conversation> chatList) {
        if (null != chatList && chatList.size() > 0) {
            this.chatList = chatList;
            loading.setVisibility(View.GONE);
            listRv.setVisibility(View.VISIBLE);
            listRv.setLayoutManager(new LinearLayoutManager(this));
            listRv.setAdapter(new ChatListRvAdapter(this, this.chatList, this));
        }
    }

    @Override
    public void showLoadError(String errorMsg) {
        loading.setVisibility(View.GONE);
        loading.setText("加载出错啦");
        loading.setTextColor(0xFFA02B2B);
        AnimationUtil.flipView(this, loading);
    }

    @Override
    public void onChatListItemClick(View v, int position) {
        if (v.getId() == R.id.item_chat_list_container) {
            if (null != this.chatList) {
                Intent chatIntent = new Intent(this, ChatActivity.class);
                try {
                    Conversation conversation = this.chatList.get(position);
                    String userId = conversation.getRole().getUser().getUserId();
                    String roleName = conversation.getRole().getName();
                    if (Constant.DEBUG) {
                        userId = "DEBUG";
                        roleName = "DEBUG";

                    }
                    chatIntent.putExtra(Constant.ACCOUNT_ID, userId);
                    chatIntent.putExtra(Constant.ROLE_NICK_NAME, roleName);
                    startActivity(chatIntent);
                } catch (Exception e) {
                    Logger.log("会话列表数据错误");

                }
            }
        }
    }
}
