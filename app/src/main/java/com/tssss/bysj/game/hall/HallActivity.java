package com.tssss.bysj.game.hall;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.game.friend.FriendsActivity;
import com.tssss.bysj.game.news.NewsActivity;
import com.tssss.bysj.game.PeopleActivity;
import com.tssss.bysj.R;
import com.tssss.bysj.game.UserInfoActivity;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;

@ViewInject(layoutId = R.layout.activity_hall)
public class HallActivity extends BaseActivity {
    private ImageButton mLookFriendsIb, mMatchPlayerIb,
            mLookPeopleIb, mLookMyInfoIb, mNewsIb;

    @Override
    protected void findViews() {
        mLookFriendsIb = findViewById(R.id.hall_look_friends_ib);
        mMatchPlayerIb = findViewById(R.id.hall_match_player_ib);
        mLookPeopleIb = findViewById(R.id.hall_look_people_ib);
        mLookMyInfoIb = findViewById(R.id.hall_look_my_info_ib);
        mNewsIb = findViewById(R.id.hall_news_ib);
    }

    @Override
    protected void setEventListeners() {
        mLookFriendsIb.setOnClickListener(this);
        mMatchPlayerIb.setOnClickListener(this);
        mLookPeopleIb.setOnClickListener(this);
        mLookMyInfoIb.setOnClickListener(this);
        mNewsIb.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hall_news_ib:
                openActivity(NewsActivity.class);
                break;
            case R.id.hall_look_friends_ib:
                openActivity(FriendsActivity.class);
                break;
            case R.id.hall_match_player_ib:
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
