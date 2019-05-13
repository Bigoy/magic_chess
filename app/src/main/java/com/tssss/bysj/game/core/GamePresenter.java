package com.tssss.bysj.game.core;

import android.graphics.Canvas;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.Chessman;
import com.tssss.bysj.game.core.other.AnchorManager;
import com.tssss.bysj.game.core.other.Chessboard;
import com.tssss.bysj.game.core.other.ChessmanManager;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.other.GameResultFactory;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.core.other.GameRoleManager;
import com.tssss.bysj.game.core.other.Rule;
import com.tssss.bysj.game.core.other.Umpire;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.other.jmessage.JMessageHelper;
import com.tssss.bysj.other.jmessage.JMessageManager;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.UserInfo;

@SuppressWarnings({"Convert2Lambda"})
public class GamePresenter extends BaseMvpPresenter<IGameContract.IView> implements
        IGameContract.IPresenter, CountDownTimer.ICountTime {
    private GameRole armyRole;
    private GameRole selfRole;

    private String armyAccountID;
    private String myAccountID;
    private Handler handler;
    private CountDownTimer timer;
    private boolean isFirst;
    private Umpire umpire;
    private ChessmanManager chessmanManager;
    private Chessboard chessboard;
    private Rule rule;
    private AnchorManager anchorManager;

    public GamePresenter(IGameContract.IView view) {
        super(view);
        handler = new Handler();
        umpire = new Umpire();
        chessboard = new Chessboard();
        rule = Rule.getRule();
        anchorManager = AnchorManager.getAnchorManager();
        chessmanManager = ChessmanManager.getChessmanManager();
        timer = new CountDownTimer(30, GamePresenter.this);
    }

    @Override
    public void prepareGame(String myAccountID, String armyAccountID) {
        this.myAccountID = myAccountID;
        this.armyAccountID = armyAccountID;
        // 显示准备游戏中的界面状态
        Map<String, String> prepareMap = new HashMap<>();
        prepareMap.put("operation", "prepare");
        sendMessage(prepareMap);
        armyRole = new GameRole();
        selfRole = UserDataCache.readRole();
        if (null == selfRole) {
            throw new IllegalStateException("selfRole = null");
        }
        // 获取对方信息
        JMessageClient.getUserInfo(armyAccountID, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    prepareGameScene();
                    prepareArmyRole(userInfo);
                    preparePlayOrder();
                    startGame();
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().error();
                            Logger.log(this, s);
                        }
                    });
                }
            }
        });

    }

    public void startTimer() {
        timer.startTimer();

    }

    protected void prepareGameScene() {
        anchorManager.createAnchors();
    }

    protected void preparePlayOrder() {
        long myAccountID = Long.parseLong(this.myAccountID);
        long armyAccountID = Long.valueOf(this.armyAccountID);
        // accountID小的先走
        if (myAccountID < armyAccountID) {
            // 己方先手
            isFirst = true;
            // 分配棋子   先手的棋在屏幕左侧
            selfRole.setChessmanCamp(Chessman.CAMP_LEFT);
            armyRole.setChessmanCamp(Chessman.CAMP_RIGHT);
            // 判定先手
            Map<String, String> firstMap = new HashMap<>();
            firstMap.put("operation", "not_first");
            sendMessage(firstMap);
        } else if (myAccountID > armyAccountID) {
            isFirst = false;
            selfRole.setChessmanCamp(Chessman.CAMP_RIGHT);
            armyRole.setChessmanCamp(Chessman.CAMP_LEFT);
        }
        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.SELF, selfRole);
        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.ARMY, armyRole);
    }

    protected void prepareArmyRole(UserInfo userInfo) {
        armyRole = JMessageHelper.toGameRole(userInfo);
    }

    private void startGame() {
        // 全部准备完后，3秒后正式开始游戏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showArmyInfo(armyRole.getName());
                getView().showMyChessmenCamp(selfRole.getChessmanCamp());
                getView().start(isFirst);
                if (isFirst) {
                    timer.startTimer();
                }
            }
        }, 3000);
    }

    /**
     * 处理JMessage消息事件
     */
    @Override
    public void handlerJMessageEvent(MessageEvent event) {
        MessageContent content = event.getMessage().getContent();
        Map<String, String> map = (Map<String, String>) JSON.parse(content.toJson());
        String text = map.get("text");
        Map<String, String> dataMap = (Map<String, String>) JSON.parse(text);
        if (null == dataMap) {
            throw new NullPointerException("dataMap = null");
        }
        String operation = dataMap.get("operation");
        if ("prepare".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().prepareGame();
                }
            });

        } else if ("not_first".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().isNotFirst();
                }
            });

        } else if ("update".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().syncChessmanPosition(
                            dataMap.get("chessman_index"),
                            dataMap.get("chessman_position"));
                }
            });

        } else if ("turn".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    startTimer();
                    getView().turnMe();
                }
            });
        } else if ("win".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(GameResultFactory.win());
                }
            });

        } else if ("lose".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(GameResultFactory.lose());
                }
            });
        } else if ("surrender".equals(operation)) {
            /*
             * 收到对方投降的消息
             */
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(GameResultFactory.win());
                }
            });
        } else if ("beingUrged".equals(operation)) {
            /*
            收到对方发来的催促消息
             */
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().beingUrged();
                }
            });
        } else if ("step_back".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().stepBack();
                }
            });
        } else if ("peace".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().peace();
                }
            });
        } else if ("peace_agree".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(GameResultFactory.peace());
                }
            });
        } else if ("peace_reject".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().peaceReject("想得美，我不同意和棋！");
                }
            });
        }
    }

    @Override
    public void surrender() {
        /*
         * 自动判定为失败
         * 向对方发送选择投降的消息
         */
        sendSurrenderMessage();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().result(GameResultFactory.lose());
            }
        });
    }

    @Override
    public void cancelAndResetTimer() {
        timer.cancelTimer();
        timer.resetTimer();
    }

    @Override
    public void urge() {
        /*
        发送催促消息给对方
         */
        sendUrgeMessage();
    }

    @Override
    public void stepBack() {
       /*
       向对方请求让棋
        */
        sendStepBackMessage();
    }

    @Override
    public void peace() {
        sendPeaceMessage();
    }

    protected void sendPeaceMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "peace");
        sendMessage(map);
    }

    @Override
    public void checkResult() {
        String result = umpire.umpire();
        if (GameResult.COMPETING.equals(result)) {
            sendTurnMessage();

        } else if (GameResult.WIN.equals(result)) {
            sendWinMessage();

        } else if (GameResult.LOSE.equals(result)) {
            sendLoseMessage();
        }
    }

    public void receivedSyncChessmanMessage(String key, String position) {
        chessmanManager.syncChessmen(key, position);
    }

    public void checkChessman(int x, int y) {
        chessmanManager.playerPrepareToCheckChessman(x, y);
    }

    public void moveChessman(int x, int y) {
        chessmanManager.moveChessman(x, y);
        cancelAndResetTimer();
        sendUpdateChessmanMessage();
        checkResult();
    }

    public boolean hasChessmanChecked() {
        return chessmanManager.hasChessmanChecked();
    }

    public boolean canMoveChessman(int x, int y) {
        return rule.canMoveChessman(x, y);
    }

    public void drawGame(Canvas gameCanvas) {
        chessboard.draw(gameCanvas);
        chessmanManager.drawChessmen(gameCanvas);
        if (!chessmanManager.whoChecked().equals(ChessmanManager.UNKNOWN)) {
            chessmanManager.drawMark(gameCanvas);
        }
    }

    /**
     * 催促对手
     */
    protected void sendUrgeMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "beingUrged");
        sendMessage(map);
    }

    /**
     * 向对方发送让棋请求
     */
    protected void sendStepBackMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "step_back");
        sendMessage(map);
    }

    /**
     * 通知对手更新棋子的位置
     */
    protected void sendUpdateChessmanMessage() {
        Map<String, String> map = new HashMap<>();
        String updateChessmanIndex = chessmanManager.whoChecked();
        String updateChessmanPosition = chessmanManager.getChessman(updateChessmanIndex).getPosition();
        map.put("chessman_index", updateChessmanIndex);
        map.put("chessman_position", updateChessmanPosition);
        map.put("operation", "update");
        sendMessage(map);
        chessmanManager.resetChessmenCheckedState();
    }

    /**
     * 通知对手走棋
     */
    protected void sendTurnMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "turn");
        sendMessage(map);
    }

    /**
     * 通知对手在本局比赛中失败
     */
    protected void sendWinMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "lose");
        sendMessage(map);
    }

    /**
     * 通知对手在本局比赛中胜利
     */
    protected void sendLoseMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "win");
        sendMessage(map);
    }

    /**
     * 通知对手我选择投降
     */
    protected void sendSurrenderMessage() {
        Map<String, String> map = new HashMap<>();
        map.put("operation", "surrender");
        sendMessage(map);
    }

    @Override
    protected IGameContract.IView getEmptyView() {
        return IGameContract.emptyView;
    }

    @Override
    public void onTicker(int time) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().timer(time);
            }
        });

    }

    @Override
    public void onTimerFinish() {
        // 倒计时结束，自动结束游戏，判定己方 - 失败


    }

    /**
     * 发送即时游戏数据
     */
    public void sendMessage(Map<String, String> contentMap) {
        if (StringUtil.isBlank(this.armyAccountID)) {
            throw new IllegalArgumentException("armyAccountID = null");
        }
        String contentJson = JSON.toJSONString(contentMap);
        if (!StringUtil.isBlank(contentJson)) {
            JMessageManager.sendTextMessage(this.armyAccountID, contentJson);
        }
    }
}
