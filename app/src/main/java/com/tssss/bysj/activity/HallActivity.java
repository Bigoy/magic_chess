package com.tssss.bysj.activity;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.interfaces.OnGDialogListener;
import com.tssss.bysj.widget.GDialog;

public class HallActivity extends BaseActivity {
    private ImageButton mNewsIb,
            mLookFriendsIb,
            mMatchPlayerIb,
            mOtherPeopleIb,
            mAboutMeIb;

    @Override
    protected void findViews() {
        mNewsIb = findViewById(R.id.hall_news_ib);
        mLookFriendsIb = findViewById(R.id.hall_look_friends_ib);
        mMatchPlayerIb = findViewById(R.id.hall_match_player_ib);
        mOtherPeopleIb = findViewById(R.id.hall_look_people_ib);
        mAboutMeIb = findViewById(R.id.hall_look_my_info_ib);
    }

    @Override
    protected void setEventListeners() {
        mNewsIb.setOnClickListener(this);
        mLookFriendsIb.setOnClickListener(this);
        mMatchPlayerIb.setOnClickListener(this);
        mOtherPeopleIb.setOnClickListener(this);
        mAboutMeIb.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hall;
    }

    @Override
    protected int getLeftBtnStyle() {
        return R.drawable.ic_btn_quit;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.hall_title;
    }

    /**
     * Exit game.
     */
    @Override
    protected void clickLeft() {
        final GDialog dialog = new GDialog(
                this,
                getString(R.string.dialog_title),
                getString(R.string.exit_app));
        dialog.setOnGDialogListener(new OnGDialogListener() {
            @Override
            public void onPassive() {
                dialog.dismiss();
            }

            @Override
            public void onPositive() {
                getMyApplication().killAppProgress();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.hall_news_ib:
                openActivity(NewsActivity.class);
                break;
            case R.id.hall_look_friends_ib:
                openActivity(FriendActivity.class);
                break;
            case R.id.hall_match_player_ib:
                openActivity(MatchActivity.class);
                break;
            case R.id.hall_look_people_ib:
                openActivity(PeopleActivity.class);
                break;
            case R.id.hall_look_my_info_ib:
                openActivity(MeActivity.class);
                break;
        }
    }
}
