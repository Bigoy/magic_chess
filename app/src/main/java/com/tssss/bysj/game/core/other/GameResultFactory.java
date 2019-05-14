package com.tssss.bysj.game.core.other;

import com.alibaba.fastjson.JSON;

import java.util.Map;

@SuppressWarnings("unchecked")
public class GameResultFactory {

    public static GameResult win() {
        GameResultDesc gameResultDesc = new GameResultDesc();
        GameResult win = new GameResult();
        win.setResult(GameResult.WIN);
        win.setResultDesc(gameResultDesc.getWinDesc());
        win.setLevelDesc("");
        win.setExpDesc("");
        return win;
    }

    public static GameResult lose() {
        GameResultDesc gameResultDesc = new GameResultDesc();
        GameResult lose = new GameResult();
        lose.setResult(GameResult.LOSE);
        lose.setResultDesc(gameResultDesc.getLoseDesc());
        lose.setLevelDesc("");
        lose.setExpDesc("");
        return lose;
    }

    public static GameResult peace() {
        GameResultDesc gameResultDesc = new GameResultDesc();
        GameResult peace = new GameResult();
        peace.setResult(GameResult.PEACE);
        peace.setResultDesc(gameResultDesc.getPeaceDesc());
        peace.setLevelDesc("");
        peace.setExpDesc("");
        return peace;
    }

    public static GameResult toGameResult(String gameResultJsonStr) {
        GameResult gameResult = new GameResult();
        Map<String, String> map = (Map<String, String>) JSON.parse(gameResultJsonStr);
        if (null != map && map.size() > 0) {
            gameResult.setResult(map.get("result"));
            gameResult.setResultDesc(map.get("result_desc"));
            gameResult.setLevelDesc(map.get("level_desc"));
            gameResult.setExpDesc(map.get("exp_desc"));
        }
        return gameResult;
    }
}
