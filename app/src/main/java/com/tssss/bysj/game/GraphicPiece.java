package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Graphic piece.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class GraphicPiece extends Piece {
    /**
     * Size of piece.
     */
    private int mSize = 60;

    /**
     * Self color.
     */
    private int mSelfColor = Color.YELLOW;

    /**
     * Rival color
     */
    private int mRivalColor = Color.BLACK;

    public GraphicPiece(String name, String camp) {
        super(name, camp);
    }


    public void setSelfColor(int selfColor) {
        mSelfColor = selfColor;
    }

    public void setRivalColor(int rivalColor) {
        mRivalColor = rivalColor;
    }

    public void setSize(int size) {
        mSize = size;
    }

    @Override
    public void draw(Canvas gameCanvas) {
        Paint piecePaint = new Paint();
        piecePaint.setStyle(Paint.Style.FILL);
        piecePaint.setAntiAlias(true);
        piecePaint.setDither(true);

        if (getCamp().equals(Piece.PIECE_CAMP_SELF))
            piecePaint.setColor(mSelfColor);

        else if (getCamp().equals(Piece.PIECE_CAMP_RIVAL))
            piecePaint.setColor(mRivalColor);

        gameCanvas.drawCircle(getAnchor().getX(), getAnchor().getY(), mSize, piecePaint);
    }
}
