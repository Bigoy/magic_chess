package com.tssss.bysj.game;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Provide help for the normal operation of the game.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class GameHelper {
    @SuppressLint("StaticFieldLeak")
    private static GameHelper gameHelper;

    private int surfaceSize;
    private Context context;

    private GameHelper() {
    }

    public static GameHelper getGameHelper() {
        if (gameHelper == null) {
            gameHelper = new GameHelper();
        }
        return gameHelper;
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
