package com.tssss.bysj.game.core.other;

/*
裁判官类。
负责判定输赢、友好结束游戏。
 */
public class Umpire {

    public Umpire() {
    }

    /**
     * 裁判判定游戏结果，有结果时，返回胜利的玩家对象的key值；无结果时，返回GamerResult.COMPETING。
     */
    public String umpire() {
        ChessmanManager cm = ChessmanManager.getChessmanManager();
        AnchorManager am = AnchorManager.getAnchorManager();

        GameUtil gameUtil = GameUtil.getGameUtil();
        int tempA = gameUtil.getSurfaceSize() / 4 * 3;
        int tempB = gameUtil.getSurfaceSize() / 4 * 2;

        if (am.getAnchor(cm.getChessman(ChessmanManager.SELF_A).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.SELF_B).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.SELF_C).getPosition()).getY() == tempA) {
            if (am.getAnchor(cm.getChessman(ChessmanManager.ARMY_A).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.ARMY_B).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.ARMY_C).getPosition()).getY() == tempB) {
                return GameResult.LOSE;
            }
        } else if (am.getAnchor(cm.getChessman(ChessmanManager.ARMY_A).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.ARMY_B).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.ARMY_C).getPosition()).getY() == tempA) {
            if (am.getAnchor(cm.getChessman(ChessmanManager.SELF_A).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.SELF_B).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.SELF_C).getPosition()).getY() == tempB) {
                return GameResult.WIN;
            }
        }
        return GameResult.COMPETING;
    }

    /**
     * 发布游戏结果 胜利
     */
    public void win() {


    }

    /**
     * 发布游戏结果 失败
     */
    public void lose() {


    }
}
