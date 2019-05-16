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
import com.tssss.bysj.other.jmessage.TextContentFactory;
import com.tssss.bysj.other.jmessage.callback.IGetGameRoleCallBack;
import com.tssss.bysj.user.UserDataCache;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.content.TextContent;

@SuppressWarnings({"Convert2Lambda"})
public class GamePresenter extends BaseMvpPresenter<IGameContract.IView> implements
        IGameContract.IPresenter{
    private GameRole armyRole;
    private GameRole selfRole;

    private String armyAccountID;
    private String myAccountID;
    private Handler handler;
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
    }

    @Override
    public void prepareGame(String myAccountID, String armyAccountID) {
        this.myAccountID = myAccountID;
        this.armyAccountID = armyAccountID;
        selfRole = UserDataCache.readRole();
        if (null == selfRole) {
            throw new IllegalStateException("selfRole = null");
        }
        // 获取对方信息
        JMessageHelper.getGameRoleByAccountID(this.armyAccountID, new IGetGameRoleCallBack() {
            @Override
            public void onComplete(GameRole role) {
                armyRole = role;
                prepareGameScene();
                prepareArmyRole(role);
                preparePlayOrder();
                umpire.ready(GamePresenter.this, myAccountID);
                TextContent ready = TextContentFactory.ready();
                sendMessage(ready);
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().error();
                        Logger.log(this, errorMsg);
                    }
                });
            }
        });

    }

    @Override
    public void peaceDenied() {
        sendMessage(TextContentFactory.peaceDeny());
    }

    @Override
    public void adversityGrantedPeace() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().result(GameResultFactory.peace());
            }
        });
    }

    @Override
    public void adversityDeniedPeace() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().peaceReject("想得美，我不同意和棋！");
            }
        });
    }

    @Override
    public void peaceGranted() {
        sendMessage(TextContentFactory.peaceGrant());
    }

    @Override
    public void adversityPeace() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().peace();
            }
        });

    }

    @Override
    public void beingUrged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().beingUrged();
            }
        });
    }

    @Override
    public void adversitySurrender() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().result(GameResultFactory.win());
            }
        });
    }

    @Override
    public void turn() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().turnMe();
            }
        });
    }

    @Override
    public void notFirst() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().isNotFirst();
            }
        });
    }

    @Override
    public void updateChessmanPosition(Map<String, String> positionMap) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().syncChessmanPosition(
                        positionMap.get("chessman_index"),
                        positionMap.get("chessman_position"));
            }
        });
    }

    @Override
    public void win() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().result(GameResultFactory.win());
            }
        });
    }

    @Override
    public void lose() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().result(GameResultFactory.lose());
            }
        });
    }

    @Override
    public void surrender() {
        sendMessage(TextContentFactory.win());
    }

    @Override
    public void urge() {
        sendMessage(TextContentFactory.urge());
    }

    @Override
    public void stepBack() {
        sendMessage(TextContentFactory.stepBack());
    }

    @Override
    public void adversityStepBack() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().stepBack();
            }
        });
    }

    @Override
    public void peace() {
        TextContent textContent = TextContentFactory.peace();
        JMessageManager.sendTextMessage(armyAccountID, textContent);
    }

    @Override
    public void checkResult() {
        String result = umpire.umpire();
        if (GameResult.COMPETING.equals(result)) {
            sendMessage(TextContentFactory.turn());

        } else if (GameResult.WIN.equals(result)) {
            sendMessage(TextContentFactory.lose());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(GameResultFactory.win());
                }
            });

        } else if (GameResult.LOSE.equals(result)) {
            sendMessage(TextContentFactory.win());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(GameResultFactory.lose());
                }
            });

        }
    }

    @Override
    protected IGameContract.IView getEmptyView() {
        return IGameContract.emptyView;
    }

    private void sendMessage(TextContent textContent) {
        JMessageManager.sendTextMessage(armyAccountID, textContent);
    }

    private void sendMessage(TextContent textContent, JMessageManager.OnSendCompleteCallBack callBack) {
        JMessageManager.sendTextMessage(armyAccountID, textContent, callBack);
    }

    public void receivedSyncChessmanMessage(String key, String position) {
        chessmanManager.syncChessmen(key, position);
    }

    public void checkChessman(int x, int y) {
        chessmanManager.playerPrepareToCheckChessman(x, y);
    }

    public void moveChessman(int x, int y) {
        chessmanManager.moveChessman(x, y);
        Map<String, String> positionMap = new HashMap<>();
        String index = chessmanManager.whoChecked();
        String position = chessmanManager.getChessman(index).getPosition();
        positionMap.put("chessman_index", index);
        positionMap.put("chessman_position", position);
        String positionJsonStr = JSON.toJSONString(positionMap);
        sendMessage(TextContentFactory.chessmanPosition(positionJsonStr));
        chessmanManager.resetChessmenCheckedState();
        checkResult();
    }

    public boolean hasChessmanChecked() {
        return chessmanManager.hasChessmanChecked();
    }

    public boolean canMoveChessman(int x, int y) {
        return rule.canMoveChessman(x, y);
    }

    public void drawGame(Canvas gameCanvas) {
        if (null != gameCanvas) {
            chessboard.draw(gameCanvas);
            chessmanManager.drawChessmen(gameCanvas);
            chessmanManager.drawMark(gameCanvas);
        }
    }

    protected void prepareGameScene() {
        anchorManager.createAnchors();
        chessmanManager.initChessmanPosition();
        anchorManager.initAnchorUseState();
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
            sendNotFirstMessage();
        } else if (myAccountID > armyAccountID) {
            isFirst = false;
            selfRole.setChessmanCamp(Chessman.CAMP_RIGHT);
            armyRole.setChessmanCamp(Chessman.CAMP_LEFT);
        }
        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.SELF, selfRole);
        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.ARMY, armyRole);
    }

    private void sendNotFirstMessage() {
        TextContent textContent = TextContentFactory.notFirst();
        JMessageManager.sendTextMessage(armyAccountID, textContent);
    }

    protected void prepareArmyRole(GameRole armyRole) {
        this.armyRole = armyRole;
    }

    @Override
    public void startGame() {
        // 全部准备完后，3秒后正式开始游戏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showArmyInfo(armyRole.getName());
                getView().showMyChessmenCamp(selfRole.getChessmanCamp());
                getView().start(isFirst);
            }
        }, 3000);
    }

    @Override
    public void adversityReady() {
        umpire.ready(this, armyAccountID);
    }

}
