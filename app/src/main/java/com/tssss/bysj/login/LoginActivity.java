package com.tssss.bysj.login;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.game.hall.HallActivity;
import com.tssss.bysj.http.HttpClient;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.util.UserDataCache;
import com.tssss.bysj.widget.GTextView;

import androidx.annotation.Nullable;

@ViewInject(layoutId = R.layout.activity_login)
public class LoginActivity extends BaseActivity implements IAccountContract.IView {
    private EditText account_et;
    private EditText password_et;
    private ImageButton login_ib;
    private GTextView account_gtv;
    private GTextView account_error_gtv;
    private GTextView password_gtv;
    private GTextView password_error_gtv;
    private GTextView logging_gtv;
    private GTextView connect_failure_gtv;

    private LoginPresenter mLoginPresenter;
    private int loginCount;
    private Handler mHandler;
    private User mLoginUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account_et.setText(UserDataCache.readAccount(this, UserDataCache.ACCOUNT));
        password_et.setText(UserDataCache.readAccount(this, UserDataCache.PASSWORD));
    }

    @Override
    protected void findViews() {
        account_et = findViewById(R.id.login_account_et);
        password_et = findViewById(R.id.login_key_et);
        login_ib = findViewById(R.id.login_ib);
        account_gtv = findViewById(R.id.login_account_gtv);
        account_error_gtv = findViewById(R.id.login_account_error_gtv);
        password_gtv = findViewById(R.id.login_password_gtv);
        password_error_gtv = findViewById(R.id.login_password_error_gtv);
        logging_gtv = findViewById(R.id.login_logging_gtv);
        connect_failure_gtv = findViewById(R.id.login_connect_failure_gtv);
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
                String account = account_et.getText().toString();
                String password = password_et.getText().toString();
                mLoginUser.setUserId(account);
                mLoginUser.setUserPassword(password);
                mLoginPresenter.verifyAccountFormat(mLoginUser);
                break;
        }
    }

    @Override
    protected void afterBindView() {
        mHandler = new Handler();
        mLoginPresenter = new LoginPresenter(this, this);
        mLoginUser = new User();
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.login_title;
    }

    @Override
    public void onAccountFormatError() {
        if (account_error_gtv.getVisibility() == View.VISIBLE) {
            AnimationUtil.flipView(this, account_error_gtv, account_error_gtv);
        } else {
            AnimationUtil.flipView(this, account_gtv, account_error_gtv);
        }
        account_et.setText("");
    }

    @Override
    public void onPasswordFormatError() {
        if (password_error_gtv.getVisibility() == View.VISIBLE) {
            AnimationUtil.flipView(this, password_error_gtv, password_error_gtv);
        } else {
            AnimationUtil.flipView(this, password_gtv, password_error_gtv);
        }
        password_et.setText("");
    }

    @Override
    public void onValidAccount() {
        if (account_error_gtv.getVisibility() == View.VISIBLE) {
            AnimationUtil.flipView(this, account_error_gtv, account_gtv);
        }
    }

    @Override
    public void onValidPassword() {
        if (password_error_gtv.getVisibility() == View.VISIBLE) {
            AnimationUtil.flipView(this, password_error_gtv, password_gtv);
        }
    }

    @Override
    public void onAccountNotFound() {
        ToastUtil.showToast(this, getString(R.string.account_not_found),
                ToastUtil.TOAST_DEFAULT);
    }

    @Override
    public void onPasswordError() {
        password_error_gtv.setText(getString(R.string.account_error_password));
        AnimationUtil.flipView(this, password_gtv, password_error_gtv);
        password_et.setText("");
    }

    @Override
    public void onProcess() {
        if (loginCount == 0) {
            lockViews();
            // 保存用户当前输入的账户信息，此时无需验证账户与密码的一致性
            UserDataCache.saveAccount(this, mLoginUser);
            AnimationUtil.flipView(this, login_ib, logging_gtv);
            mLoginPresenter.confirmAccountOperation();
        }
    }

    @Override
    public void onConnectionFailure(int code) {
        if (Constant.NET_CODE_OK != code) {
            logging_gtv.clearAnimation();
            connect_failure_gtv.setText("连接失败");
            AnimationUtil.flipView(this, logging_gtv, connect_failure_gtv);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AnimationUtil.flipView(LoginActivity.this, connect_failure_gtv, login_ib);
                    unlockViews();
                }
            }, 1500);
        }
    }

    @Override
    public void onSuccess(User user) {
        loginCount = 0;
        Log.i(getClass().getSimpleName(), "登陆成功");
        Log.i("userId", user.getUserId());
        Log.i("userPwd", user.getUserPassword());
        openActivityAndFinishSelf(HallActivity.class);
    }

    /**
     * 锁定输入和登录按钮
     * <p>
     * 处于正在登录状态时不允许用户执行操作
     */
    protected void lockViews() {
        login_ib.setEnabled(false);
        account_et.setEnabled(false);
        password_et.setEnabled(false);
    }

    /**
     * 解锁输入和登录按钮
     */
    protected void unlockViews() {
        login_ib.setEnabled(true);
        account_et.setEnabled(true);
        password_et.setEnabled(true);
    }


}
