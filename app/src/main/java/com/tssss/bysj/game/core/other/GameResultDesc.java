package com.tssss.bysj.game.core.other;

import java.util.Random;

public class GameResultDesc {

    private final static String[] winDesc = new String[]{
            "恭喜！你赢了！✌",
            "给我找个能打的来👅",
            "小菜一碟嘛简直👍",
            "我果然是最靓的仔😎",
            "不愧是全村的希望"};

    private final static String[] loseDesc = new String[]{
            "遗憾！你输了！😭",
            "就这样输了？💢",
            "不要放弃！应该会赢的？😥",
            "哪里跌倒，哪里爬起来。😱",
            "输了！！！😔"};

    private final static String[] peaceDesc = new String[]{
            "万事和为贵🤝",
            "打成共识🤝",
            "和棋🤝",
            "英雄所见略同🤝",
            "打成共识🤝"};

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
