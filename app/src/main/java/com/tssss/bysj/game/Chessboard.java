package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Chessboard
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Chessboard {
    /**
     * Brush drawing chessboard.
     */
    private Paint mPaint;


    public Chessboard() {
        initPaint();
    }

    protected Paint getPaint() {
        return this.mPaint;
    }

    /**
     * Draw chess board.
     */
    public void drawChessboard(Canvas gameCanvas) {
    }

    /**
     * Initialize paint.
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

}
