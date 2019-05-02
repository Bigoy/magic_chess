package com.tssss.bysj.game.core.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.game.Chessman;
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.game.core.GameRoleManager;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

@ViewInject(layoutId = R.layout.activity_game)
public class GameActivity extends BaseActivity implements View.OnTouchListener,
        CountDownTimer.ICountTime, OnMenuItemClickListener {

    private GameSurfaceView mGameView;
    private GTextView name;
    private GTextView timeTv;

    private GameRole armyRole;
    private GameRole selfRole;

    private String armyId;
    private Conversation gameConversation;
    private Handler handler;

    private Menu gameMenu;

    private boolean exitNormal = true;

    /**
     * id 小的先下
     */
    public static boolean first;


    @Override
    protected void findViews() {
        mGameView = findViewById(R.id.game_gsv);
        name = findViewById(R.id.game_name);
        timeTv = findViewById(R.id.game_time);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setEventListeners() {
        mGameView.setOnTouchListener(this);
    }


    @Override
    protected void clickTopBarLeft() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .desc("退出游戏？")
                .subDesc("强行退出将自动投降")
                .operationListener(new AlertDialog.OnDialogOperationListener() {
                    @Override
                    public void ok() {
                        Map<String, String> map = new HashMap<>();
                        map.put("operation", "result");
                        map.put("final", "lose");
                        map.put("exp", "-50");
                        sendMessage(JSON.toJSONString(map));

                        Intent intent = new Intent(GameActivity.this, GameResultActivity.class);
                        intent.putExtra("result", "你输了");
                        intent.putExtra("exp", "经验值 -50");
                        intent.putExtra("desc", "不要灰心，总会赢的");
                        startActivity(intent);
                    }

                    @Override
                    public void no() {

                    }
                });
        builder.display();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void afterBindView() {
        List<String> gameMenuItems = new ArrayList<>();
        gameMenuItems.add("投降");
        gameMenuItems.add("催一下对方");
        gameMenuItems.add("需要让棋子");
        gameMenu = new Menu(this, this);
        gameMenu.setMenuItems(gameMenuItems);

        armyRole = new GameRole();
        selfRole = new GameRole();
        handler = new Handler();
        Intent intent = getIntent();

        if (null != intent) {
            JMessageClient.registerEventReceiver(this);
            armyId = intent.getStringExtra(Constant.ACCOUNT_ID);


            JMessageClient.getUserInfo(armyId, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
                        armyRole.setUser(new User(armyId, null));
                        armyRole.setName(map.get(Constant.ROLE_NICK_NAME));
                        armyRole.setSex(map.get(Constant.ROLE_SEX));
                        String level = intent.getStringExtra(Constant.ROLE_LEVEL);
                        if (StringUtil.isBlank(level)) {
                            level = Constant.ROLE_SX_I;
                        }
                        armyRole.setLevel(level);
                        armyRole.setSignature(map.get(Constant.ROLE_SIGNATURE));
                        armyRole.setRoleExperience(Integer.valueOf(map.get(Constant.ROLE_EXP)));

                        ToastUtil.showToast(GameActivity.this, "准备中， 5 秒后开始", ToastUtil.TOAST_DEFAULT);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                long me = Long.parseLong(JMessageClient.getMyInfo().getUserName());
                                long army = Long.valueOf(armyId);


                                if (me < army) {
                                    first = true;
                                    // 分配棋子
                                    selfRole.setChessmanCamp(Chessman.CAMP_SELF);
                                    armyRole.setChessmanCamp(Chessman.CAMP_ARMY);


                                    Map<String, String> map = new HashMap<>();
                                    map.put("operation", "prepare");
                                    map.put("chessman_camp", Chessman.CAMP_ARMY);
                                    sendMessage(JSON.toJSONString(map));


                                    Logger.log(me + "  " + army);
                                    Map<String, String> map1 = new HashMap<>();
                                    map1.put("operation", "first");
                                    map1.put("chessman_index", "");
                                    map1.put("chessman_position", "");

                                    sendMessage(JSON.toJSONString(map1));
                                }

                            }
                        }, 5000);
                        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.SELF, selfRole);
                        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.ARMY, armyRole);

                        name.setText("对手是 " + armyRole.getName());
                        gameConversation = Conversation.createSingleConversation(armyId);
                        mGameView.setHost(GameActivity.this);
                        mGameView.initPlayer(armyRole);
                        mGameView.initTimer(30, GameActivity.this);

                    } else {
                        ToastUtil.showToast(GameActivity.this, "进入游戏失败", ToastUtil.TOAST_ERROR);
                        finish();
                    }
                }
            });

        }
    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    protected void clickTopBarRight() {
        super.clickTopBarRight();
        gameMenu.display();

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.game_title;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGameView.doTouch(event);
        return false;
    }

    @Override
    public void onTicker(int time) {
        timeTv.setText(String.valueOf(time));
        if (time == 10) {
            ToastUtil.showToast(this, "10 秒后自动认输", ToastUtil.TOAST_DEFAULT);
        }
    }

    @Override
    public void onTimerFinish() {
        Intent intent = new Intent(this, GameResultActivity.class);
        intent.putExtra("result", "你输了");
        intent.putExtra("desc", "唉！好可惜");
        intent.putExtra("exp", "经验值 -50");

        Map<String, String> map = new HashMap<>();
        map.put("operation", "result");
        map.put("result", "你赢了");
        map.put("desc", "对方超时自动认输");
        map.put("exp", "经验值 +50");
        sendMessage(JSON.toJSONString(map));

        startActivity(intent);
        finish();
    }


    public void onEventMainThread(MessageEvent event) {
        MessageContent content = event.getMessage().getContent();
        Map<String, String> map = (Map<String, String>) JSON.parse(content.toJson());
        String text = map.get("text");
        Map<String, String> dataMap = (Map<String, String>) JSON.parse(text);
        String operation = dataMap.get("operation");
        if ("prepare".equals(operation)) {
            String camp = dataMap.get("chessman_camp");
            selfRole.setChessmanCamp(camp);
            if (Chessman.CAMP_SELF.equals(camp)) {
                ToastUtil.showToast(this, "你的棋子在左边", ToastUtil.TOAST_DEFAULT);

            } else {
                ToastUtil.showToast(this, "你的棋子在右边", ToastUtil.TOAST_DEFAULT);
            }
        } else if ("first".equals(operation)) {
            mGameView.startGame();
            ToastUtil.showToast(this, "你先走", ToastUtil.TOAST_DEFAULT);
        } else if ("update".equals(operation)) {
            mGameView.updateChessmanPositionWhenCannotTouch(dataMap.get("chessman_index"),
                    dataMap.get("chessman_position"));

        } else if ("turn".equals(operation)) {
            mGameView.startGame();
            ToastUtil.showToast(this, "该你了", ToastUtil.TOAST_DEFAULT);
        } else if ("result".equals(operation)) {
            Intent intent = new Intent(GameActivity.this, GameResultActivity.class);
            intent.putExtra("result", dataMap.get("result"));
            intent.putExtra("desc", dataMap.get("desc"));
            intent.putExtra("exp", dataMap.get("exp"));
            startActivity(intent);
            finish();

        } else if ("surrender".equals(operation)) {
            Intent intent = new Intent(GameActivity.this, GameResultActivity.class);
            intent.putExtra("result", "你赢了");
            intent.putExtra("desc", "你以奇高的技艺迫使对方投降");
            intent.putExtra("exp", "+ 50");
            startActivity(intent);
        } else if ("come_on".equals(operation)) {
            ToastUtil.showToast(this, "亲，这边麻烦快点儿呢？", ToastUtil.TOAST_DEFAULT);
        } else if ("rang_qi".equals(operation)) {
            mGameView.setCanTouch(true);
            ToastUtil.showToast(this, "您需要给对方让一步棋", ToastUtil.TOAST_DEFAULT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
        if (!exitNormal) {
            // 主动退出游戏
            Map<String, String> map = new HashMap<>();
            map.put("operation", "result");
            sendMessage(JSON.toJSONString(map));
        }
    }

    public void sendMessage(String s) {
        //通过username和appkey拿到会话对象，通过指定appkey可以创建一个和跨应用用户的会话对象，从而实现跨应用的消息发送
        Conversation mConversation = JMessageClient.getSingleConversation(armyId, null);
        if (mConversation == null) {
            mConversation = Conversation.createSingleConversation(armyId, null);
        }

        //构造message content对象
        TextContent textContent = new TextContent(s);

        //创建message实体，设置消息发送回调。
        final Message message = mConversation.createSendMessage(textContent, armyId);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    Logger.log("发送成功`");
                }
            }
        });
        MessageSendingOptions options = new MessageSendingOptions();
        options.setShowNotification(false);
        options.setRetainOffline(false);
        JMessageClient.sendMessage(message, options);

    }


    public void gameOver() {


    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                // 投降
                Map<String, String> surrender = new HashMap<>();
                surrender.put("operation", "surrender");
                sendMessage(JSON.toJSONString(surrender));
                gameMenu.dismiss();
                break;
            case 1:
                Map<String, String> come_on = new HashMap<>();
                come_on.put("operation", "come_on");
                sendMessage(JSON.toJSONString(come_on));
                // 催一下
                gameMenu.dismiss();
                break;
            case 2:
                mGameView.setCanTouch(false);
                Map<String, String> rang_qi = new HashMap<>();
                rang_qi.put("operation", "rang_qi");
                sendMessage(JSON.toJSONString(rang_qi));
                // 需要让棋
                gameMenu.dismiss();
                break;
        }
    }
}
