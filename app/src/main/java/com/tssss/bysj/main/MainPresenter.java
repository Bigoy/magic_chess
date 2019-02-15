package com.tssss.bysj.main;

import com.tssss.bysj.R;
import com.tssss.bysj.main.home.HomeFragment;
import com.tssss.mvp.base.BaseMvpPresenter;

public class MainPresenter extends BaseMvpPresenter<IMainContract.IView> implements IMainContract.IPresenter {

    public MainPresenter(IMainContract.IView view) {
        super(view);
    }

    @Override
    protected IMainContract.IView getEmptyView() {
        return IMainContract.emptyView;
    }

    @Override
    public void initFragment() {
        getView().replaceFragment(R.id.main_container, new HomeFragment());
    }

}
