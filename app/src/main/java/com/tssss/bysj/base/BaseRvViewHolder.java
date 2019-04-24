package com.tssss.bysj.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tssss.bysj.componet.GTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRvViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseRvViewHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    private void init() {
        findViews();
        instantiateObject();
    }

    protected abstract void instantiateObject();

    public abstract void fillData(T data);

    protected abstract void findViews();

    protected View findViewById(int id) {
        View v = itemView.findViewById(id);
        return v;
    }

    protected GTextView findGTextView(int id) {
        return (GTextView) findViewById(id);
    }

    protected TextView findTextView(int id) {
        return (TextView) findViewById(id);
    }

    protected ImageView findImageView(int id) {
        return (ImageView) findViewById(id);
    }

    protected RecyclerView findRecyclerView(int id) {
        return (RecyclerView) findViewById(id);

    }

    protected Context getContent() {
        return itemView.getContext();

    }
}
