package com.tssss.bysj.game.friend;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;

@ViewInject(layoutId = R.layout.activity_friend)
public class FriendsActivity extends BaseActivity{
    @Override
    protected void findViews() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void afterBindView() {

    }
    /*private ImageButton mFriendsBackIb, mSeekFriendsIb;
    private ImageView mFriendsNullIv;
    private GTextView mFriendsNumberGtv;

    private FriendsPresenter friendsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendsPresenter.loadFriends(
                UserManager.getUserManager().getUser().getUserId(),
                this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        friendsPresenter = new FriendsPresenter();
        return friendsPresenter;
    }

    @Override
    protected void findViews() {
        mFriendsBackIb = findViewById(R.id.friends_back_ib);
        mSeekFriendsIb = findViewById(R.id.friends_seek_friends_ib);
    }

    @Override
    protected void setEventListeners() {
        mFriendsBackIb.setOnClickListener(this);
        mSeekFriendsIb.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back_ib:
                finish();
                break;
            case R.id.friends_seek_friends_ib:
                openActivity(PeopleActivity.class);
                break;
        }
    }

    @Override
    public void showNullFriends() {
        mFriendsNullIv.setVisibility(View.VISIBLE);
        mSeekFriendsIb.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFriends(List<GameRole> friends) {
        mFriendsNumberGtv.setText("当前好友数：" + friends.size());
    }*/
}
