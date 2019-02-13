package com.tssss.bysj.mvp;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tssss on 2019/2/13.
 * <p>
 * 本类保存所有的 Presenter 层实例，保证所有的 Presenter 同步关联 View 层的生命周期。
 * <p>
 * 解释一下这里为什么要 implements ILifeCircle ？
 * 答：因为 MvpController 需要在各个生命周期方法中调用 lifeCircles 集合里面持有的所有的 Presenter 的
 * 对应方法。而实现了 IMvpView 接口的 View 层同时需要在自己的生命周期方法中通过 MvpController 同步关联
 * 对应的 Presenter 层的生命周期方法，这就需要开发者在 MvpController 类中手动编写同样多的生命周期方法，
 * 显然这样太麻烦，所以直接实现了 ILifeCircle 接口就是为了不用写这么多的方法名，直接用 ILifeCircle 接口
 * 中的方法名。偷懒而已。所以 MvpController 实际上跟 ILifeCircle 没有继承实现关系。
 */
public class MvpController implements ILifeCircle {
    // 保存所有的 Presenter 实例，使用集合是防止 Presenter 的实例对象重复
    private Set<ILifeCircle> lifeCircles = new HashSet<>();

    public void savePresenter(ILifeCircle lifeCircle) {
        this.lifeCircles.add(lifeCircle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, Intent intent, Bundle getArguments) {
        for (ILifeCircle next : this.lifeCircles) {
            if (intent == null) {
                intent = new Intent();
            }
            if (getArguments == null) {
                getArguments = new Bundle();
            }
            next.onCreate(savedInstanceState, intent, getArguments);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState, Intent intent, Bundle getArguments) {
        for (ILifeCircle next : this.lifeCircles) {
            if (intent == null) {
                intent = new Intent();
            }
            if (getArguments == null) {
                getArguments = new Bundle();
            }
            next.onActivityCreated(savedInstanceState, intent, getArguments);
        }
    }

    @Override
    public void onStart() {
        for (ILifeCircle next : this.lifeCircles) {
            next.onStart();
        }
    }

    @Override
    public void onResume() {
        for (ILifeCircle next : this.lifeCircles) {
            next.onResume();
        }
    }

    @Override
    public void onPause() {
        for (ILifeCircle next : this.lifeCircles) {
            next.onPause();
        }
    }

    @Override
    public void onStop() {
        for (ILifeCircle next : this.lifeCircles) {
            next.onStop();
        }
    }

    @Override
    public void onDestroy() {
        for (ILifeCircle next : this.lifeCircles) {
            next.onDestroy();
        }
        this.lifeCircles.clear();
    }

    @Override
    public void destroyView() {
        for (ILifeCircle next : this.lifeCircles) {
            next.destroyView();
        }
    }

    @Override
    public void onViewDestroy() {
        for (ILifeCircle next : this.lifeCircles) {
            next.onViewDestroy();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        for (ILifeCircle next : this.lifeCircles) {
            next.onNewIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (ILifeCircle next : this.lifeCircles) {
            next.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        for (ILifeCircle next : this.lifeCircles) {
            next.onSaveInstanceState(bundle);
        }
    }

    @Override
    public void attachView(IMvpView iMvpView) {
        for (ILifeCircle next : this.lifeCircles) {
            next.attachView(iMvpView);
        }
    }
}
