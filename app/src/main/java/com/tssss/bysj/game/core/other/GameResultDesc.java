package com.tssss.bysj.game.core.other;

import java.util.Random;

public class GameResultDesc {
    private final static String[] winDesc = new String[]{
            "",
            "",
            "",
            "",
            ""};

    private final static String[] loseDesc = new String[]{
            "",
            "",
            "",
            "",
            ""};

    private final static String[] peaceDesc = new String[]{
            "",
            "",
            "",
            "",
            ""};

    public String getWinDesc() {
        return winDesc[getRandomInt()];
    }

    public String getLoseDesc() {
        return loseDesc[getRandomInt()];
    }

    public String getPeaceDesc() {
        return peaceDesc[getRandomInt()];
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(5);
    }
}
