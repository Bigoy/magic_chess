package com.tssss.bysj.game.hall;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.BaseApplication;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.game.UserInfoActivity;
import com.tssss.bysj.game.friend.FriendsActivity;
import com.tssss.bysj.game.im.ChatListActivity;
import com.tssss.bysj.game.news.veiw.NewsActivity;
import com.tssss.bysj.game.setting.SettingActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

@ViewInject(layoutId = R.layout.activity_hall)
public class HallActivity extends BaseActivity implements OnMenuItemClickListener {
    private ImageView gameNews;
    private ImageView myFriends;
    private ImageView msg;
    private ImageView gameSetting;
    private ImageButton matchGame;

    private Menu menu;

    @Override
    protected void findViews() {
        gameNews = findViewById(R.id.hall_game_news);
        myFriends = findViewById(R.id.hall_friends);
        msg = findViewById(R.id.hall_msg);
        gameSetting = findViewById(R.id.hall_game_setting);
        matchGame = findViewById(R.id.hall_match_game);
    }

    @Override
    protected void setEventListeners() {
        gameNews.setOnClickListener(this);
        myFriends.setOnClickListener(this);
        msg.setOnClickListener(this);
        gameSetting.setOnClickListener(this);
        matchGame.setOnClickListener(this);
    }

    /**
     * 向 HallPresenter 发送游戏匹配请求
     */
    private void matchGame() {
        if (Constant.DEBUG) {
            Logger.log("not match, debug");
        }
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
        switch (v.getId()) {
            case R.id.hall_game_news:
                openActivity(NewsActivity.class);
                break;
            case R.id.hall_friends:
                openActivity(FriendsActivity.class);
                break;
            case R.id.hall_msg:
                openActivity(ChatListActivity.class);
                break;
            case R.id.hall_game_setting:
                openActivity(SettingActivity.class);
                break;
            case R.id.hall_match_game:
                matchGame();
                break;
            default:
        }
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
        List<String> items = new ArrayList<>();
        items.add("退出游戏");
        items.add("我的信息");
//        items.add("游戏帮助");
        menu = new Menu.Builder(this, this)
                .items(items)
                .build();
        menu.display();

    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                menu.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .operationType(AlertDialog.OPERATION_TYPE_NORMAL)
                        .desc("退出游戏")
                        .subDesc("真的不多玩一会儿吗？")
                        .okDesc("不玩了！")
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                backLauncher();
                                BaseApplication.exitApp();
                            }

                            @Override
                            public void no() {

                            }
                        });
                builder.display();
                break;
            case 1:
                Intent intent = new Intent(this, UserInfoActivity.class);
                try {

                    UserInfo myInfo = JMessageClient.getMyInfo();
                    JSONObject jsonObject = new JSONObject(myInfo.getSignature());
                    intent.putExtra(Constant.ROLE_NICK_NAME, jsonObject.getString(Constant.ROLE_NICK_NAME));
                    intent.putExtra(Constant.ROLE_SEX, jsonObject.getString(Constant.ROLE_SEX));
                    intent.putExtra(Constant.ROLE_SIGNATURE, jsonObject.getString(Constant.ROLE_SIGNATURE));

                    String level = jsonObject.getString(Constant.ROLE_LEVEL);
                    intent.putExtra(Constant.ROLE_LEVEL, level);

                } catch (JSONException e) {
                    intent.putExtra(Constant.ROLE_LEVEL, Constant.ROLE_SX);
                } finally {
                    startActivity(intent);
                }

                Log.i("Menu", "item = " + position);
                menu.dismiss();
                break;
         /*   case 2:
                Log.i("Menu", "item = " + position);
                menu.dismiss();
                break;*/
            default:
        }
    }
}
