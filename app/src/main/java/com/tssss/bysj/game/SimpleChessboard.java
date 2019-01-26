package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.graphics.Path;

/**
 * Simple chessboard with layout style of 3x3.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class SimpleChessboard extends Chessboard {

    public SimpleChessboard() {
        super();
    }

    @Override
    public void drawChessboard(Canvas gameCanvas) {
        Path path = new Path();

        AnchorManager am = AnchorManager.getAnchorManager();

        int radius = GameHelper.getGameHelper().getSurfaceSize() / 16;

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

        if (gameCanvas != null) {
            gameCanvas.drawPath(path, getPaint());

            gameCanvas.drawCircle(am.getAnchor(AnchorManager.SEVEN).getX(),
                    am.getAnchor(AnchorManager.SEVEN).getY(), radius, getPaint());
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.EIGHT).getX(),
                    am.getAnchor(AnchorManager.EIGHT).getY(), radius, getPaint());
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.NINE).getX(),
                    am.getAnchor(AnchorManager.NINE).getY(), radius, getPaint());
        }
    }
}
