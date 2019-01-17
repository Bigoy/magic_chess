package com.tssss.bysj.activity;

import android.os.Bundle;
import android.util.Log;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        welcome();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    private void welcome() {
        Log.wtf(getClass().getSimpleName(), "run MainActivity");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    openActivity(StartActivity.class);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
