package com.tssss.bysj.game.core;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.Chessman;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.core.other.GameRoleManager;
import com.tssss.bysj.game.core.view.GameActivity;
import com.tssss.bysj.game.im.JMessageManager;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class GamePresenter extends BaseMvpPresenter<IGameContract.IView> implements IGameContract.IPresenter,
        CountDownTimer.ICountTime {
    private GameRole armyRole;
    private GameRole selfRole;

    private String armyAccountID;
    private String myAccountID;
    private Handler handler;
    private CountDownTimer timer;
    private boolean isFirst;
    private GameActivity gameActivity;

    public GamePresenter(GameActivity gameActivity, IGameContract.IView view) {
        super(view);
        this.gameActivity = gameActivity;
        handler = new Handler();
        timer = new CountDownTimer(30, GamePresenter.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycler();
    }

    private void recycler() {
        gameActivity = null;
    }

    @Override
    public void prepareGame(String myAccountID, String armyAccountID) {
        this.myAccountID = myAccountID;
        this.armyAccountID = armyAccountID;
        // 显示准备游戏中的界面状态
        Map<String, String> prepareMap = new HashMap<>();
        prepareMap.put("operation", "prepare");
        sendMessage(JSON.toJSONString(prepareMap));
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
                    prepare(userInfo);

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().error();
                        }
                    });
                }
            }
        });

    }

    /**
     * 获取对方信息成功后开始准备游戏
     */
    private void prepare(UserInfo userInfo) {
        Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
        armyRole.setUser(new User(armyAccountID, null));
        armyRole.setName(map.get(Constant.ROLE_NICK_NAME));
        armyRole.setSex(map.get(Constant.ROLE_SEX));
        armyRole.setLevel(map.get(Constant.ROLE_LEVEL));
        armyRole.setSignature(map.get(Constant.ROLE_SIGNATURE));
        armyRole.setRoleExperience(Integer.valueOf(map.get(Constant.ROLE_EXP)));
        armyRole.setScore(Integer.valueOf(map.get(Constant.ROLE_SCORE)));
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
            sendMessage(JSON.toJSONString(firstMap));
        } else if (myAccountID > armyAccountID) {
            isFirst = false;
            selfRole.setChessmanCamp(Chessman.CAMP_RIGHT);
            armyRole.setChessmanCamp(Chessman.CAMP_LEFT);
        }
        // 添加游戏双方角色到GameRoleManager
        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.SELF, selfRole);
        GameRoleManager.getGameRoleManager().addPlayer(GameRoleManager.ARMY, armyRole);
        startGame();
    }

    private void startGame() {
        // 全部准备完后，3秒后正式开始游戏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showArmyInfo(armyRole.getName());
                getView().showChessmanCamp(selfRole.getChessmanCamp());
                getView().start(isFirst);
                timer.startTimer();
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
                    getView().syncChessmanPosition(dataMap.get("chessman_index"),
                            dataMap.get("chessman_position"));
                }
            });

        } else if ("turn".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().turnMe();
                }
            });
        } else if ("result".equals(operation)) {
            GameResult gameResult = new GameResult();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().result(dataMap.get("chessman_index"),
                            dataMap.get("chessman_position"),
                            gameResult);
                }
            });

        } else if ("surrender".equals(operation)) {
            GameResult gameResult = new GameResult();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().surrender(gameResult);
                }
            });

        } else if ("come_on".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().urge();
                }
            });
        } else if ("rang_qi".equals(operation)) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getView().stepBack();
                }
            });

        }

    }

    @Override
    public void surrender() {

    }

    @Override
    public void cancelAndResetTimer() {
        timer.cancelTimer();
        timer.resetTimer();
    }

    @Override
    public void urge() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().urge();
            }
        });
    }

    @Override
    public void stepBack() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().stepBack();
            }
        });

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
    public void sendMessage(String content) {
        if (StringUtil.isBlank(this.armyAccountID)) {
            throw new IllegalArgumentException("armyAccountID = null");
        }
        JMessageManager.sendTextMessage(this.armyAccountID, content);

    }
}
