package com.tssss.bysj.game;

import com.tssss.bysj.game.role.GameRoleManager;

public class GameManager {
    public void prepare(AnchorManager am, ChessmanManager cm, GameRoleManager pm) {
        am.initAnchorUseState();

        cm.initChessmanPosition();
        cm.initChessmanCamp();
        cm.resetChessmenCheckedState();
    }
}
