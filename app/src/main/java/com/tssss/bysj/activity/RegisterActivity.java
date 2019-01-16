package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnGDialogListener;
import com.tssss.bysj.interfaces.OnRegisterListener;
import com.tssss.bysj.presenter.RegisterPresenter;
import com.tssss.bysj.util.NumberUtil;
import com.tssss.bysj.util.PasswordUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GDialog;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements View.OnClickListener, OnRegisterListener {

    private EditText mAccountEt, mPasswordEt;
    private ImageButton mRegisterIb, mHomeIb;

    private RegisterPresenter mPresenter;

    @Override
    protected void findViews() {
        mAccountEt = findViewById(R.id.register_account_et);
        mPasswordEt = findViewById(R.id.register_key_et);
        mRegisterIb = findViewById(R.id.register_ib);
        mHomeIb = findViewById(R.id.register_home_ib);
    }

    @Override
    protected void setEventListeners() {
        mRegisterIb.setOnClickListener(this);
        mHomeIb.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearInputText();
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new RegisterPresenter();
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_home_ib:
                openActivity(StartActivity.class);
                break;
            case R.id.register_ib:
                register();
                break;
        }
    }

    private void register() {
        if (StringUtil.empty(mAccountEt.getText().toString()) && StringUtil.empty(mPasswordEt.getText().toString())) {
            long userId = Long.parseLong(mAccountEt.getText().toString());
            String password = mPasswordEt.getText().toString();
            if (NumberUtil.isPhoneNumber(userId) && PasswordUtil.isValidPassword(password)) {
                mPresenter.requestRegister(userId, password, this);
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
    public void onSuccess() {
        ToastUtil.showToast(this, "注册成功！", ToastUtil.TOAST_DEFAULT);
        openActivity(RoleActivity.class);
    }

    @Override
    public void onFail() {
        ToastUtil.showToast(this, "注册出错！", ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onRegistered() {
        ToastUtil.showToast(this, "您已经注册过了！", ToastUtil.TOAST_DEFAULT);
        final GDialog gDialog = new GDialog(this, "创建角色", "需要重新创建一个游戏角色吗？");
        gDialog.setOnGDialogListener(new OnGDialogListener() {
            @Override
            public void onPassive() {
                gDialog.dismiss();
            }

            @Override
            public void onPositive() {
                openActivity(RoleActivity.class);
            }
        });
    }
}
