package com.tssss.bysj.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tssss.bysj.game.core.other.Anchor;
import com.tssss.bysj.game.core.other.AnchorManager;
import com.tssss.bysj.game.core.other.GameRoleManager;
import com.tssss.bysj.game.core.other.GameUtil;

public class Chessman {
    // 棋子所属的阵营。
    public static String CAMP_LEFT = "CAMP_LEFT";
    public static String CAMP_RIGHT = "CAMP_RIGHT";
    private String mCamp;
    private String mPosition;
    private boolean isChecked;

    public void setCamp(String mCamp) {
        this.mCamp = mCamp;
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
        AnchorManager am = AnchorManager.getAnchorManager();
        GameUtil gameUtil = GameUtil.getGameUtil();
        GameRoleManager pm = GameRoleManager.getGameRoleManager();


        Bitmap src = null;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        Anchor anchor = am.getAnchor(mPosition);
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
        gameCanvas.drawCircle(anchor.getX(), anchor.getY(), 60, paint);


       /* if (src != null) {
            Bitmap bitmap = Bitmap.createBitmap(src);
            int w = bitmap.getWidth() / 2;
            int h = bitmap.getHeight() / 2;
            gameCanvas.drawBitmap(bitmap, am.getAnchor(mPosition).getX() - w,
                    am.getAnchor(mPosition).getY() - h, null);
        }*/
    }
}
