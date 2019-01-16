package com.tssss.bysj.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnFriendsListener;
import com.tssss.bysj.presenter.FriendsPresenter;
import com.tssss.bysj.user.UserManager;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.widget.GTextView;

import java.util.List;

import androidx.annotation.Nullable;

@ContentView(R.layout.activity_friends)
public class FriendsActivity extends BaseActivity implements View.OnClickListener, OnFriendsListener {
    private ImageButton mFriendsBackIb, mSeekFriendsIb;
    private ImageView mFriendsNullIv;
    private GTextView mFriendsNumberGtv;

    private FriendsPresenter friendsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendsPresenter.loadFriends(
                UserManager.getUserManager().getUser().getUserId(),
                this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        friendsPresenter = new FriendsPresenter();
        return friendsPresenter;
    }

    @Override
    protected void findViews() {
        mFriendsBackIb = findViewById(R.id.friends_back_ib);
        mSeekFriendsIb = findViewById(R.id.friends_seek_friends_ib);
    }

    @Override
    protected void setEventListeners() {
        mFriendsBackIb.setOnClickListener(this);
        mSeekFriendsIb.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back_ib:
                finish();
                break;
            case R.id.friends_seek_friends_ib:
                openActivity(PeopleActivity.class);
                break;
        }
    }

    @Override
    public void showNullFriends() {
        mFriendsNullIv.setVisibility(View.VISIBLE);
        mSeekFriendsIb.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFriends(List<GameRole> friends) {
        mFriendsNumberGtv.setText("当前好友数：" + friends.size());
    }
}
