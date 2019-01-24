package com.tssss.bysj.activity;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.game.GameSurfaceView;

public class GameActivity extends BaseActivity implements View.OnTouchListener {
    private GameSurfaceView mGameSurfaceView;


    @Override
    protected void findViews() {
        mGameSurfaceView = findViewById(R.id.game_gsv);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGameSurfaceView.doTouch(event);
        return false;
    }
}
