package com.tssss.bysj.game.role.fragment;


import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.util.ToastUtil;

@ViewInject(layoutId = R.layout.fragment_news)
public class NewsFragment extends BaseFragment {
    private GTextView loading;
    private int clickCount;

    @Override
    protected void findViews() {
        loading = (GTextView) findViewById(R.id.fragment_news_loading);
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected void setEventListeners() {
        loading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        egg();
    }

    /**
     * 彩蛋功能
     */
    private void egg() {
        clickCount++;
        if (clickCount == 5) {
            ToastUtil.showToast(getContext(), "屏幕戳坏了", ToastUtil.TOAST_DEFAULT);
            clickCount = 0;
        }
    }

}
