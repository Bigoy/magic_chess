package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.adapter.PeopleAdapter;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnPeopleListener;
import com.tssss.bysj.presenter.PeoplePresenter;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.util.ToastUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PeopleActivity extends BaseActivity implements OnPeopleListener {
    private ImageView mNullPeopleIv;
    private RecyclerView mPeopleRv;

    private PeoplePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.listPeople(this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new PeoplePresenter();
        return mPresenter;
    }

    @Override
    protected void findViews() {
        mNullPeopleIv = findViewById(R.id.people_null_iv);
        mPeopleRv = findViewById(R.id.people_rv);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_people;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.people_title;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onComplete(List<GameRole> allPlayers) {
        mNullPeopleIv.setVisibility(View.GONE);

        mPeopleRv.setVisibility(View.VISIBLE);
        mPeopleRv.setAdapter(new PeopleAdapter(this, allPlayers));
    }

    @Override
    public void onFailure() {
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
        mNullPeopleIv.setVisibility(View.VISIBLE);
    }
}
