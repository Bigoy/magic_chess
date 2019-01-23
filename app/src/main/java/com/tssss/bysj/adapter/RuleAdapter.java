package com.tssss.bysj.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class RuleAdapter extends PagerAdapter {
    private List<ImageView> mRules;


    public RuleAdapter(List<ImageView> rules) {
        this.mRules = rules;
    }

    @Override
    public int getCount() {
        return mRules.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = mRules.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mRules.get(position));
    }
}
