package com.tssss.bysj.game;

import android.graphics.Canvas;

/**
 * Piece
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Piece {
    /**
     * Camp of piece.
     */
    public static String PIECE_CAMP_SELF = "PIECE_CAMP_SELF";
    public static String PIECE_CAMP_RIVAL = "PIECE_CAMP_RIVAL";

    /**
     * Camp of piece.
     */
    private String mCamp;

    /**
     * Position of piece.
     */
    private Anchor mAnchor;

    /**
     * Check state.
     */
    private boolean mChecked;


    public Piece(String camp) {
        this.mCamp = camp;
    }

    public void setAnchor(Anchor anchor) {
        mAnchor = anchor;
    }

    public Anchor getAnchor() {
        return mAnchor;
    }

    public String getCamp() {
        return this.mCamp;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public boolean getCheckedState() {
        return mChecked;
    }

    /**
     * Draw piece.
     */
    public void draw(Canvas gameCanvas) {
    }
}
