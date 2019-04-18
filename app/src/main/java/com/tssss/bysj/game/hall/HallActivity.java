package com.tssss.bysj.game.hall;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.game.friend.FriendsActivity;
import com.tssss.bysj.util.DialogUtil;
import com.tssss.bysj.widget.AlertDialog;
import com.tssss.bysj.widget.DialogModel;

@ViewInject(layoutId = R.layout.activity_hall)
public class HallActivity extends BaseActivity {
    private ImageButton game_news;
    private ImageButton my_friends;
    private ImageButton msg;
    private ImageButton game_setting;
    private ImageButton match_game;

    @Override
    protected void findViews() {
        game_news = findViewById(R.id.hall_game_news);
        my_friends = findViewById(R.id.hall_friends);
        msg = findViewById(R.id.hall_msg);
        game_setting = findViewById(R.id.hall_game_setting);
        match_game = findViewById(R.id.hall_match_game);
    }

    @Override
    protected void setEventListeners() {
        game_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openActivity(GameNewsActivity.class);
            }
        });
        my_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FriendsActivity.class);
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openActivity(ChatActivity.class);
            }
        });
        game_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openActivity(SettingActivity.class);
            }
        });
        match_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchGame();
            }
        });
    }

    /**
     * 向 HallPresenter 发送游戏匹配请求
     */
    private void matchGame() {

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
        super.onClick(v);
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

    @Override
    protected void clickTopBarRight() {
        DialogModel dialogModel = new DialogModel.Builder()
                .setTitle("更多")
                .setTime("2019-04-18")
                .setContent("测试内容")
                .build();
        DialogUtil.showAlertDialog(this, dialogModel, new AlertDialog.OnOperationBarClickListener() {
            @Override
            public void clickOperationBar(int index) {
            }
        }, null);
    }
}
