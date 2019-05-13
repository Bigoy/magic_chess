package com.tssss.bysj.game.help;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;

import java.util.ArrayList;
import java.util.List;

public class RulePresenter extends BaseMvpPresenter<IRuleContract.IView> implements IRuleContract.IPresenter {
    private Context context;

    public RulePresenter(IRuleContract.IView view) {
        super(view);
    }

    @Override
    public void loadRules(Context context) {
        List rules = new ArrayList();
        ImageView ruleOne = new ImageView(context);
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
        rules.add(ruleFour);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().onShowRules(rules);
            }
        });

    }

    @Override
    protected IRuleContract.IView getEmptyView() {
        return IRuleContract.emptyView;
    }
}
