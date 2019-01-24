package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Mark selected chessman.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Mark {
    /**
     * @param piece selected piece
     */
    public void mark(Canvas gameCanvas, Piece piece) {
        Paint markPaint = new Paint();
        markPaint.setStyle(Paint.Style.STROKE);
        markPaint.setStrokeWidth(10.0f);
        markPaint.setAntiAlias(true);
        markPaint.setDither(true);
        markPaint.setColor(Color.RED);

        gameCanvas.drawCircle(
                piece.getAnchor().getX(),
                piece.getAnchor().getY(),
                65,
                markPaint);
    }
}
