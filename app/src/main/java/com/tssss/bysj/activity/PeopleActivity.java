package com.tssss.bysj.activity;

import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;

@ContentView(R.layout.activity_people)
public class PeopleActivity extends BaseActivity {

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setEventListeners() {

    }

    public void back(View view) {
        finish();
    }

}
