package com.tssss.bysj.game;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

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

    /**
     * Name.
     */
    private String mName;


    public Piece(String name, String camp) {
        this.mName = name;
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

    public String getName() {
        return this.mName;
    }

    /**
     * Draw piece.
     */
    public void draw(Canvas gameCanvas) {
    }

    @NonNull
    @Override
    public String toString() {
        return "camp = " + this.mCamp
                + "\t"
                + "anchor = " + this.mAnchor.getName()
                + "\t"
                + "checked = " + this.mChecked
                + "\n";
    }
}
