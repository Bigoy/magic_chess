package com.tssss.bysj.game.core.other;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Chessman {
    // 棋子所属的阵营。
    public static String CAMP_LEFT = "CAMP_LEFT";
    public static String CAMP_RIGHT = "CAMP_RIGHT";
    private String mCamp;
    private String mPosition;
    private boolean isChecked;
    private Paint paint;
    private AnchorManager anchorManager;

    public Chessman() {
        anchorManager = AnchorManager.getAnchorManager();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setCamp(String mCamp) {
        this.mCamp = mCamp;
        if (mCamp.equals(CAMP_LEFT)) {
            paint.setColor(Color.BLUE);

            /*if (pm.getPlayer(GameRoleManager.SELF).getSex().equals(Constant.ROLE_SEX_MAN)) {
//                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_boy);
            } else if (pm.getPlayer(GameRoleManager.SELF).getSex().equals(Constant.ROLE_SEX_WOMAN)) {
//                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_girl);
            }*/
        } else if (mCamp.equals(CAMP_RIGHT)) {
            paint.setColor(Color.YELLOW);
           /* if (pm.getPlayer(GameRoleManager.ARMY).getSex().equals(Constant.ROLE_SEX_MAN)) {
//                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_boy);
            } else if (pm.getPlayer(GameRoleManager.ARMY).getSex().equals(Constant.ROLE_SEX_WOMAN)) {
//                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_girl);
            }*/
        }
    }

    public String getCamp() {
        return this.mCamp;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public void setPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public String getPosition() {
        return this.mPosition;
    }

    public void draw(Canvas gameCanvas) {
        Anchor anchor = anchorManager.getAnchor(mPosition);
        gameCanvas.drawCircle(anchor.getX(), anchor.getY(), 60, paint);
       /*GameUtil gameUtil = GameUtil.getGameUtil();
        GameRoleManager pm = GameRoleManager.getGameRoleManager();
        Bitmap src = null;
        if (src != null) {
            Bitmap bitmap = Bitmap.createBitmap(src);
            int w = bitmap.getWidth() / 2;
            int h = bitmap.getHeight() / 2;
            gameCanvas.drawBitmap(bitmap, am.getAnchor(mPosition).getX() - w,
                    am.getAnchor(mPosition).getY() - h, null);
        }*/
    }
}
