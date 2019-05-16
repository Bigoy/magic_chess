package com.tssss.bysj.game.core.other;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tssss.bysj.game.Chessman;
import com.tssss.bysj.other.Logger;

import java.util.HashMap;
import java.util.Map;

/*
 * 棋子管理者。
 * 管理棋子的位置，身份。
 */
public class ChessmanManager {
    private static ChessmanManager mChessmanManager;
    private Paint markPaint;
    private AnchorManager anchorManager;
    public static String SELF_A = "SELF_A";
    public static String SELF_B = "SELF_B";
    public static String SELF_C = "SELF_C";
    public static String ARMY_A = "ARMY_A";
    public static String ARMY_B = "ARMY_B";
    public static String ARMY_C = "ARMY_C";
    public static String UNKNOWN = "UNKNOWN";
    private Chessman selfA;
    private Chessman selfB;
    private Chessman selfC;
    private Chessman armyA;
    private Chessman armyB;
    private Chessman armyC;
    private Map<String, Chessman> chessmen;


    private ChessmanManager() {
        chessmen = new HashMap<>();
        anchorManager = AnchorManager.getAnchorManager();
        initChessmen();
        initChessmanCamp();
        initChessmanPosition();
        initMark();
    }

    private void initMark() {
        markPaint = new Paint();
        markPaint.setAntiAlias(true);
        markPaint.setDither(true);
        markPaint.setColor(Color.BLACK);
        markPaint.setStyle(Paint.Style.STROKE);
        markPaint.setStrokeWidth(4);
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
        chessmen.put(SELF_A, selfA);
        chessmen.put(SELF_B, selfB);
        chessmen.put(SELF_C, selfC);
        chessmen.put(ARMY_A, armyA);
        chessmen.put(ARMY_B, armyB);
        chessmen.put(ARMY_C, armyC);
    }

    private void initChessmanPosition() {
        selfA.setPosition(AnchorManager.ONE);
        selfB.setPosition(AnchorManager.FOUR);
        selfC.setPosition(AnchorManager.SEVEN);
        armyA.setPosition(AnchorManager.THREE);
        armyB.setPosition(AnchorManager.SIX);
        armyC.setPosition(AnchorManager.NINE);
    }

    private void initChessmanCamp() {
        selfA.setCamp(Chessman.CAMP_LEFT);
        selfB.setCamp(Chessman.CAMP_LEFT);
        selfC.setCamp(Chessman.CAMP_LEFT);
        armyA.setCamp(Chessman.CAMP_RIGHT);
        armyB.setCamp(Chessman.CAMP_RIGHT);
        armyC.setCamp(Chessman.CAMP_RIGHT);
    }

    public Chessman getChessman(String key) {
        return chessmen.get(key);
    }

    /*
    Drawing chessmen uniformly.
     */
    public void drawChessmen(Canvas gameCanvas) {
        selfA.draw(gameCanvas);
        selfB.draw(gameCanvas);
        selfC.draw(gameCanvas);
        armyA.draw(gameCanvas);
        armyB.draw(gameCanvas);
        armyC.draw(gameCanvas);
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
        return UNKNOWN;
    }

    /*
    Update army's chessmen newPosition, which are from the server.
     */
    public void moveChessman(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        Rule rule = Rule.getRule();
        if (rule.canMoveChessman(x, y)) {
            Chessman checkedChessman = chessmen.get(whoChecked());
            updateChessmanPosition(checkedChessman, am.identifyAnchor(x, y));
        }
    }

    public void syncChessmen(String key, String newPosition) {
        Chessman checkedChessman = chessmen.get(key);
        updateChessmanPosition(checkedChessman, newPosition);
    }

    private void updateChessmanPosition(Chessman chessman, String newPosition) {
        AnchorManager am = AnchorManager.getAnchorManager();
        if (null == chessman) {
            throw new NullPointerException();
        }
        String oldPosition = chessman.getPosition();
        Anchor oldAnchor = am.getAnchor(oldPosition);
        oldAnchor.setUsed(false);
        chessman.setPosition(newPosition);
        Anchor newAnchor = am.getAnchor(newPosition);
        newAnchor.setUsed(true);
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
        } else return armyC.getChecked();
    }

    /*
    Return the key of checked chessman.
     */
    public String whoChecked() {
        if (selfA.getChecked()) {
            Logger.log("选中的棋子：" + SELF_A);
            return SELF_A;
        } else if (selfB.getChecked()) {
            Logger.log("选中的棋子：" + SELF_B);
            return SELF_B;
        } else if (selfC.getChecked()) {
            Logger.log("选中的棋子：" + SELF_C);
            return SELF_C;
        } else if (armyA.getChecked()) {
            Logger.log("选中的棋子：" + ARMY_A);
            return ARMY_A;
        } else if (armyB.getChecked()) {
            Logger.log("选中的棋子：" + ARMY_B);
            return ARMY_B;
        } else if (armyC.getChecked()) {
            Logger.log("选中的棋子：" + ARMY_C);
            return ARMY_C;
        }
        return UNKNOWN;
    }

    /*
    选中一个棋子。
     */
    public void playerPrepareToCheckChessman(int touchX, int touchY) {
        AnchorManager am = AnchorManager.getAnchorManager();
        Rule rule = Rule.getRule();
        if (rule.canCheckChessman(touchX, touchY)) {
            Chessman checkedChessman = chessmen.get(identify(am.identifyAnchor(touchX, touchY)));
            if (null == checkedChessman) {
                throw new NullPointerException();
            }
            checkedChessman.setChecked(true);
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
    public void drawMark(Canvas gameCanvas) {
        Chessman checkChessman = chessmen.get(whoChecked());
        if (null != checkChessman) {
            Anchor checkChessmanPosition = anchorManager.getAnchor(checkChessman.getPosition());
            gameCanvas.drawCircle(checkChessmanPosition.getX(),
                    checkChessmanPosition.getY(),
                    80,
                    markPaint);

        }
    }
}

