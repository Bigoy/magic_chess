package com.tssss.bysj.game.main;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.login.LoginActivity;

@ViewInject(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity{
    private ImageButton login_ib;

    @Override
    protected void findViews() {
        login_ib = findViewById(R.id.ac_main_login_ib);
    }

    @Override
    protected void setEventListeners() {
        login_ib.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected boolean requestFullScreen() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ac_main_login_ib:
                openActivity(LoginActivity.class);
                break;
        }
    }

}
