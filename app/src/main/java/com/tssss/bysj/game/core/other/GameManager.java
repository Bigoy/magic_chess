package com.tssss.bysj.game.core.other;


public class GameManager {
    public void prepare(AnchorManager am, ChessmanManager cm, GameRoleManager pm) {
        am.initAnchorUseState();

        cm.initChessmanPosition();
        cm.initChessmanCamp();
        cm.resetChessmenCheckedState();
    }
}
