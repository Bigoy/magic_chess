package com.tssss.bysj.game.core.other;

import com.tssss.bysj.game.core.GamePresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;

/*
裁判官类。
负责判定输赢、友好结束游戏。
 */
public class Umpire {
    private boolean iReadyState;

    private boolean adversityReadyState;

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

    public void ready(GamePresenter gamePresenter, String accountID) {
        String myAccountID = UserDataCache.readAccount(Constant.ACCOUNT_ID);
        if (myAccountID.equals(accountID)) {
            iReadyState = true;
        }else if (!myAccountID.equals(accountID)){
            adversityReadyState = true;
        }
        if (iReadyState && adversityReadyState) {
            gamePresenter.startGame();
            Logger.log("双方已经准备");
        }
    }

}
