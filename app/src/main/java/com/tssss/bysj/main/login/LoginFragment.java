package com.tssss.bysj.main.login;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.R2;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.main.login.presenter.LoginPresenter;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GTextView;

import butterknife.BindView;

@ViewInject(layoutId = R.layout.fragment_login)

public class LoginFragment extends BaseFragment implements ILoginFmContract.IView {
    @BindView(R2.id.login_account_et)
    protected EditText mAccountEt;
    @BindView(R2.id.login_key_et)
    protected EditText mPasswordEt;
    @BindView(R2.id.login_ib)
    protected ImageButton mLoginIb;
    @BindView(R2.id.login_logging_gtv)
    protected GTextView mLoggingGtv;
    @BindView(R2.id.login_account_gtv)
    protected GTextView mAccountGtv;
    @BindView(R2.id.login_password_gtv)
    protected GTextView mPasswordGtv;
    @BindView(R2.id.login_account_error_gtv)
    protected GTextView mAccountErrorGtv;
    @BindView(R2.id.login_password_error_gtv)
    protected GTextView mPasswordErrorGtv;
    @BindView(R2.id.login_gtv)
    protected GTextView mLoginGtv;
    @BindView(R2.id.login_reconnect_gtv)
    protected GTextView mReconnectGtv;

    private User loginUser;
    private ILoginFmContract.IPresenter mPresenter;
    private int loginCount;

    @Override
    protected void afterBindView() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected void setEventListeners() {
        mLoginIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
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
                ToastUtil.showToast(getActivity(), getString(R.string.error), ToastUtil.TOAST_ERROR);
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

    private void login() {
        loginUser = new User();
        try {
            loginUser.setUserId(Long.parseLong(mAccountEt.getText().toString()));

        } catch (NumberFormatException e) {
            loginUser.setUserId(-1L);
        }

        loginUser.setUserPassword(mPasswordEt.getText().toString());
        mPresenter.identifyAccount(loginUser);
    }

    private void switchView(View gone, View show) {
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_slide_out);
        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_slide_in);

        gone.startAnimation(out);
        gone.setVisibility(View.GONE);

        show.setVisibility(View.VISIBLE);
        show.startAnimation(in);
    }

    private void hideView(View hideView) {
        hideView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_out));
        hideView.setVisibility(View.GONE);
    }

    private void showView(View showView) {
        showView.setVisibility(View.VISIBLE);
        showView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_in));
    }

    private void lockViews() {
        mAccountEt.setEnabled(false);
        mPasswordEt.setEnabled(false);
        mLoginIb.setEnabled(false);
    }

    private void reconnect() {
        if (mReconnectGtv.getVisibility() == View.VISIBLE)
            switchView(mReconnectGtv, mReconnectGtv);

        if (mReconnectGtv.getVisibility() == View.GONE)
            switchView(mLoggingGtv, mReconnectGtv);

        mPresenter.login();
    }

    private void unlockViews() {
        mAccountEt.setEnabled(true);
        mPasswordEt.setEnabled(true);
        mLoginIb.setEnabled(true);
    }
}
