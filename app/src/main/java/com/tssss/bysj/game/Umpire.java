package com.tssss.bysj.game;

import com.tssss.bysj.game.role.GameRoleManager;
import com.tssss.bysj.util.GameUtil;

/*
裁判官类。
负责判定输赢、友好结束游戏。
 */
public class Umpire {

    public Umpire() {
    }

    /*
    裁判判定游戏结果，有结果时，返回胜利的玩家对象的key值；无结果时，返回GamerResult.COMPETING。
    有关玩家的获取操作请使用PlayerManager类。
     */
    public String umpire() {
        ChessmanManager cm = ChessmanManager.getChessmanManager();
        AnchorManager am = AnchorManager.getAnchorManager();
        GameRoleManager pm = GameRoleManager.getGameRoleManager();

        GameUtil gameUtil = GameUtil.getGameUtil();
        int tempA = gameUtil.getSurfaceSize() / 4 * 3;
        int tempB = gameUtil.getSurfaceSize() / 4 * 2;

        if (am.getAnchor(cm.getChessman(ChessmanManager.SELF_A).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.SELF_B).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.SELF_C).getPosition()).getY() == tempA) {
            if (am.getAnchor(cm.getChessman(ChessmanManager.ARMY_A).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.ARMY_B).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.ARMY_C).getPosition()).getY() == tempB) {
                // self输掉对局。self经验减去50，army经验加上50。
                pm.getPlayer(GameRoleManager.SELF).setRoleExperience(-50);
                pm.getPlayer(GameRoleManager.ARMY).setRoleExperience(50);
                // 游戏结束。
                stop();
                return GameRoleManager.ARMY;
            }
        } else if (am.getAnchor(cm.getChessman(ChessmanManager.ARMY_A).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.ARMY_B).getPosition()).getY() == tempA &&
                am.getAnchor(cm.getChessman(ChessmanManager.ARMY_C).getPosition()).getY() == tempA) {
            if (am.getAnchor(cm.getChessman(ChessmanManager.SELF_A).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.SELF_B).getPosition()).getY() == tempB &&
                    am.getAnchor(cm.getChessman(ChessmanManager.SELF_C).getPosition()).getY() == tempB) {
                // self赢得比赛。self经验加上50，army经验减去50。
                pm.getPlayer(GameRoleManager.SELF).setRoleExperience(-50);
                pm.getPlayer(GameRoleManager.ARMY).setRoleExperience(50);
                // 游戏结束。
                stop();
                return GameRoleManager.SELF;
            }
        }
        return GameResult.COMPETING;
    }

    /*
    游戏结束。
     */
    private void stop() {
        // 停止绘制。
        GameSurfaceView.isDrawing = false;
        // 游戏结果弹窗。
    }
}
