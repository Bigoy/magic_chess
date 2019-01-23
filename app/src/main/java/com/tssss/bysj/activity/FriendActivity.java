package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.adapter.FriendAdapter;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnFriendsListener;
import com.tssss.bysj.presenter.FriendsPresenter;
import com.tssss.bysj.user.UserManager;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.util.ToastUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class FriendActivity extends BaseActivity implements OnFriendsListener {
    private ImageView mNullFriendIv;
    private RecyclerView mFriendsRv;

    private FriendsPresenter mPresent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresent.loadFriends(
                UserManager.getUserManager().getUser(),
                this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresent = new FriendsPresenter();
        return mPresent;
    }

    @Override
    protected void findViews() {
        mNullFriendIv = findViewById(R.id.friends_null_iv);
        mFriendsRv = findViewById(R.id.friends_rv);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.friends_title;
    }

    @Override
    public void onNullFriends() {
        mNullFriendIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onComplete(List<GameRole> friends) {
        mFriendsRv.setAdapter(new FriendAdapter(this, friends));
    }

    @Override
    public void onFailure() {
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
    }
}
