package com.tssss.bysj.login.login;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.login.IAccountContract;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.ToastUtil;

@ViewInject(layoutId = R.layout.activity_login)
public class LoginActivity extends BaseActivity implements IAccountContract.IView {
    private EditText account_et;
    private EditText password_et;
    private ImageButton login_ib;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void findViews() {
        account_et = findViewById(R.id.login_account_et);
        password_et = findViewById(R.id.login_key_et);
        login_ib = findViewById(R.id.login_ib);
    }

    @Override
    protected void setEventListeners() {
        account_et.setOnClickListener(this);
        password_et.setOnClickListener(this);
        login_ib.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_ib:
                User loginUser = new User();
                String account = account_et.getText().toString();
                String password = password_et.getText().toString();
                loginUser.setUserId(account);
                loginUser.setUserPassword(password);
                mLoginPresenter.verifyAccountFormat(loginUser);
                doViewClicked(login_ib);
                break;
        }
    }

    @Override
    protected void afterBindView() {
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.login_title;
    }

    @Override
    public void onAccountFormatError() {
        ToastUtil.showToast(this,getString(R.string.account_invalid_id),
                ToastUtil.TOAST_ERROR);
        account_et.setText("");
    }

    @Override
    public void onPasswordFormatError() {
        ToastUtil.showToast(this,getString(R.string.account_invalid_password),
                ToastUtil.TOAST_ERROR);
        password_et.setText("");
    }

    @Override
    public void onAccountNotFound() {
        ToastUtil.showToast(this,getString(R.string.account_not_found),
                ToastUtil.TOAST_DEFAULT);
    }

    @Override
    public void onPasswordError() {
        ToastUtil.showToast(this,getString(R.string.account_error_password),
                ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onProcess() {
        Log.i(getClass().getSimpleName(), "正在登陆，请稍后...");
    }

    @Override
    public void onConnectionFailure(int code) {
        if (Constant.NET_CODE_OK != code) {
            ToastUtil.showToast(this,getString(R.string.net_can_not_connect_server),
                    ToastUtil.TOAST_ERROR);
        }
    }

    @Override
    public void onSuccess(User user) {
        Log.i(getClass().getSimpleName(), "登陆成功");
    }
}
