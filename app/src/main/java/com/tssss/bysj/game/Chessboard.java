package com.tssss.bysj.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.tssss.bysj.R;
import com.tssss.bysj.util.GameUtil;

/*
 * 棋盘类。
 */
public class Chessboard {
    /*
     * 绘制棋盘形状。
     *
     * @param gameCanvas gameView传入的canvas对象。
     */
    public void draw(Canvas gameCanvas) {
        /*AnchorManager am = AnchorManager.getAnchorManager();
        GameUtil gameUtil = GameUtil.getGameUtil();

        // 绘制棋盘图片背景。
        Bitmap src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.bg_board);
        Bitmap bitmap = Bitmap.createBitmap(src);
        int x = am.getAnchor(AnchorManager.FIVE).getX() - bitmap.getWidth() / 2;
        int y = am.getAnchor(AnchorManager.FIVE).getY() - bitmap.getHeight() / 2;

        int radius = gameUtil.getSurfaceSize() / 16;

        // 根据锚点坐标生成形状path。
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
        // canvas画出path。
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);

        if (gameCanvas != null) {
            gameCanvas.drawBitmap(bitmap, x, y, null);
            // 绘制棋盘的线条。
            gameCanvas.drawPath(path, paint);
            // 绘制棋盘下方的三个圆圈。
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.SEVEN).getX(),
                    am.getAnchor(AnchorManager.SEVEN).getY(), radius, paint);
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.EIGHT).getX(),
                    am.getAnchor(AnchorManager.EIGHT).getY(), radius, paint);
            gameCanvas.drawCircle(am.getAnchor(AnchorManager.NINE).getX(),
                    am.getAnchor(AnchorManager.NINE).getY(), radius, paint);
        }*/
    }
}
