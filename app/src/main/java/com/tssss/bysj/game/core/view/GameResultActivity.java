package com.tssss.bysj.game.core.view;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.GameResultPresenter;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.other.GameResultFactory;
import com.tssss.bysj.game.hall.HallActivity;

@ViewInject(layoutId = R.layout.game_result_activity)
public class GameResultActivity extends BaseActivity {

    private GTextView result;
    private GTextView desc;
    private GTextView exp;
    private GTextView level;
    private ImageButton i_known;
    private RelativeLayout container;
    private GameResult gameResult;
    private GameResultPresenter gameResultPresenter;


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
        level = findViewById(R.id.game_result_level_desc);
        i_known = findViewById(R.id.game_result_ok);
        container = findViewById(R.id.game_result_container);
    }

    @Override
    protected void setEventListeners() {
        i_known.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        gameResultPresenter = new GameResultPresenter();
        Intent intent = getIntent();
        if (null != intent) {
            String gameResultJsonStr = intent.getStringExtra("game_result");
            this.gameResult = GameResultFactory.toGameResult(gameResultJsonStr);
            if (null != gameResult) {
                String result = gameResult.getResult();
                if (GameResult.WIN.equals(result)) {
                    showWinStyle();
                } else if (GameResult.LOSE.equals(result)) {
                    showLoseStyle();
                } else if (GameResult.PEACE.equals(result)) {
                    showPeaceStyle();
                }
                fillData();
                gameResultPresenter.uploadUserInfo(gameResult.getResult());
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.game_result_ok) {
            openActivityDelayAndFinishSelf(HallActivity.class, 200);
        }
    }

    protected void showWinStyle() {

    }

    protected void showLoseStyle() {

    }

    protected void showPeaceStyle() {

    }

    protected void fillData() {
        result.setText(gameResult.getResult());
        desc.setText(gameResult.getResultDesc());
        level.setText(gameResult.getLevelDesc());
        exp.setText(gameResult.getExpDesc());
    }

}
