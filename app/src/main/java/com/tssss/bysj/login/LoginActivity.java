package com.tssss.bysj.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.activity.HallActivity;
import com.tssss.bysj.activity.RegisterActivity;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnGDialogListener;
import com.tssss.bysj.util.NumberUtil;
import com.tssss.bysj.util.PasswordUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GDialog;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements OnLoginListener, View.OnClickListener {
    private EditText mAccountEt, mPasswordEt;
    private ImageButton mLoginIb, mBackIb;

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
        mBackIb = findViewById(R.id.login_back_ib);
    }

    @Override
    protected void setEventListeners() {
        mBackIb.setOnClickListener(this);
        mLoginIb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back_ib:
                finish();
                break;
            case R.id.login_ib:
                login();
                break;
        }
    }

    private void login() {
        if (StringUtil.empty(mAccountEt.getText().toString()) && StringUtil.empty(mPasswordEt.getText().toString())) {
            long userId = Long.parseLong(mAccountEt.getText().toString());
            String password = mPasswordEt.getText().toString();

            if (NumberUtil.isPhoneNumber(userId) && PasswordUtil.isValidPassword(password)) {
                mPresenter.requestLogin(userId, password, this);
            } else {
                ToastUtil.showToast(this, "账户或密码格式错误！", ToastUtil.TOAST_ERROR);
            }
        } else {
            ToastUtil.showToast(this, "账户或密码不能为空！", ToastUtil.TOAST_ERROR);
        }
    }

    private void clearInputText() {
        mAccountEt.setText("");
        mPasswordEt.setText("");
    }

    @Override
    public void onLoginSuccess() {
        ToastUtil.showToast(this, "登录成功！", ToastUtil.TOAST_DEFAULT);
        openActivity(HallActivity.class);
    }

    @Override
    public void onLoginError() {
        ToastUtil.showToast(this, "发生错误，请检查网络！", ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onUserNotExit() {
        final GDialog gDialog = new GDialog(this, "注册提示", "未注册，现在注册？");
        gDialog.setOnGDialogListener(new OnGDialogListener() {
            @Override
            public void onPassive() {
                gDialog.dismiss();
            }

            @Override
            public void onPositive() {
                openActivity(RegisterActivity.class);
            }
        });
        gDialog.show();
    }

    @Override
    public void onUserPasswordError() {
        mPasswordEt.setText("");
        ToastUtil.showToast(this, "密码错误，请重试！", ToastUtil.TOAST_ERROR);
    }

}
