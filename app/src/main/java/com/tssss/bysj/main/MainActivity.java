package com.tssss.bysj.main;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.login.register.RegisterActivity;
import com.tssss.bysj.login.login.LoginActivity;

@ViewInject(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity{
    private ImageButton login_ib;
    private ImageButton register_ib;

    @Override
    protected void findViews() {
        login_ib = findViewById(R.id.ac_main_login_ib);
        register_ib = findViewById(R.id.ac_main_register_ib);
    }

    @Override
    protected void setEventListeners() {
        login_ib.setOnClickListener(this);
        register_ib.setOnClickListener(this);
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
            case R.id.ac_main_register_ib:
                openActivity(RegisterActivity.class);
                break;
        }
    }

}
