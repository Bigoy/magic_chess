package com.tssss.bysj.game.rank;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.friend.AddFriendCallBackHandler;
import com.tssss.bysj.game.role.UserInfoActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.jmessage.JMessageManager;
import com.tssss.bysj.util.SystemUtil;
import com.tssss.bysj.util.ToastUtil;

@ViewInject(layoutId = R.layout.activity_rank)
public class RankActivity extends BaseActivity implements IRankContract.IView, RankAdapter.RankViewHolder.OnRankItemClickListener {
    private GTextView updateTime;
    private RecyclerView rankRv;

    private RankPresenter rankPresenter;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder friendBuilder;


    @Override
    protected void findViews() {
        updateTime = findViewById(R.id.rank_update_time);
        rankRv = findViewById(R.id.rank_rv);
    }

    @Override
    protected void setEventListeners() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void afterBindView() {
        builder = new AlertDialog.Builder(this)
                .subDesc("加载中...")
                .operationType(AlertDialog.OPERATION_TYPE_SIMPLE);
        builder.display();
        rankPresenter = new RankPresenter(this, this);
        rankPresenter.loadRankData();
        rankPresenter.setOnRankItemClickListener(this);
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.rank_title;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showRank(RankAdapter rankAdapter) {
        if (null != rankAdapter) {
            builder.dismiss();
            updateTime.setText("最近更新时间：" + SystemUtil.getCurrentTime());
            rankRv.setLayoutManager(new LinearLayoutManager(this));
            rankRv.setAdapter(rankAdapter);
        }
    }

    @Override
    public void loadError() {
        builder.dismiss();
        ToastUtil.showToast(this, "加载错误", ToastUtil.TOAST_ERROR);
        finish();
    }


    @Override
    public void onAddFriend(String accountID) {
        JMessageManager.addFriend(accountID, new AddFriendCallBackHandler(RankActivity.this));

    }

    @Override
    public void onLookInfo(String accountID) {
        Intent infoIntent = new Intent(RankActivity.this, UserInfoActivity.class);
        infoIntent.putExtra(Constant.ACCOUNT_ID, accountID);
        startActivity(infoIntent);

    }
}
