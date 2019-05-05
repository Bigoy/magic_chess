package com.tssss.bysj.game.core.other;

import android.annotation.SuppressLint;
import android.content.Context;

public class GameUtil {
    @SuppressLint("StaticFieldLeak")
    private static GameUtil gameUtil;

    private int surfaceSize;
    private Context context;

    private GameUtil() {
    }

    public static GameUtil getGameUtil() {
        if (gameUtil == null) {
            gameUtil = new GameUtil();
        }
        return gameUtil;
    }

    public void setSurfaceSize(int surfaceSize) {
        this.surfaceSize = surfaceSize;
    }

    public int getSurfaceSize() {
        return this.surfaceSize;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }
}
