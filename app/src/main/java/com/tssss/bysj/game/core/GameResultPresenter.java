package com.tssss.bysj.game.core;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.core.other.Rule;
import com.tssss.bysj.other.jmessage.JMessageHelper;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class GameResultPresenter {

    public void uploadUserInfo(String result) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfo myInfo = JMessageClient.getMyInfo();
                GameRole role = JMessageHelper.toGameRole(myInfo);
                Rule rule = Rule.getRule();
                int exp = rule.calculateExp(result);
                int score = rule.calculateScore(result);
                role.upgrade(exp, score, null);
            }
        });
        thread.start();
    }

}
