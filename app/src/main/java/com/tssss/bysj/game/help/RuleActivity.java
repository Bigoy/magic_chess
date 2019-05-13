package com.tssss.bysj.game.help;

import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.tssss.bysj.R;
import com.tssss.bysj.adapter.RuleAdapter;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;

import java.util.List;

@ViewInject(layoutId = R.layout.activity_rule)
public class RuleActivity extends BaseActivity implements IRuleContract.IView {
    private ViewPager mRuleVp;

    private RulePresenter rulePresenter;

    @Override
    protected void findViews() {
        mRuleVp = findViewById(R.id.rule_vp);
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.rule_title;
    }

    @Override
    protected void afterBindView() {
        rulePresenter = new RulePresenter(this);
        rulePresenter.loadRules(this);
    }

    @Override
    public void onShowRules(List<ImageView> rules) {
        mRuleVp.setAdapter(new RuleAdapter(rules));
    }
}
