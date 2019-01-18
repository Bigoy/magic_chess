package com.tssss.bysj.game;

import android.graphics.Canvas;

public class Chessman {
    // 棋子所属的阵营。
    public static int CAMP_SELF = 0;
    public static int CAMP_ARMY = 1;
    private int mCamp;
    private String mPosition;
    private boolean isChecked;

    public void setCamp(int mCamp) {
        this.mCamp = mCamp;
    }

    public int getCamp() {
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
       /* AnchorManager am = AnchorManager.getAnchorManager();
        GameUtil gameUtil = GameUtil.getGameUtil();
        GameRoleManager pm = GameRoleManager.getGameRoleManager();

        Bitmap src = null;
        if (mCamp == CAMP_SELF) {
            if (pm.getRole(GameRoleManager.SELF).getSexResId() == (GameRole.SEX_BOY)) {
                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_boy);
            } else if (pm.getRole(GameRoleManager.SELF).getSexResId() == (GameRole.SEX_GIRL)) {
                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_girl);
            }
        } else if (mCamp == CAMP_ARMY) {
            if (pm.getRole(GameRoleManager.OTHER).getSexResId() == (GameRole.SEX_BOY)) {
                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_boy_02);
            } else if (pm.getRole(GameRoleManager.OTHER).getSexResId() == (GameRole.SEX_GIRL)) {

                src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_girl_02);
            }
        }

        if (src != null) {
            Bitmap bitmap = Bitmap.createBitmap(src);
            int w = bitmap.getWidth() / 2;
            int h = bitmap.getHeight() / 2;
            gameCanvas.drawBitmap(bitmap, am.getAnchor(mPosition).getX() - w,
                    am.getAnchor(mPosition).getY() - h, null);
        }
*/
    }
}
