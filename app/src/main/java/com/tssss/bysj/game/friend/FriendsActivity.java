package com.tssss.bysj.game.friend;

import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

@ViewInject(layoutId = R.layout.activity_friend)
public class FriendsActivity extends BaseActivity implements OnMenuItemClickListener {
    private GTextView all;
    private GTextView group;

    private int friendsCount;
    private Menu menu;

    @Override
    protected void findViews() {
        all = findViewById(R.id.friends_category_all_tv);
        group = findViewById(R.id.friends_category_group_tv);
    }

    @Override
    protected void setEventListeners() {
        all.setOnClickListener(this);
        group.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.friends_title;
    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    protected void clickTopBarRight() {
        super.clickTopBarRight();
        List<String> items = new ArrayList<>();
        items.add("添加好友");
        items.add("好友统计");
        menu = new Menu.Builder(this, this)
                .items(items)
                .build();
        menu.display();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.friends_category_all_tv:
                initCategoryTabState();
                startColorAnimation(all);
                break;
            case R.id.friends_category_group_tv:
                initCategoryTabState();
                startColorAnimation(group);
                break;
            default:
        }
    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                menu.dismiss();
                AlertDialog dialog = new AlertDialog(this);
                dialog.desc("好友统计");
                dialog.subDesc("你总共有好友：" + friendsCount);
                dialog.noDesc("知道了");
                dialog.operationType(AlertDialog.OPERATION_TYPE_NO);
                dialog.operationListener(new AlertDialog.OnDialogOperationListener() {
                    @Override
                    public void ok() {

                    }

                    @Override
                    public void no() {
                    }
                });
                dialog.display();
                break;
            default:
        }
    }

    private void startColorAnimation(GTextView view) {
        initCategoryTabState();
        AnimationUtil.startBackgroundColorAnimator(view);
    }

    private void initCategoryTabState() {
        all.setTextColor(0xFF7E561B);
        group.setTextColor(0xFF7E561B);
        all.setBackgroundColor(0x00ffffff);
        group.setBackgroundColor(0x00ffffff);
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
