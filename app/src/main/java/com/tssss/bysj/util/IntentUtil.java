package com.tssss.bysj.util;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.view.GameActivity;
import com.tssss.bysj.game.core.view.GameResultActivity;
import com.tssss.bysj.game.im.ChatActivity;
import com.tssss.bysj.other.Constant;

public class IntentUtil {
    public static void startGameResultIntent(@NonNull Activity activity, @NonNull GameResult gameResult) {
        Intent intent = new Intent(activity, GameResultActivity.class);
        String resultJsonStr = JSON.toJSONString(gameResult);
        intent.putExtra("game_result", resultJsonStr);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startChatIntent(@NonNull Activity activity, String targetID) {
        Intent chatIntent = new Intent(activity, ChatActivity.class);
        chatIntent.putExtra(Constant.ACCOUNT_ID, targetID);
        activity.startActivity(chatIntent);
    }

    public static void startGameIntent(@NonNull Activity activity, String targetID) {
        Intent gameIntent = new Intent(activity, GameActivity.class);
        gameIntent.putExtra(Constant.ACCOUNT_ID, targetID);
        activity.startActivity(gameIntent);
    }

}
