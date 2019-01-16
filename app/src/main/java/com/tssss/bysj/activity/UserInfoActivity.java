package com.tssss.bysj.activity;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;

@ContentView(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mBackIb;

    @Override
    protected void findViews() {
        mBackIb = findViewById(R.id.my_back_ib);
    }

    @Override
    protected void setEventListeners() {
        mBackIb.setOnClickListener(this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_back_ib:
                finish();
                break;
        }
    }
}
