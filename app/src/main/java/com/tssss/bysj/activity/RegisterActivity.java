package com.tssss.bysj.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnRegisterListener;
import com.tssss.bysj.login.LoginActivity;
import com.tssss.bysj.presenter.RegisterPresenter;
import com.tssss.bysj.util.ToastUtil;

public class RegisterActivity extends BaseActivity implements OnRegisterListener {
    private ImageButton mRegisterAccountIb;
    private EditText mAccountEt,
            mPasswordEt;

    private RegisterPresenter mPresenter;

    @Override
    protected void findViews() {
        mRegisterAccountIb = findViewById(R.id.register_ib);
        mAccountEt = findViewById(R.id.register_account_et);
        mPasswordEt = findViewById(R.id.register_key_et);
    }

    @Override
    protected void setEventListeners() {
        mRegisterAccountIb.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getLeftBtnStyle() {
        return R.drawable.ic_btn_home;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.register_title;
    }

    @Override
    protected void clickLeft() {
        openActivity(StartActivity.class);
        finish();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId() == R.id.register_ib) {
            registerAccount();
        }
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new RegisterPresenter();
        return mPresenter;
    }

    /**
     * Register account
     */
    private void registerAccount() {
        /*// Verify the validity of account
        if (!AccountUtil.validAccount(mAccountEt.getText().toString(),
                mPasswordEt.getText().toString())) {

            ToastUtil.showToast(this, getString(R.string.account_invalid),
                    ToastUtil.TOAST_ERROR);
        } else {
            // Register
            User user = new User(Long.parseLong(mAccountEt.getText().toString()),
                    mPasswordEt.getText().toString());
            mPresenter.requestRegister(user, this);
        }*/

        // Test
        openActivity(NewRoleActivity.class);
    }

    /**
     * Registered successfully
     */
    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
    }

    @Override
    public void onRegistered() {
        ToastUtil.showToast(this, getString(R.string.register_registered),
                ToastUtil.TOAST_DEFAULT);
        // Open LoginActivity
        openActivity(LoginActivity.class);
    }
}
