package com.tssss.bysj.game.rank;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import com.tssss.bysj.util.StringUtil;

public class RankSortedListCallBack extends SortedListAdapterCallback<Rank> {

    public RankSortedListCallBack(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(Rank o1, Rank o2) {
        return o1.getRole().getScore() - o2.getRole().getScore();
    }

    @Override
    public boolean areContentsTheSame(Rank oldItem, Rank newItem) {
        return isSame(oldItem, newItem);
    }

    @Override
    public boolean areItemsTheSame(Rank item1, Rank item2) {
        return isSame(item1, item2);
    }

    private boolean isSame(Rank rank01, Rank rank02) {
        String role01AccountID = rank01.getRole().getUser().getUserId();
        String role02AccountID = rank02.getRole().getUser().getUserId();

        if (!StringUtil.isBlank(role01AccountID) && !StringUtil.isBlank(role02AccountID)) {
            return role01AccountID.equals(role02AccountID);
        }

        return false;
    }
}
