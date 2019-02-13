package com.tssss.bysj.mvp;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by tssss on 2019/2/13.
 * <p>
 * 利用 Java 中介者设计模式搭建的 MVP 架构。
 * <p>
 * 本接口担任中介者设计模式中的抽象中介者角色，属于 Presenter 层，对应 Activity 和 Fragment 的生命周期。当 View 层处于其自身生命
 * 周期的某一时刻时，实现本接口的 Presenter 层同步调用对应的生命周期方法。
 * <p>
 * 本接口包含 Activity 和 Fragment 的所有生命周期方法。
 */
public interface ILifeCircle {
    /**
     * Activity 或 Fragment 创建时调用
     */
    void onCreate(Bundle savedInstanceState, Intent intent, Bundle getArguments);

    /**
     * 对应 Fragment 的 onActivityCreated() 方法
     */
    void onActivityCreated(Bundle savedInstanceState, Intent intent, Bundle getArguments);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void destroyView();

    void onViewDestroy();

    void onNewIntent(Intent intent);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onSaveInstanceState(Bundle bundle);

    void attachView(IMvpView iMvpView);
}
