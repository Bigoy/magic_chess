package com.tssss.bysj.main.home;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.R2;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.main.login.LoginFragment;
import com.tssss.bysj.main.register.RegisterFragment;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

@ViewInject(layoutId = R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements IHomeFmContract.IView {
    @BindView(R2.id.fm_home_login_ib)
    protected ImageButton mLoginIb;
    @BindView(R2.id.fm_home_register_ib)
    protected ImageButton mRegisterIb;

    private IHomeFmContract.IPresenter mPresenter;

    @Override
    protected void afterBindView() {
        mPresenter = new HomeFmPresenter(this);
    }

    @Override
    protected void setEventListeners() {
        mLoginIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到 LoginFragment
                mPresenter.replaceFragment(R.id.main_container, new LoginFragment());
            }
        });
        mRegisterIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.replaceFragment(R.id.main_container, new RegisterFragment());
            }
        });
    }

    @Override
    public void switchFragmentPage(int container, Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(container, fragment).commit();
    }
}
