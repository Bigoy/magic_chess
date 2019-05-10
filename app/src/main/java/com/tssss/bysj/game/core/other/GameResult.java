package com.tssss.bysj.game.core.other;

@SuppressWarnings("unused")
public class GameResult {
    /**
     * 正在对局中
     */
    public static String COMPETING = "competing";
    /**
     * 胜利
     */
    public static String WIN = "WIN";
    /**
     * 失败
     */
    public static String LOSE = "LOSE";
    /**
     * 和棋
     */
    public static String PEACE = "PEACE";

    private String result;

    private String resultDesc;

    private String levelDesc;

    private String expDesc;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

}
