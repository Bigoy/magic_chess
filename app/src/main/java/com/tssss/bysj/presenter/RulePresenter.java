package com.tssss.bysj.presenter;

import android.content.Context;
import android.widget.ImageView;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnRuleDataListener;
import com.tssss.bysj.interfaces.OnRuleListener;
import com.tssss.bysj.model.RuleModel;

import java.util.List;

public class RulePresenter extends PresenterImp implements OnRuleDataListener {
    private RuleModel ruleModel;
    private OnRuleListener listener;

    public void requestRules(Context context, OnRuleListener listener) {
        this.listener = listener;
        ruleModel = new RuleModel();
        ruleModel.loadRulesData(context, this);
    }

    @Override
    public void onLoadRuleData(List<ImageView> rules) {
        listener.onLoadRules(rules);
    }
}
