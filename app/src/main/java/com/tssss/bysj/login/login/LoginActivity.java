package com.tssss.bysj.login.login;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.login.IAccountContract;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GTextView;

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
    private GTextView reconnect_gtv;

    private LoginPresenter mLoginPresenter;
    private int loginCount;

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
        reconnect_gtv = findViewById(R.id.login_reconnect_gtv);
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
        if (account_error_gtv.getVisibility() == View.VISIBLE) {
            flipView(account_error_gtv, account_error_gtv);
        } else {
            flipView(account_gtv, account_error_gtv);
        }
        account_et.setText("");
    }

    @Override
    public void onPasswordFormatError() {
        if (password_error_gtv.getVisibility() == View.VISIBLE) {
            flipView(password_error_gtv, password_error_gtv);
        } else {
            flipView(password_gtv, password_error_gtv);
        }
        password_et.setText("");
    }

    @Override
    public void onValidAccount() {
        if (account_error_gtv.getVisibility() == View.VISIBLE) {
            flipView(account_error_gtv, account_gtv);
        }
    }

    @Override
    public void onValidPassword() {
        if (password_error_gtv.getVisibility() == View.VISIBLE) {
            flipView(password_error_gtv, password_gtv);
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
        flipView(password_gtv, password_error_gtv, R.anim.alpha_slide_in, R.anim.alpha_slide_out);
        password_et.setText("");
    }

    @Override
    public void onProcess() {
        if (loginCount == 0) {
            if (login_ib.getVisibility() == View.VISIBLE) {
                flipView(login_ib, logging_gtv);
            }
            lockViews();
            // 延时一秒执行登录操作，登录中提示不显示
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoginPresenter.confirmAccountOperation();
                }
            }, 400);
        }
    }

    @Override
    public void onConnectionFailure(int code) {
        Log.i("code", "" + code);
        if (Constant.NET_CODE_OK != code) {
            loginCount++;
            Log.wtf("loginCount", "" + loginCount);

            if (loginCount == 7) {
                if (reconnect_gtv.getVisibility() == View.VISIBLE) {
                    flipView(reconnect_gtv, login_ib);
                }
                unlockViews();
                loginCount = 0;
                ToastUtil.showToast(this, getString(R.string.net_can_not_connect_server)
                        + "\n" + code, ToastUtil.TOAST_ERROR);

            } else if (loginCount < 4) {
                flipView(logging_gtv, logging_gtv);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoginPresenter.confirmAccountOperation();
                    }
                }, 500);
            } else if (loginCount < 7) {
                reconnect();
            }

        }
    }

    @Override
    public void onSuccess(User user) {
        loginCount = 0;
        Log.i(getClass().getSimpleName(), "登陆成功");
        Log.i("userId", user.getUserId());
        Log.i("userPwd", user.getUserPassword());
    }

    protected void lockViews() {
        login_ib.setEnabled(false);
        account_et.setEnabled(false);
        password_et.setEnabled(false);
    }

    protected void unlockViews() {
        login_ib.setEnabled(true);
        account_et.setEnabled(true);
        password_et.setEnabled(true);
    }

    protected void hideView(View hideView) {
        hideView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_out));
        hideView.setVisibility(View.GONE);
    }

    protected void showView(View showView) {
        showView.setVisibility(View.VISIBLE);
        showView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in));
    }

    protected void reconnect() {
        if (reconnect_gtv.getVisibility() == View.VISIBLE)
            flipView(reconnect_gtv, reconnect_gtv);

        if (reconnect_gtv.getVisibility() == View.GONE)
            flipView(logging_gtv, reconnect_gtv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoginPresenter.confirmAccountOperation();
            }
        }, 500);
    }

}
