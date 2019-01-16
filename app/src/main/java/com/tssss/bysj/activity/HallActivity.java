package com.tssss.bysj.activity;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnGDialogListener;
import com.tssss.bysj.widget.GDialog;

@ContentView(R.layout.activity_hall)
public class HallActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mLookFriendsIb, mMatchPlayerIb,
            mLookPeopleIb, mLookMyInfoIb, mNewsIb, mExitIb;

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
        mLookFriendsIb = findViewById(R.id.hall_look_friends_ib);
        mMatchPlayerIb = findViewById(R.id.hall_match_player_ib);
        mLookPeopleIb = findViewById(R.id.hall_look_people_ib);
        mLookMyInfoIb = findViewById(R.id.hall_look_my_info_ib);
        mNewsIb = findViewById(R.id.hall_news_ib);
        mExitIb = findViewById(R.id.hall_exit_ib);
    }

    @Override
    protected void setEventListeners() {
        mLookFriendsIb.setOnClickListener(this);
        mMatchPlayerIb.setOnClickListener(this);
        mLookPeopleIb.setOnClickListener(this);
        mLookMyInfoIb.setOnClickListener(this);
        mNewsIb.setOnClickListener(this);
        mExitIb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hall_exit_ib:
                final GDialog gDialog = new GDialog(this, "退出提示", "关闭游戏吗？");
                gDialog.setOnGDialogListener(new OnGDialogListener() {
                    @Override
                    public void onPassive() {
                        gDialog.dismiss();
                    }

                    @Override
                    public void onPositive() {
//                        closeApp();
                    }
                });
                gDialog.show();
                break;
            case R.id.hall_news_ib:
                openActivity(NewsActivity.class);
                break;
            case R.id.hall_look_friends_ib:
                openActivity(FriendsActivity.class);
                break;
            case R.id.hall_match_player_ib:
                openActivity(MatchActivity.class);
                break;
            case R.id.hall_look_people_ib:
                openActivity(PeopleActivity.class);
                break;
            case R.id.hall_look_my_info_ib:
                openActivity(UserInfoActivity.class);
                break;
        }
    }

}
