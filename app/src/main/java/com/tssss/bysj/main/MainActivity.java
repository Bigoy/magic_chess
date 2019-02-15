package com.tssss.bysj.main;

import android.os.Bundle;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@ViewInject(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainContract.IView {
    private MainPresenter mPresenter = new MainPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.initFragment();
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected void afterBindView() {
    }

    private void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected boolean isFullScreenActivity() {
        return true;
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    @Override
    public void hideFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().hide(fragment).commit();
    }

    @Override
    public void addFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    @Override
    public void replaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }

    @Override
    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment);
    }
}
