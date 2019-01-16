package com.tssss.bysj.model;

import android.content.Context;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.interfaces.OnRuleDataListener;

import java.util.ArrayList;
import java.util.List;

public class RuleModel {
    public void loadRulesData(Context context, OnRuleDataListener listener) {
        List rules = new ArrayList();

        /*ImageView ruleOne = new ImageView(context);
        ImageView ruleTwo = new ImageView(context);
        ImageView ruleThree = new ImageView(context);
        ImageView ruleFour = new ImageView(context);

        ruleOne.setImageResource(R.drawable.rule_one);
        ruleTwo.setImageResource(R.drawable.rule_two);
        ruleThree.setImageResource(R.drawable.rule_three);
        ruleFour.setImageResource(R.drawable.rule_four);

        rules.add(ruleOne);
        rules.add(ruleTwo);
        rules.add(ruleThree);
        rules.add(ruleFour);*/

        listener.onLoadRuleData(rules);
    }
}
