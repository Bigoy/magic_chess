package com.tssss.bysj.util;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.game.core.view.GameResultActivity;

public class IntentUtil {
    public static void startGameResultIntent(@NonNull Activity activity, @NonNull GameResult gameResult) {
        Intent intent = new Intent(activity, GameResultActivity.class);
        String resultJsonStr = JSON.toJSONString(gameResult);
        intent.putExtra("game_result", resultJsonStr);
        activity.startActivity(intent);
        activity.finish();
    }
}
