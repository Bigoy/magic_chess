package com.tssss.bysj.activity;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;

@ContentView(R.layout.activity_start)
public class StartActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mStartIb;
    private ImageButton mRegisterIb;
    private ImageButton mRuleIb;

    @Override
    protected PresenterImp attachPresenter() {
        return null;
    }


    @Override
    protected void findViews() {
       /* mStartIb = findViewById(R.id.start_ib);
        mRegisterIb = findViewById(R.id.start_register_ib);
        mRuleIb = findViewById(R.id.start_rule_ib);*/
    }

    @Override
    protected void setEventListeners() {
        /*mStartIb.setOnClickListener(this);
        mRegisterIb.setOnClickListener(this);
        mRuleIb.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.start_ib:
                openActivity(LoginActivity.class);
                break;
            case R.id.start_register_ib:
                openActivity(RegisterActivity.class);
                break;
            case R.id.start_rule_ib:
                openActivity(RuleActivity.class);
                break;
        }*/
    }

}
