package com.tssss.bysj.game;

/**
 * @author Tssss
 * @date 2019-1-24
 */
public class Turn {
    private static Turn instance;

    private boolean turn = true;


    private Turn() {
    }

    public static synchronized Turn getInstance() {
        if (instance == null)
            instance = new Turn();

        return instance;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean getTurn() {
        return this.turn;
    }
}
