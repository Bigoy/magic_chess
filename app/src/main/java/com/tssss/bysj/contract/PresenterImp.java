package com.tssss.bysj.contract;

import java.lang.ref.WeakReference;

public class PresenterImp implements Presenter {
    private WeakReference<View> mView;

    @Override
    public void attachView(View view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mView.clear();
    }
}
