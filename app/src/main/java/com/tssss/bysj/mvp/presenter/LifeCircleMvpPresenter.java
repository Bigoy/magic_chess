package com.tssss.bysj.mvp.presenter;

import android.util.Log;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.lang.ref.WeakReference;

/**
 * Created by tssss on 2019/2/13.
 * <p>
 * 本类担任中介者设计模式中的二级抽象中介者角色。
 */
public abstract class LifeCircleMvpPresenter<T extends IMvpView> implements ILifeCircle {
    protected WeakReference<T> weakReference;

    protected LifeCircleMvpPresenter() {
        super();
    }

    public LifeCircleMvpPresenter(IMvpView iMvpView) {
        super();
        attachView(iMvpView);
        MvpController mvpController = iMvpView.getMvpController();
        mvpController.savePresenter(this);
    }

    @Override
    public void attachView(IMvpView iMvpView) {
        if (weakReference == null) {
            weakReference = new WeakReference(iMvpView);
        } else {
            T view = weakReference.get();
            if (view != iMvpView) {
                weakReference = new WeakReference(iMvpView);
            }
        }
    }

    @Override
    public void onDestroy() {
        // View 层生命周期结束时，回收其对象，解决由 View 层对象没有及时回收导致的 OOM
        weakReference.clear();
        weakReference = null;

        Log.wtf(getClass().getSimpleName(), "当前绑定的 View 层被摧毁了");
    }

    // 返回 Presenter 层所持有的 View 层的引用
    protected T getView() {
        T view = weakReference != null ? weakReference.get() : null;
        if (view == null) {
            return getEmptyView();
        }
        return view;
    }

    // 防止 View 层生命周期结束时，Presenter 层依然调用 View 方法出现空指针异常导致软件崩溃
    protected abstract T getEmptyView();
}
