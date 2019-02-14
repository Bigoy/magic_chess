package com.tssss.bysj.login;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.login.presenter.LoginPresenter;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GTextView;

public class LoginActivity extends BaseActivity implements ILoginActivityContract.IView {
    private EditText mAccountEt, mPasswordEt;
    private ImageButton mLoginIb;
    private GTextView mLoggingGtv,
            mAccountGtv,
            mPasswordGtv,
            mAccountErrorGtv,
            mPasswordErrorGtv,
            mLoginGtv,
            mReconnectGtv;

    private User loginUser;
    private ILoginActivityContract.IPresenter mPresenter;
    private int loginCount;

    @Override
    protected void onStart() {
        super.onStart();

        loginUser = new User();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearInputText();
        loginCount = 0;
    }

    @Override
    protected void findViews() {
        mAccountEt = findViewById(R.id.login_account_et);
        mPasswordEt = findViewById(R.id.login_key_et);
        mLoginIb = findViewById(R.id.login_ib);
        mLoggingGtv = findViewById(R.id.login_logging_gtv);
        mAccountGtv = findViewById(R.id.login_account_gtv);
        mPasswordGtv = findViewById(R.id.login_password_gtv);
        mAccountErrorGtv = findViewById(R.id.login_account_error_gtv);
        mPasswordErrorGtv = findViewById(R.id.login_password_error_gtv);
        mLoginGtv = findViewById(R.id.login_gtv);
        mReconnectGtv = findViewById(R.id.login_reconnect_gtv);
    }

    @Override
    protected void setEventListeners() {
        mLoginIb.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void afterBindView() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.login_title;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.login_ib:
                login();
                break;
        }
    }

    /**
     * Login
     */
    private void login() {
        try {
            loginUser.setUserId(Long.parseLong(mAccountEt.getText().toString()));

        } catch (NumberFormatException e) {
            loginUser.setUserId(-1L);
        }

        loginUser.setUserPassword(mPasswordEt.getText().toString());
        mPresenter.identifyAccount(loginUser);
    }

    private void clearInputText() {
        mAccountEt.setText("");
        mPasswordEt.setText("");
    }

    private void switchView(View gone, View show) {
        Animation out = AnimationUtils.loadAnimation(this, R.anim.alpha_slide_out);
        Animation in = AnimationUtils.loadAnimation(this, R.anim.alpha_slide_in);

        gone.startAnimation(out);
        gone.setVisibility(View.GONE);

        show.setVisibility(View.VISIBLE);
        show.startAnimation(in);
    }

    @Override
    public void onLoginSuccess() {
        loginCount = 0;
//        openActivity(HallActivity.class);
    }

    @Override
    public void onLoginError() {
        loginCount++;

        if (loginCount == 6) {

            if (mReconnectGtv.getVisibility() == View.VISIBLE) {

                showView(mLoginGtv);
                switchView(mReconnectGtv, mLoginIb);
                unlockViews();
                ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
                loginCount = 0;
            }

        }

        if (loginCount >= 1 && loginCount < 6)
            reconnect();
    }

    @Override
    public void onUserNotExit() {
//        remindUserRegister();
    }

    @Override
    public void onUserPasswordError() {
        mPasswordEt.setText("");
        switchView(mLoggingGtv, mLoginIb);
        unlockViews();
    }

    @Override
    public void onInvalidPhoneNumber() {
        mAccountEt.setText("");

        if (mAccountErrorGtv.getVisibility() == View.VISIBLE)
            switchView(mAccountErrorGtv, mAccountErrorGtv);
        else
            switchView(mAccountGtv, mAccountErrorGtv);
    }

    @Override
    public void onInvalidPassword() {
        mPasswordEt.setText("");

        if (mPasswordErrorGtv.getVisibility() == View.VISIBLE)
            switchView(mPasswordErrorGtv, mPasswordErrorGtv);
        else
            switchView(mPasswordGtv, mPasswordErrorGtv);
    }

    @Override
    public void onValidPhoneNumber() {
        if (mAccountGtv.getVisibility() != View.VISIBLE)
            switchView(mAccountErrorGtv, mAccountGtv);
    }

    @Override
    public void onValidPassword() {
        if (mPasswordGtv.getVisibility() != View.VISIBLE)
            switchView(mPasswordErrorGtv, mPasswordGtv);
    }

    @Override
    public void onValidAccount() {
        if (loginCount == 0) {
            hideView(mLoginGtv);
            switchView(mLoginIb, mLoggingGtv);
            lockViews();

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    mPresenter.login();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void hideView(View hideView) {
        hideView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_out));
        hideView.setVisibility(View.GONE);
    }

    private void showView(View showView) {
        showView.setVisibility(View.VISIBLE);
        showView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in));
    }

    private void lockViews() {
        mAccountEt.setEnabled(false);
        mPasswordEt.setEnabled(false);
        mLoginIb.setEnabled(false);
    }

    private void unlockViews() {
        mAccountEt.setEnabled(true);
        mPasswordEt.setEnabled(true);
        mLoginIb.setEnabled(true);
    }

    private void reconnect() {
        if (mReconnectGtv.getVisibility() == View.VISIBLE)
            switchView(mReconnectGtv, mReconnectGtv);

        if (mReconnectGtv.getVisibility() == View.GONE)
            switchView(mLoggingGtv, mReconnectGtv);

        mPresenter.login();
    }

    /*
      Remind user to register game account
     */
//    private void remindUserRegister() {
//        final GDialog gDialog = new GDialog(this, getString(R.string.register_dialog_title),
//                getString(R.string.register_dialog_content));
//        gDialog.setOnGDialogListener(new OnGDialogListener() {
//            @Override
//            public void onPassive() {
//                gDialog.dismiss();
//                finish();
//            }
//
//            @Override
//            public void onPositive() {
////                openActivity(RegisterActivity.class);
//            }
//        });
//        gDialog.show();
//    }

}
