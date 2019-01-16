package com.tssss.bysj.activity;

import android.widget.TextView;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;

@ContentView(R.layout.activity_chat)
public class ChatActivity extends BaseActivity {
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {

    }
}
