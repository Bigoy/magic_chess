package com.tssss.bysj.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.mvp.view.LifeCircleMvpFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseFragment extends LifeCircleMvpFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        ViewInject viewInject = this.getClass().getAnnotation(ViewInject.class);
        if (viewInject != null) {
            int layoutId = viewInject.layoutId();
            if (layoutId > 0) {
                view = initFragmentView(inflater, layoutId);
                findViews();
                afterBindView();
            } else {
                throw new RuntimeException("layoutId <= 0");
            }
        } else {
            throw new RuntimeException("no annotation");
        }
        return view;
    }

    protected abstract void findViews();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEventListeners();
    }


    protected abstract void afterBindView();

    private View initFragmentView(LayoutInflater inflater, int layoutResId) {
        return inflater.inflate(layoutResId, null);
    }

    protected abstract void setEventListeners();

    protected View findViewById(int id) {
        return Objects.requireNonNull(getActivity()).findViewById(id);
    }
}
