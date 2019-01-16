package com.tssss.bysj.contract;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tssss.bysj.annotation.ContentView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View {
    protected PresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViews();

        // attach mPresenter
        if (attachPresenter() != null) {
            mPresenter = attachPresenter();
            mPresenter.attachView(this);
        }

        //Parse @ContentView annotation
        ContentView cv = getClass().getAnnotation(ContentView.class);
        if (cv != null) {
            setContentView(cv.value());
        } else {
            Log.i(getClass().getSimpleName(), "no layoutId, skipped");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setEventListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * Subclass selects specific Presenter (extends PresenterImp)
     *
     * @return mPresenter
     */
    protected abstract PresenterImp attachPresenter();

    /**
     * FindViewById()
     */
    protected abstract void findViews();

    /**
     * SetXXListener
     */
    protected abstract void setEventListeners();

    /**
     * Open activity without data
     */
    protected void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
