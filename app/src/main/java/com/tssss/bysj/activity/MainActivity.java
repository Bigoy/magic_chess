package com.tssss.bysj.activity;

import android.os.Bundle;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        welcome();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {
    }

    private void welcome() {
    }
}
