package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;

import androidx.annotation.Nullable;

@ContentView(R.layout.activity_match)
public class MatchActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton mCancelIb;

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }

    @Override
    public void findViews() {
//        mCancelIb = findViewById(R.id.match_cancel_ib);
    }

    @Override
    protected void setEventListeners() {
        mCancelIb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.match_cancel_ib:
                finish();
                break;
            default:
                break;
        }*/
    }
}

