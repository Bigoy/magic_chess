package com.tssss.bysj.other.jmessage;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.core.IGameContract;
import com.tssss.bysj.game.core.view.GameActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.util.ContextUtil;
import com.tssss.bysj.util.IntentUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.Map;

import cn.jpush.im.android.api.content.TextContent;

public class GameMessageHandler extends AbsTextMessageHandler {

    public GameMessageHandler(TextMessageContent textMessageContent) {
        super(textMessageContent);
    }

    @Override
    public void handleTextMessage() {
        TextContent textContent = textMessageContent.getTextContent();
        String from = textMessageContent.getFrom();
        Map<String, String> gameOperationTypeMap = textContent.getStringExtras();
        String gameOperationType = gameOperationTypeMap.get(Constant.MESSAGE_GAME_OPERATION);
        Activity activity = ContextUtil.getCurrentActivity();
        if (null == activity) {
            Logger.log(this, "current activity is null");
        }
        IGameContract.IPresenter gamePresenter = null;
        if (activity instanceof GameActivity) {
            gamePresenter = ((GameActivity) activity).getGamePresenter();
            if (null == gamePresenter) {
                Logger.log(this, "gamePresenter is null");
            }
        }
        switch (gameOperationType) {
            case "game_invitation":
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .subDesc(textContent.getText())
                        .noDesc("拒绝")
                        .okDesc("可以")
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                String grantStr = "对方接受了你的邀请，即将开始游戏";
                                TextContent ok = TextContentFactory.grantGameInvitation(grantStr);
                                JMessageManager.sendTextMessage(from, ok);
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity)
                                        .operationType(AlertDialog.OPERATION_TYPE_SIMPLE)
                                        .desc("请等待对方开始");
                                builder1.display();
                            }

                            @Override
                            public void no() {
                                String denyStr = "对方现在不想玩游戏";
                                TextContent deny = TextContentFactory.denyGameInvitation(denyStr);
                                JMessageManager.sendTextMessage(from, deny);
                            }
                        });
                builder.display();
                break;
            case "game_invitation_grant":
                TextContent startGame = TextContentFactory.notifyAdversityStartGame();
                JMessageManager.sendTextMessage(from, startGame,
                        new JMessageManager.OnSendCompleteCallBack() {
                            @Override
                            public void onSuccess() {
                                IntentUtil.startGameIntent(activity, from);
                            }

                            @Override
                            public void onFailure(String errorMsg) {
                                ToastUtil.showToast(activity, "发生错误", ToastUtil.TOAST_ERROR);
                            }
                        });
                break;
            case "game_invitation_deny":
                ToastUtil.showToast(activity, "对方现在不想玩游戏", ToastUtil.TOAST_ERROR);
                break;
            case "start_game":
                IntentUtil.startGameIntent(activity, from);
                break;
            case "chessman_position":
                Map<String, String> map = textContent.getStringExtras();
                String chessmanPositionJsonStr = map.get("chessman_position");
                Map<String, String> positionMap = (Map<String, String>) JSON.parse(chessmanPositionJsonStr);
                gamePresenter.updateChessmanPosition(positionMap);
                break;
            case "win":
                gamePresenter.win();
                break;
            case "lose":
                gamePresenter.lose();
                break;
            case "adversity_surrender":
                gamePresenter.adversitySurrender();
                break;
            case "being_urge":
                gamePresenter.beingUrged();
                break;
            case "turn":
                gamePresenter.turn();
                break;
            case "not_first":
                gamePresenter.notFirst();
                break;
            case "peace":
                gamePresenter.adversityPeace();
                break;
            case "peace_grant":
                gamePresenter.adversityGrantedPeace();
                break;
            case "peace_deny":
                gamePresenter.adversityDeniedPeace();
                break;
            case "step_back":
                gamePresenter.adversityStepBack();
                break;
            case "ready":
                gamePresenter.adversityReady();
                break;

        }

    }
}
