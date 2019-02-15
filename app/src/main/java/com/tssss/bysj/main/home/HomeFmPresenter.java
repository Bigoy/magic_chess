package com.tssss.bysj.main.home;

import com.tssss.mvp.base.BaseMvpPresenter;

import androidx.fragment.app.Fragment;

public class HomeFmPresenter extends BaseMvpPresenter<IHomeFmContract.IView> implements IHomeFmContract.IPresenter {
    public HomeFmPresenter(IHomeFmContract.IView view) {
        super(view);
    }

    @Override
    protected IHomeFmContract.IView getEmptyView() {
        return IHomeFmContract.emptyView;
    }

    @Override
    public void replaceFragment(int container, Fragment fragment) {
        getView().switchFragmentPage(container, fragment);
    }
}
