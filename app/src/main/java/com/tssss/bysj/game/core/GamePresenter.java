package com.tssss.bysj.game.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.Chessman;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.core.other.GameRoleManager;
import com.tssss.bysj.game.core.view.GameActivity;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;

import java.util.HashMap;
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
        // 显示准备游戏中的界面状态
        Map<String, String> prepareMap = new HashMap<>();
        prepareMap.put("operation", "prepare");
        sendMessage(JSON.toJSONString(prepareMap));
        this.myAccountID = myAccountID;
        this.armyAccountID = armyAccountID;
        armyRole = new GameRole();
        selfRole = UserDataCache.readRole();
        handler = new Handler();
        timer = new CountDownTimer(30, GamePresenter.this);
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
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().showArmyInfo(armyRole.getName());
            }
        });
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
        // 5秒后开始游戏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showChessmanCamp(selfRole.getChessmanCamp());
                if (isFirst) {
                    getView().start();

                }
            }
        }, 5000);

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
                    getView().result(gameResult);
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
        timer.cacel();
        timer.reset();
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
    public void sendMessage(String s) {
        //通过username和appkey拿到会话对象，通过指定appkey可以创建一个和跨应用用户的会话对象，从而实现跨应用的消息发送
        Conversation conversation = JMessageClient.getSingleConversation(armyAccountID, null);
        if (conversation == null) {
            conversation = Conversation.createSingleConversation(armyAccountID, null);
        }

        //构造message content对象
        TextContent textContent = new TextContent(s);

        //创建message实体，设置消息发送回调。
        final Message message = conversation.createSendMessage(textContent, armyAccountID);
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
}
