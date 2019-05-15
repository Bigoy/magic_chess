package com.tssss.bysj.game.splash;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.application.MCApplication;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.main.MainActivity;
import com.tssss.bysj.game.splash.presenter.SplashAdPresenter;
import com.tssss.bysj.util.SystemUtil;

@ViewInject(layoutId = R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements ISplashContract.IView {
    private SplashAdPresenter mAdPresenter;

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected void afterBindView() {
        if (!SystemUtil.checkNet()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .desc("网络状态")
                    .subDesc("抱歉，必须连接到互联网才可以体验游戏")
                    .operationType(AlertDialog.OPERATION_TYPE_NORMAL)
                    .okDesc("去连接网络")
                    .noDesc("退出游戏")
                    .operationListener(new AlertDialog.OnDialogOperationListener() {
                        @Override
                        public void ok() {
                            Intent netIntent = new Intent();
                            netIntent.setAction(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                            startActivity(netIntent);
                        }

                        @Override
                        public void no() {
                            MCApplication.exitApp();
                        }
                    });
            builder.display();
        } else {
            initAdPresenter();
        }
    }

    private void initAdPresenter() {
        mAdPresenter = new SplashAdPresenter(this);
        mAdPresenter.loadAd();
    }

    @Override
    protected boolean requestFullScreen() {
        return true;
    }

    @Override
    public void showAd() {
        Log.i(getClass().getSimpleName(), "暂无广告提供方");
    }

    @Override
    public void skipAd() {
        if (SystemUtil.checkNet()) {
            openActivityAndFinishSelf(MainActivity.class);
        }
    }
}
