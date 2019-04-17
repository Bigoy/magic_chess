package com.tssss.bysj.game.hall;

import android.util.Log;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;

@ViewInject(layoutId = R.layout.activity_hall)
public class HallActivity extends BaseActivity {

    @Override
    protected void findViews() {
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.hall_title;
    }

    @Override
    protected int getTopBarLeftViewStyle() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        Log.i("KEY_BACK", "BACK REFUSED");
    }
}
