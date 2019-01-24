package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Simple chessboard with layout style of 3x3.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class SimpleChessboard extends Chessboard {
    @Override
    public void drawChessboard(Canvas gameCanvas) {
        AnchorManager am = AnchorManager.getAnchorManager();

        int radius = GameHelper.getGameHelper().getSurfaceSize() / 16;

        Path path = new Path();
        path.moveTo(am.getAnchor(AnchorManager.SEVEN).getX(),
                am.getAnchor(AnchorManager.SEVEN).getY() - radius);
        path.lineTo(am.getAnchor(AnchorManager.ONE).getX(),
                am.getAnchor(AnchorManager.ONE).getY());
        path.lineTo(am.getAnchor(AnchorManager.THREE).getX(),
                am.getAnchor(AnchorManager.THREE).getY());
        path.lineTo(am.getAnchor(AnchorManager.NINE).getX(),
                am.getAnchor(AnchorManager.NINE).getY() - radius);

        path.moveTo(am.getAnchor(AnchorManager.TWO).getX(),
                am.getAnchor(AnchorManager.TWO).getY());
        path.lineTo(am.getAnchor(AnchorManager.EIGHT).getX(),
                am.getAnchor(AnchorManager.EIGHT).getY() - radius);

        path.moveTo(am.getAnchor(AnchorManager.FOUR).getX(),
                am.getAnchor(AnchorManager.FOUR).getY());
        path.lineTo(am.getAnchor(AnchorManager.SIX).getX(),
                am.getAnchor(AnchorManager.SIX).getY());

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);

        if (gameCanvas != null) {
            gameCanvas.drawPath(path, paint);

            gameCanvas.drawCircle(am.getAnchor(AnchorManager.SEVEN).getX(),
                    am.getAnchor(AnchorManager.SEVEN).getY(), radius, paint);
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.EIGHT).getX(),
                    am.getAnchor(AnchorManager.EIGHT).getY(), radius, paint);
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.NINE).getX(),
                    am.getAnchor(AnchorManager.NINE).getY(), radius, paint);
        }
    }
}
