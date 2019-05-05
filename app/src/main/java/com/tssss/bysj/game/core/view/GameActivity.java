package com.tssss.bysj.game.core.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.game.Chessman;
import com.tssss.bysj.game.core.GamePresenter;
import com.tssss.bysj.game.core.IGameContract;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.exceptions.JMessageException;

@ViewInject(layoutId = R.layout.activity_game)
public class GameActivity extends BaseActivity implements View.OnTouchListener,
        OnMenuItemClickListener, IGameContract.IView {

    private GameSurfaceView mGameView;
    private GTextView name;
    private GTextView timeTv;
    private Menu gameMenu;
    private AlertDialog.Builder prepareDialog;

    GamePresenter gamePresenter;

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
                        gamePresenter.surrender();
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
        JMessageClient.registerEventReceiver(this);
        gamePresenter = new GamePresenter(this, this);
        mGameView.setGamePresenter(this.gamePresenter);
        initMenu();
        Intent intent = getIntent();
        if (null != intent) {
            gamePresenter.prepareGame(UserDataCache.readAccount(Constant.ACCOUNT_ID),
                    intent.getStringExtra(Constant.ACCOUNT_ID));
        }
    }

    /**
     * 更多菜单
     */
    private void initMenu() {
        List<String> gameMenuItems = new ArrayList<>();
        gameMenuItems.add("投降");
        gameMenuItems.add("催一下对方");
        gameMenuItems.add("需要让棋");
        gameMenu = new Menu(this, this);
        gameMenu.setMenuItems(gameMenuItems);

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
    protected int getTopBarCenterViewStyle() {
        return R.drawable.game_title;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGameView.doTouch(event);
        return false;
    }

    /**
     * 接受JMessage即时消息
     */
    @SuppressWarnings("unused")
    public void onEventMainThread(MessageEvent event) {
        gamePresenter.handlerJMessageEvent(event);

    }

    /**
     * 页面被强制关闭时，默认判定为投降
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
        gamePresenter.surrender();
    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                // 投降
                gamePresenter.surrender();
                gameMenu.dismiss();
                break;
            case 1:
                gamePresenter.urge();
                // 催一下
                gameMenu.dismiss();
                break;
            case 2:
                gamePresenter.stepBack();
                // 需要让棋
                gameMenu.dismiss();
                break;
        }
    }

    @Override
    public void prepareGame() {
        prepareDialog = new AlertDialog.Builder(this)
                .desc("准备游戏中，请等候...")
                .subDesc("几秒钟就好哦")
                .operationType(AlertDialog.OPERATION_TYPE_SIMPLE);
        prepareDialog.display();

    }

    @Override
    public void start() {
        prepareDialog.dismiss();
        mGameView.touchTrue();

    }

    @Override
    public void syncChessmanPosition(String chessmanKey, String position) {
        mGameView.syncChessmen(chessmanKey, position);

    }

    @Override
    public void turnMe() {
        mGameView.touchTrue();
        ToastUtil.showToast(this, "该你了", ToastUtil.TOAST_DEFAULT);

    }

    @Override
    public void result(GameResult gameResult) {
        /*Intent intent = new Intent(GameActivity.this, GameResultActivity.class);
            intent.putExtra("result", dataMap.get("result"));
            intent.putExtra("desc", dataMap.get("desc"));
            intent.putExtra("exp", dataMap.get("exp"));
            startActivity(intent);
            finish();*/

    }

    @Override
    public void isNotFirst() {
        ToastUtil.showToast(this, "对方先手", ToastUtil.TOAST_DEFAULT);

    }

    @Override
    public void surrender(GameResult gameResult) {

    }

    @Override
    public void showChessmanCamp(String camp) {
        if (Chessman.CAMP_LEFT.equals(camp)) {
            ToastUtil.showToast(this, "你的棋子在左边", ToastUtil.TOAST_DEFAULT);

        } else {
            ToastUtil.showToast(this, "你的棋子在右边", ToastUtil.TOAST_DEFAULT);
        }

    }

    @Override
    public void urge() {
        ToastUtil.showToast(this, "亲，这边麻烦快点儿呢？", ToastUtil.TOAST_ERROR);

    }

    @Override
    public void peace() {

    }

    @Override
    public void stepBack() {
        mGameView.setCanTouch(true);
        ToastUtil.showToast(this, "您需要给对方让一步棋", ToastUtil.TOAST_DEFAULT);

    }

    @Override
    public void timer(int time) {
        timeTv.setText(String.valueOf(time));
        if (time == 10) {
            ToastUtil.showToast(this, "10 秒后自动认输", ToastUtil.TOAST_DEFAULT);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showArmyInfo(String armyName) {
        name.setText("对方是：" + armyName);

    }

    @Override
    public void error() {
        ToastUtil.showToast(this, "进入游戏失败，退出", ToastUtil.TOAST_DEFAULT);
        prepareDialog.dismiss();
        finish();

    }
}
