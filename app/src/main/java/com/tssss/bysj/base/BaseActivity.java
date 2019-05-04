package com.tssss.bysj.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tssss.bysj.R;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.game.im.JMessageManager;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.mvp.view.LifeCircleMvpActivity;
import com.tssss.bysj.util.AnimationUtil;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseActivity extends LifeCircleMvpActivity implements
        android.view.View.OnClickListener {

    private ImageButton mLeftBtn, mRightBtn;
    private ImageView mCenterIv;

    private BaseMvpPresenter mPresenter;
    private BaseApplication mApplication;

    private Handler handler;
    private BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        ViewInject viewInject = this.getClass().getAnnotation(ViewInject.class);
        if (viewInject != null) {
            int layoutId = viewInject.layoutId();
            if (layoutId > 0) {
                setContentView(layoutId);
                findViews();
                setEventListeners();
            } else {
                throw new RuntimeException("layoutId < 0");
            }
        } else {
            throw new RuntimeException("no annotation");
        }

        hideStatusBarAndNavigationBar();
        afterBindView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler();
    }

    /**
     * 统一处理 Activity 的 TopBar 点击事件
     * <p>
     * 如非全屏界面，子类重写 onClick 方法时必须调用 super.onClick() 方法
     */
    @Override
    public void onClick(android.view.View v) {
        if (v instanceof ImageButton) {
            AnimationUtil.flipView(this, v);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 300);
        topBarClick(v);
    }

    protected void topBarClick(View v) {
        switch (v.getId()) {
            case R.id.top_bar_left:
                clickTopBarLeft();
                break;
            case R.id.top_bar_center:
                clickTopBarCenter();
                break;
            case R.id.top_bar_right:
                clickTopBarRight();
                break;
            default:
        }

    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout root = new LinearLayout(this);
        root.setBackgroundResource(getBackgroundResId());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(lp);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(root);
        if (!requestFullScreen()) {
            getLayoutInflater().inflate(getTopBarId(), root, true);
            initTopBar();
        }
        getLayoutInflater().inflate(layoutResID, root, true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideStatusBarAndNavigationBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 隐藏状态栏和导航栏，实现全屏
     */
    protected void hideStatusBarAndNavigationBar() {
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    /**
     * Set background
     *
     * @return resId
     */
    protected int getBackgroundResId() {
        return R.drawable.bg_common;
    }

    /**
     * Set top bar
     */
    protected int getTopBarId() {
        return R.layout.top_bar;
    }

    /**
     * 设置 TopBar 左边的 View 显示样式 一般为返回按钮
     */
    protected int getTopBarLeftViewStyle() {
        return R.drawable.ic_btn_back;
    }

    /**
     * 设置 TopBar 中间的 View 显示样式
     */
    protected int getTopBarCenterViewStyle() {
        return 0;
    }

    /**
     * 设置 TopBar 右边的 View 显示样式
     */
    protected int getTopBarRightViewStyle() {
        return 0;
    }

    /**
     * Set activity to full screen
     */
    protected boolean requestFullScreen() {
        return false;
    }

    /**
     * FindViewById()
     */
    protected abstract void findViews();

    /**
     * SetXXListener
     */
    protected abstract void setEventListeners();

    protected abstract void afterBindView();

    /**
     * Open activity without data
     */
    protected void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 打开一个 activity 并且摧毁自身
     *
     * @param clazz clazz
     */
    protected void openActivityAndFinishSelf(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        this.finish();
    }

    protected void openActivityDelay(Class clazz, long ms) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity(clazz);
            }
        }, ms);
    }

    /**
     * Initialize views of top bar.
     */
    private void initTopBar() {
        if (!requestFullScreen()) {
            setTopBarLeft();
            setTopBarCenter();
            setTopBarRight();
        }
    }

    private void setTopBarLeft() {
        mLeftBtn = findViewById(R.id.top_bar_left);
        if (getTopBarLeftViewStyle() == 0) {
            mLeftBtn.setVisibility(View.INVISIBLE);
        } else {
            mLeftBtn.setImageResource(getTopBarLeftViewStyle());
            mLeftBtn.setOnClickListener(this);
        }
    }

    private void setTopBarCenter() {
        mCenterIv = findViewById(R.id.top_bar_center);
        if (getTopBarCenterViewStyle() == 0) {
            mCenterIv.setVisibility(View.INVISIBLE);
        } else {
            mCenterIv.setImageResource(getTopBarCenterViewStyle());
            mCenterIv.setOnClickListener(this);
        }
    }

    private void setTopBarRight() {
        mRightBtn = findViewById(R.id.top_bar_right);
        if (getTopBarRightViewStyle() == 0) {
            mRightBtn.setVisibility(View.INVISIBLE);
        } else {
            mRightBtn.setImageResource(getTopBarRightViewStyle());
            mRightBtn.setOnClickListener(this);
        }
    }

    /**
     * Handle event generated by the user clicking the left
     * view of top bar, the default is back.
     */
    protected void clickTopBarLeft() {
        this.finish();
    }

    /**
     * Handle event generated by the user clicking the center
     * view of top bar.
     */
    protected void clickTopBarCenter() {
    }

    /**
     * Handle event generated by the user clicking the right
     * view of top bar.
     */
    protected void clickTopBarRight() {
    }

    /**
     * Get context of current activity.
     */
    protected Context getContext() {
        return getApplicationContext();
    }

    protected void replaceFragment(int containerID, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out)
                .replace(containerID, fragment)
                .commit();
    }

    protected void addFragment(int containerID, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(containerID, fragment);
    }

    protected void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment);
    }

    protected void backLauncher() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    @Override
    public void onBackPressed() {

    }
}
