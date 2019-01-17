package com.tssss.bysj.contract;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tssss.bysj.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View,
        android.view.View.OnClickListener {

    private ImageButton mLeftBtn, mRightBtn;
    private ImageView mCenterIv;

    private PresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        findViews();

        // attach mPresenter
        if (attachPresenter() != null) {
            mPresenter = attachPresenter();
            mPresenter.attachView(this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setEventListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * Subclass selects specific Presenter (extends PresenterImp)
     *
     * @return mPresenter
     */
    protected PresenterImp attachPresenter() {
        return null;
    }

    /**
     * Lock back key
     */
    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(android.view.View v) {
        if (v.getId() == R.id.top_bar_left) {
            finish();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout root = new LinearLayout(this);
        root.setBackgroundResource(getBackgroundResId());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(root);

        if (!isFullScreen()) {
            getLayoutInflater().inflate(getTopBarId(), root, true);
            setTopBar();
        }

        getLayoutInflater().inflate(layoutResID, root, true);
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
     * Set src of the left ImageButton
     */
    protected int getLeftBtnStyle() {
        return R.drawable.ic_btn_back;
    }

    /**
     * Set src of the center ImageView (title of current activity)
     */
    protected int getCenterIvStyle() {
        return 0;
    }

    /**
     * Set src of the right ImageButton
     */
    protected int getRightIbStyle() {
        return 0;
    }

    /**
     * Set activity to full screen
     */
    protected boolean isFullScreen() {
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

    /**
     * Set layout id
     */
    protected abstract int getLayoutId();

    /**
     * Open activity without data
     */
    protected void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * Initialize views of top bar
     */
    private void setTopBar() {
        mLeftBtn = findViewById(R.id.top_bar_left);
        mCenterIv = findViewById(R.id.top_bar_center);
        mRightBtn = findViewById(R.id.top_bar_right);

        mLeftBtn.setOnClickListener(this);
        mCenterIv.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);

        mLeftBtn.setImageResource(getLeftBtnStyle());
        mCenterIv.setImageResource(getCenterIvStyle());
        mRightBtn.setImageResource(getRightIbStyle());
    }

}
