package com.tssss.bysj.game.rank;

import android.content.Context;
import android.os.Handler;

import androidx.recyclerview.widget.SortedList;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.jmessage.IGetAllGameRoleCallBack;
import com.tssss.bysj.other.jmessage.JMessageHelper;

import java.util.List;

public class RankPresenter extends BaseMvpPresenter<IRankContract.IView> implements IRankContract.IPresenter {
    private Handler handler;
    private RankSortedListCallBack rankSortedListCallBack;
    private RankAdapter rankAdapter;
    private RankAdapter.RankViewHolder.OnRankItemClickListener onRankItemClickListener;


    public RankPresenter(Context context, IRankContract.IView view) {
        super(view);
        handler = new Handler();
        rankAdapter = new RankAdapter(context, null);
        rankSortedListCallBack = new RankSortedListCallBack(rankAdapter);
    }

    public void setOnRankItemClickListener(RankAdapter.RankViewHolder.OnRankItemClickListener onRankItemClickListener) {
        this.onRankItemClickListener = onRankItemClickListener;

    }

    @Override
    public void loadRankData() {
        SortedList<Rank> rankSortedList = new SortedList<>(Rank.class, rankSortedListCallBack);
        JMessageHelper.listAllGameRoles(new IGetAllGameRoleCallBack() {
            @Override
            public void onSuccess(List<GameRole> gameRoleList) {
                int gameRoleListSize = gameRoleList.size();
                if (gameRoleListSize > 0) {
                    for (int i = 0; i < gameRoleListSize; i++) {
                        GameRole role = gameRoleList.get(i);
                        Rank rank = new Rank();
                        rank.setRole(role);
                        rank.setRankNum(i);
                        rankSortedList.add(rank);
                    }
                    rankAdapter.setRankSortedList(rankSortedList);
                    handler.post(() -> getView().showRank(rankAdapter));

                } else {
                    handler.post(() -> getView().loadError());
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.post(() -> getView().loadError());
            }
        });
    }

    @Override
    protected IRankContract.IView getEmptyView() {
        return IRankContract.emptyView;
    }
}
