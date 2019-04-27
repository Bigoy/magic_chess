package com.tssss.bysj.game.core.view;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.hall.HallActivity;

@ViewInject(layoutId = R.layout.game_result_activity)
public class GameResultActivity extends BaseActivity {

    private GTextView result;
    private GTextView desc;
    private GTextView exp;
    private ImageButton i_known;
    private RelativeLayout container;

    private String resultStr;
    private String descStr;
    private String expStr;


    @Override
    protected int getTopBarLeftViewStyle() {
        return 0;
    }

    @Override
    protected void clickTopBarLeft() {

    }

    @Override
    protected void findViews() {
        result = findViewById(R.id.game_result_result);
        desc = findViewById(R.id.game_result_desc);
        exp = findViewById(R.id.game_result_exp);
        i_known = findViewById(R.id.game_result_ok);
        container = findViewById(R.id.game_result_container);
    }

    @Override
    protected void setEventListeners() {
        i_known.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        Intent intent = getIntent();
        if (null != intent) {
            resultStr = intent.getStringExtra("result");
            descStr = intent.getStringExtra("desc");
            expStr = intent.getStringExtra("exp");

            if (resultStr.equals("你输了")) {
//                container.setBackgroundResource(R.drawable.bg_common_gray);
                result.setTextColor(Color.GRAY);
                desc.setTextColor(Color.GRAY);
                exp.setTextColor(Color.GRAY);
            }


            result.setText(resultStr);
            desc.setText(descStr);
            exp.setText(expStr);

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.game_result_ok) {
            openActivity(HallActivity.class);
            finish();
        }
    }
}
