package com.tssss.bysj.game.core;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.tssss.bysj.game.Chessman;

import java.util.HashMap;
import java.util.Map;

/*
 * 棋子管理者。
 * 管理棋子的位置，身份。
 */
public class ChessmanManager {
    @SuppressLint("StaticFieldLeak")
    private static ChessmanManager mChessmanManager;

    public static String SELF_A = "selfA";
    public static String SELF_B = "selfB";
    public static String SELF_C = "selfC";
    public static String ARMY_A = "armyA";
    public static String ARMY_B = "armyB";
    public static String ARMY_C = "armyC";

    private Chessman selfA;
    private Chessman selfB;
    private Chessman selfC;
    private Chessman armyA;
    private Chessman armyB;
    private Chessman armyC;

    private Map<String, Chessman> chessmen;

    private ChessmanManager() {
        chessmen = new HashMap<>();

        initChessmen();
    }

    public static ChessmanManager getChessmanManager() {
        if (mChessmanManager == null) {
            mChessmanManager = new ChessmanManager();
        }
        return mChessmanManager;
    }

    /*
     * 棋子相关的初始化工作。
     */
    private void initChessmen() {
        selfA = new Chessman();
        selfB = new Chessman();
        selfC = new Chessman();
        armyA = new Chessman();
        armyB = new Chessman();
        armyC = new Chessman();

        // 保存给棋子管理者。
        chessmen.put(SELF_A, selfA);
        chessmen.put(SELF_B, selfB);
        chessmen.put(SELF_C, selfC);
        chessmen.put(ARMY_A, armyA);
        chessmen.put(ARMY_B, armyB);
        chessmen.put(ARMY_C, armyC);
    }

    public void initChessmanPosition() {
        chessmen.get(SELF_A).setPosition(AnchorManager.ONE);
        chessmen.get(SELF_B).setPosition(AnchorManager.FOUR);
        chessmen.get(SELF_C).setPosition(AnchorManager.SEVEN);

        chessmen.get(ARMY_A).setPosition(AnchorManager.THREE);
        chessmen.get(ARMY_B).setPosition(AnchorManager.SIX);
        chessmen.get(ARMY_C).setPosition(AnchorManager.NINE);
    }

    public void initChessmanCamp() {
        chessmen.get(SELF_A).setCamp(Chessman.CAMP_SELF);
        chessmen.get(SELF_B).setCamp(Chessman.CAMP_SELF);
        chessmen.get(SELF_C).setCamp(Chessman.CAMP_SELF);

        chessmen.get(ARMY_A).setCamp(Chessman.CAMP_ARMY);
        chessmen.get(ARMY_B).setCamp(Chessman.CAMP_ARMY);
        chessmen.get(ARMY_C).setCamp(Chessman.CAMP_ARMY);
    }

    public Chessman getChessman(String key) {
        return chessmen.get(key);
    }

    /*
    Drawing chessmen uniformly.
     */
    public void drawChessmen(Canvas gameCanvas) {
        chessmen.get(SELF_A).draw(gameCanvas);
        chessmen.get(SELF_B).draw(gameCanvas);
        chessmen.get(SELF_C).draw(gameCanvas);
        chessmen.get(ARMY_A).draw(gameCanvas);
        chessmen.get(ARMY_B).draw(gameCanvas);
        chessmen.get(ARMY_C).draw(gameCanvas);
    }

    public String identify(String position) {
        if (selfA.getPosition().equals(position)) {
            return SELF_A;
        } else if (selfB.getPosition().equals(position)) {
            return SELF_B;
        } else if (selfC.getPosition().equals(position)) {
            return SELF_C;
        } else if (armyA.getPosition().equals(position)) {
            return ARMY_A;
        } else if (armyB.getPosition().equals(position)) {
            return ARMY_B;
        } else if (armyC.getPosition().equals(position)) {
            return ARMY_C;
        }
        return "";
    }

    /*
    更新棋子位置。
     */
    public void update(MotionEvent event, String keyChessman, String position) {
        AnchorManager am = AnchorManager.getAnchorManager();
        Rule rule = Rule.getRule();
        if (rule.canMoveChessman(event)) {
            // 原来的锚点使用状态设为false。
            am.getAnchor(chessmen.get(keyChessman).getPosition()).setUsed(false);
            chessmen.get(keyChessman).setPosition(position);
            am.getAnchor(position).setUsed(true);
        }

        // 更新的棋子位置上传到服务器。

    }

    /*
    Update army's chessmen position, which are from the server.
     */
    public void updateArmyPosition(String key, String position) {
        AnchorManager am = AnchorManager.getAnchorManager();
        am.getAnchor(chessmen.get(key).getPosition()).setUsed(false);
        chessmen.get(key).setPosition(position);
        am.getAnchor(position).setUsed(true);
    }

    /*
    Determine if any chessman have been selected.
     */
    public boolean hasChessmanChecked() {
        if (selfA.getChecked()) {
            return true;
        } else if (selfB.getChecked()) {
            return true;
        } else if (selfC.getChecked()) {
            return true;
        } else if (armyA.getChecked()) {
            return true;
        } else if (armyB.getChecked()) {
            return true;
        } else if (armyC.getChecked()) {
            return true;
        }
        return false;
    }

    /*
    Return the key of checked chessman.
     */
    public String whoChecked() {
        if (selfA.getChecked()) {
            return SELF_A;
        } else if (selfB.getChecked()) {
            return SELF_B;
        } else if (selfC.getChecked()) {
            return SELF_C;
        } else if (armyA.getChecked()) {
            return ARMY_A;
        } else if (armyB.getChecked()) {
            return ARMY_B;
        } else if (armyC.getChecked()) {
            return ARMY_C;
        }
        return null;
    }

    /*
    选中一个棋子。
     */
    public void checkChessman(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        AnchorManager am = AnchorManager.getAnchorManager();
        Rule rule = Rule.getRule();

        if (rule.canCheckChessman(event)) {
            chessmen.get(identify(am.identifyAnchor(touchX, touchY))).setChecked(true);
        }
    }

    /*
    重置所有棋子的选中状态为false。
     */
    public void resetChessmenCheckedState() {
        selfA.setChecked(false);
        selfB.setChecked(false);
        selfC.setChecked(false);
        armyA.setChecked(false);
        armyB.setChecked(false);
        armyC.setChecked(false);
    }

    /*
    当棋子选中时在选中的棋子位置绘制稍大的圆圈作为标记。
     */
    public void drawMark(Canvas gameCanvas, String chessmanKey) {
        AnchorManager am = AnchorManager.getAnchorManager();
        GameUtil gameUtil = GameUtil.getGameUtil();

        Paint markPaint = new Paint();
        markPaint.setAntiAlias(true);
        markPaint.setDither(true);
        markPaint.setColor(Color.BLACK);
        markPaint.setStyle(Paint.Style.STROKE);
        markPaint.setStrokeWidth(4);

        gameCanvas.drawCircle(am.getAnchor(getChessman(chessmanKey).getPosition()).getX(),
                am.getAnchor(getChessman(chessmanKey).getPosition()).getY(), 80, markPaint);

       /* Bitmap src = BitmapFactory.decodeResource(gameUtil.getContext().getResources(), R.drawable.ic_flag);
        Bitmap bitmap = Bitmap.createBitmap(src);

        int x = am.getAnchor(chessmen.get(chessmanKey).getPosition()).getX() - (bitmap.getWidth() / 2);
        int y = am.getAnchor(chessmen.get(chessmanKey).getPosition()).getY() - (bitmap.getHeight() / 2);

        gameCanvas.drawBitmap(bitmap, x, y, markPaint);*/
    }
}

