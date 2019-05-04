package com.tssss.bysj.game.rank;

import android.annotation.SuppressLint;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.util.SystemUtil;
import com.tssss.bysj.util.ToastUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.activity_rank)
public class RankActivity extends BaseActivity implements IRankContract.IView {
    private GTextView updateTime;
    private RecyclerView rankRv;

    private RankPresenter rankPresenter;
    private AlertDialog.Builder builder;


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
}
