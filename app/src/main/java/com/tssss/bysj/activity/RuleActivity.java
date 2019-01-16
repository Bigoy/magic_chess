package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.adapter.RuleAdapter;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnRuleListener;
import com.tssss.bysj.presenter.RulePresenter;

import java.util.List;

import androidx.viewpager.widget.ViewPager;

@ContentView(R.layout.activity_rule)
public class RuleActivity extends BaseActivity implements View.OnClickListener, OnRuleListener {
    private ViewPager mRuleVp;
    private ImageView mBackIv;

    private RulePresenter rulePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rulePresenter.requestRules(this, this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        rulePresenter = new RulePresenter();
        return rulePresenter;
    }

    @Override
    protected void findViews() {
        mRuleVp = findViewById(R.id.rule_vp);
        mBackIv = findViewById(R.id.rule_back_iv);
    }

    @Override
    protected void setEventListeners() {
        mBackIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rule_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onLoadRules(List<ImageView> rules) {
        mRuleVp.setAdapter(new RuleAdapter(rules));
    }
}
