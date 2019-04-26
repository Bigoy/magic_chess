package com.tssss.bysj.game.core.view;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;

@ViewInject(layoutId = R.layout.activity_game)
public class GameActivity extends BaseActivity implements View.OnTouchListener {

    private GameSurfaceView mGameView;


    @Override
    protected void findViews() {
        mGameView = findViewById(R.id.game_gsv);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.game_title;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGameView.doTouch(event);
        return false;
    }
}
