package com.tssss.bysj.mvp.base;

import android.content.Intent;
import android.os.Bundle;

import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.presenter.LifeCircleMvpPresenter;

/**
 * Created by tssss on 2019/2/13.
 * <p>
 * 中间层，避免所有 Presenter 继承 LifeCircleMvpPresenter 类时都必须实现所有方法导致的代码冗余。
 */
public class BaseMvpPresenter<T extends IMvpView> extends LifeCircleMvpPresenter {

    public BaseMvpPresenter(T view) {
        super(view);
    }

    @Override
    protected IMvpView getEmptyView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, Intent intent, Bundle getArguments) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState, Intent intent, Bundle getArguments) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void destroyView() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }
}
