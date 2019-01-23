package com.tssss.bysj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tssss.bysj.R;
import com.tssss.bysj.game.people.PeopleHolder;
import com.tssss.bysj.user.role.GameRole;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PeopleAdapter extends RecyclerView.Adapter {
    private List<GameRole> mPeople;
    private Context mContext;


    public PeopleAdapter(Context context, List<GameRole> people) {
        mContext = context;
        mPeople = people;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeopleHolder(LayoutInflater.from(mContext).inflate(R.layout.item_simple_player_info,
                parent));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PeopleHolder)
            ((PeopleHolder) holder).setViews(mPeople.get(position));
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }
}
