package com.tssss.bysj.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tssss.bysj.R;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.mvp.view.LifeCircleMvpActivity;

import androidx.annotation.Nullable;


public abstract class BaseActivity extends LifeCircleMvpActivity implements
        android.view.View.OnClickListener {

    private ImageButton mLeftBtn, mRightBtn;
    private ImageView mCenterIv;

    private BaseMvpPresenter mPresenter;
    private BaseApplication mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        afterBindView();
    }

    /**
     * 统一处理 Activity 的 TopBar 点击事件
     * 如非隐藏 TopBar 的界面，子类重写 onClick 方法时必须调用 super.onClick() 方法
     */
    @Override
    public void onClick(android.view.View v) {
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
        }
    }

    /**
     * 通用 view 点击动画
     */
    protected void doViewClicked(View v) {
        Animation out = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        Animation in = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        v.clearAnimation();
        v.startAnimation(out);
        v.startAnimation(in);
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
            setTopBar();
        }
        getLayoutInflater().inflate(layoutResID, root, true);
        /*// 隐藏底部导航栏
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);*/
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
     * @param clazz clazz
     */
    protected void openActivityAndFinishSelf(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        this.finish();
    }

    /**
     * Initialize views of top bar.
     */
    private void setTopBar() {
        mLeftBtn = findViewById(R.id.top_bar_left);
        mCenterIv = findViewById(R.id.top_bar_center);
        mRightBtn = findViewById(R.id.top_bar_right);

        mLeftBtn.setOnClickListener(this);
        mCenterIv.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);

        mLeftBtn.setImageResource(getTopBarLeftViewStyle());
        mCenterIv.setImageResource(getTopBarCenterViewStyle());
        mRightBtn.setImageResource(getTopBarRightViewStyle());
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
}
