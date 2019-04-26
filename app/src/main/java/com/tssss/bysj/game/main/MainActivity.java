package com.tssss.bysj.game.main;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.login.LoginActivity;

@ViewInject(layoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity {
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
                /*if (AppDataCache.readAccountState().equals(Constant.ACCOUNT_STATE_LOGIN)) {
//                    openActivity(HallActivity.class);
                    // 本地已经登录，那么登录到极光Message
                    String cacheId = UserDataCache.readAccount(Constant.ACCOUNT_ID);
                    String cachePsd = UserDataCache.readAccount(Constant.ACCOUNT_PASSWORD);
                    if (!StringUtil.isBlank(cacheId) && !StringUtil.isBlank(cachePsd)) {
                        JMessageClient.login(cacheId, cachePsd, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i == 0) {
                                    Logger.log("JIM 登录成功" + s);


                                } else {
                                    openActivity(LoginActivity.class);
                                    AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGOUT);
                                    Log.w("MainActivity", "JMessage login failed >>> " + s);
                                }
                            }
                        });
                    } else {
                        openActivity(LoginActivity.class);
                        AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGOUT);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }
                break;*/
        }
    }

}
