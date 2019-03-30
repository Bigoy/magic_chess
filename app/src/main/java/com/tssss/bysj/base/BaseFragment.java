package com.tssss.bysj.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.mvp.view.LifeCircleMvpFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends LifeCircleMvpFragment {
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        ViewInject viewInject = this.getClass().getAnnotation(ViewInject.class);
        if (viewInject != null) {
            int layoutId = viewInject.layoutId();
            if (layoutId > 0) {
                view = initFragmentView(inflater, layoutId);
                bindView(view);
                afterBindView();
            } else {
                throw new RuntimeException("layoutId <= 0");
            }
        } else {
            throw new RuntimeException("no annotation");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEventListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void afterBindView();

    private View initFragmentView(LayoutInflater inflater, int layoutResId) {
        return inflater.inflate(layoutResId, null);
    }

    private void bindView(View mView) {
        unbinder = ButterKnife.bind(this, mView);
    }

    protected abstract void setEventListeners();
}
