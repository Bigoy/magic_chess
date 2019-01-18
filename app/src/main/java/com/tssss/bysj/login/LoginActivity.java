package com.tssss.bysj.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnGDialogListener;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GDialog;

public class LoginActivity extends BaseActivity implements OnLoginListener {
    private EditText mAccountEt, mPasswordEt;
    private ImageButton mLoginIb;

    private LoginPresenter mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearInputText();
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new LoginPresenter();
        return mPresenter;
    }

    @Override
    protected void findViews() {
        mAccountEt = findViewById(R.id.login_account_et);
        mPasswordEt = findViewById(R.id.login_key_et);
        mLoginIb = findViewById(R.id.login_ib);
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
        // Verify the validity of account
        if (!AccountUtil.validAccount(mAccountEt.getText().toString(),
                mPasswordEt.getText().toString())) {

            ToastUtil.showToast(this, getString(R.string.account_invalid),
                    ToastUtil.TOAST_ERROR);
        } else {
            // login
            User user = new User(Long.parseLong(mAccountEt.getText().toString()),
                    mPasswordEt.getText().toString());
            mPresenter.requestLogin(user, this);
        }
    }

    private void clearInputText() {
        mAccountEt.setText("");
        mPasswordEt.setText("");
    }

    @Override
    public void onLoginSuccess() {
        ToastUtil.showToast(this, getString(R.string.success), ToastUtil.TOAST_DEFAULT);
//        openActivity(HallActivity.class);
    }

    @Override
    public void onLoginError() {
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onUserNotExit() {
        remindUserRegister();
    }

    @Override
    public void onUserPasswordError() {
        mPasswordEt.setText("");
        ToastUtil.showToast(this, "密码错误，请重试！", ToastUtil.TOAST_ERROR);
    }

    /**
     * Remind user to register game account
     */
    private void remindUserRegister() {
        final GDialog gDialog = new GDialog(this, getString(R.string.register_dialog_title),
                getString(R.string.register_dialog_content));
        gDialog.setOnGDialogListener(new OnGDialogListener() {
            @Override
            public void onPassive() {
                gDialog.dismiss();
            }

            @Override
            public void onPositive() {
//                openActivity(RegisterActivity.class);
            }
        });
        gDialog.show();
    }

}
