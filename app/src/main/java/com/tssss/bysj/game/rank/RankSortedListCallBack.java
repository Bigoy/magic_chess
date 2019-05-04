package com.tssss.bysj.game.rank;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;

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
        return oldItem.getRole().getUser().getUserId().equals(newItem.getRole().getUser().getUserId());
    }

    @Override
    public boolean areItemsTheSame(Rank item1, Rank item2) {
        return item1.getRole().getUser().getUserId().equals(item2.getRole().getUser().getUserId());
    }
}
