package com.tssss.bysj.game.role.info.fragment.battle_record;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.role.info.UserInfoActivity;
import com.tssss.bysj.other.Constant;

import java.util.List;

@ViewInject(layoutId = R.layout.fragment_battle_record)
public class BattleRecordFragment extends BaseFragment implements IBattleRecordContract.IView {
    private RecyclerView battleRecordRv;
    private GTextView loadingTv;
    private LinearLayout container;
    private BattleRecordPresenter battleRecordPresenter;
    private Handler handler;

    public BattleRecordFragment() {
        this.handler = new Handler();
        battleRecordPresenter = new BattleRecordPresenter(this);
    }

    @Override
    protected void findViews() {
        battleRecordRv = (RecyclerView) findViewById(R.id.fragment_battle_record_rv);
        loadingTv = (GTextView) findViewById(R.id.fragment_battle_record_loading);
        container = (LinearLayout) findViewById(R.id.fragment_battle_record_rv_container);
    }

    @Override
    protected void afterBindView() {
        UserInfoActivity activity = (UserInfoActivity) getActivity();
        battleRecordPresenter.loadBattleRecord(activity.getUserAccount());
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    public void showBattleRecord(List<BattleRecord> battleRecordList) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (null == battleRecordList || battleRecordList.size() == 0) {
                    loadingTv.setText("没有查询到对战记录 ^0^");
                } else {
                    loadingTv.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                    battleRecordRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    battleRecordRv.setAdapter(new BattleRecordAdapter(getContext(), battleRecordList,
                            new BattleRecordAdapter.BattleRecordViewHolder.OnBattleRecordItemClickListener() {
                                @Override
                                public void onBattleRecordItemClick(View v, int position) {
                                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                                    intent.putExtra(Constant.ACCOUNT_ID, battleRecordList.get(position).getAccountID());
                                    startActivity(intent);
                                }
                            }));
                }
            }
        });
    }
}
