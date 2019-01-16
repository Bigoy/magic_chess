package com.tssss.bysj.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.game.GameSurfaceView;
import com.tssss.bysj.widget.GTextView;

@ContentView(R.layout.activity_game)
public class GameActivity extends BaseActivity implements View.OnTouchListener {

    private ImageView mGamePlayerSelfHeadIv, mGamePlayerArmyHeadIv,
            mGamePlayerSelfSexIv, mGamePlayerArmySexIv;
    private GTextView mGamePlayerArmyNameGtv,
            mGamePlayerSelfNameGtv;
    private ImageButton mSendMsgIb,
            mHelpIb, mAddFriendIb;
    private GameSurfaceView mGameView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
        /*mGamePlayerSelfHeadIv = findViewById(R.id.game_player_self_head_iv);
        mGamePlayerArmyNameGtv = findViewById(R.id.game_player_army_name_tv);
        mGamePlayerSelfNameGtv = findViewById(R.id.game_player_self_name_tv);
        mGamePlayerArmyHeadIv = findViewById(R.id.game_player_army_head_iv);
        mGamePlayerArmySexIv = findViewById(R.id.game_player_army_sex_iv);
        mSendMsgIb = findViewById(R.id.game_send_msg_ib);
        mHelpIb = findViewById(R.id.game_help_ib);
        mGamePlayerSelfSexIv = findViewById(R.id.game_player_self_sex_iv);
        mAddFriendIb = findViewById(R.id.game_add_friend_ib);
        mGameView = findViewById(R.id.game_gsv);*/
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setEventListeners() {
        mGameView.setOnTouchListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*switch (v.getId()) {
            case R.id.game_gsv:
                mGameView.doTouch(event);
                break;
        }*/
        return false;
    }

}
