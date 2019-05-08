package com.tssss.bysj.game.core.other;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;

import com.tssss.bysj.other.Logger;
import com.tssss.bysj.util.ToastUtil;

public class Rule {
    @SuppressLint("StaticFieldLeak")
    private static Rule rule;

    private static String MOVE_NOT_BETWEEN_CIRCLES = "两个圆圈之间不能直接走棋。",
            MOVE_INCORRECT_DIRECTION = "请横向或竖向移动棋子，并且一次只能移动一格。",
            MOVE_POSITION_USED = "当前位置已经存在棋子，不能落子。",
            ANCHOR_NOT_IN_RANGE = "请点在棋盘网格的相交处。";

    // true表示轮到当前用户走棋。
    private boolean turn = true;

    private Rule() {
    }

    public static Rule getRule() {
        return rule == null ? new Rule() : rule;
    }

    /*
    棋子选中的规则：
    a 点击在棋盘格子上；
    b 点击的地方必须有棋子存在；
     */
    public boolean canCheckChessman(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        ChessmanManager cm = ChessmanManager.getChessmanManager();
        GameRoleManager gm = GameRoleManager.getGameRoleManager();
        if (am.inRange(x, y)) {
            if (am.getAnchor(am.identifyAnchor(x, y)).isUsed()) {
                if (cm.getChessman(cm.identify(am.identifyAnchor(x, y))).getCamp().equals(gm.getPlayer(GameRoleManager.SELF).getChessmanCamp())) {
                    Logger.log("你的棋子");
                    return true;
                } else {
                    Logger.log("该棋子属于对方");
                    cm.resetChessmenCheckedState();

                }
            } else {
                cm.resetChessmenCheckedState();
                Log.wtf(getClass().getSimpleName(), Constant.CHECK_NO_CHESSMAN);
            }
        } else {
            cm.resetChessmenCheckedState();
            Log.wtf(getClass().getSimpleName(), ANCHOR_NOT_IN_RANGE);
        }
        return false;
    }

    /*
    棋子移动条件：
    a 新位置不能有棋子；
    b 每次只能移动一格；
    c 只能移动直线；
    d 新位置是一个锚点；
    e 两个圆圈之间不能直接走棋。
     */
    public boolean canMoveChessman(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        ChessmanManager cm = ChessmanManager.getChessmanManager();
        GameUtil gameUtil = GameUtil.getGameUtil();
        String checkedPos = (cm.getChessman(cm.whoChecked()).getPosition());
        int oldX = am.getAnchor(checkedPos).getX();
        int oldY = am.getAnchor(checkedPos).getY();
        if (am.inRange(x, y)) {
            if (!am.getAnchor(am.identifyAnchor(x, y)).isUsed()) {
                String oldPos = am.identifyAnchor(oldX, oldY);
                String newPos = am.identifyAnchor(x, y);

                int setX = Math.abs(am.getAnchor(newPos).getX() - am.getAnchor(oldPos).getX());
                int setY = Math.abs(am.getAnchor(newPos).getY() - am.getAnchor(oldPos).getY());

                if (Math.sqrt(setX * setX + setY * setY) == gameUtil.getSurfaceSize() / 4) {
                    if (canMoveToCircle(oldPos, newPos)) {
                        return true;
                    } else {
                        cm.resetChessmenCheckedState();
                        Log.wtf(getClass().getSimpleName(), MOVE_NOT_BETWEEN_CIRCLES);
                        ToastUtil.showToast(gameUtil.getContext(), MOVE_NOT_BETWEEN_CIRCLES, ToastUtil.TOAST_ERROR);
                    }
                } else {
                    cm.resetChessmenCheckedState();
                    ToastUtil.showToast(gameUtil.getContext(), MOVE_INCORRECT_DIRECTION, ToastUtil.TOAST_ERROR);
                }
            } else {
                cm.resetChessmenCheckedState();
                ToastUtil.showToast(gameUtil.getContext(), MOVE_POSITION_USED, ToastUtil.TOAST_ERROR);
            }
        } else {
            cm.resetChessmenCheckedState();
            ToastUtil.showToast(gameUtil.getContext(), ANCHOR_NOT_IN_RANGE, ToastUtil.TOAST_ERROR);
        }
        return false;
    }

    private boolean canMoveToCircle(String oldPos, String newPos) {
        if (oldPos.equals(AnchorManager.SEVEN)) {
            if (newPos.equals(AnchorManager.EIGHT)) {
                return false;
            }
        } else if (oldPos.equals(AnchorManager.EIGHT)) {
            if (newPos.equals(AnchorManager.SEVEN) || newPos.equals(AnchorManager.NINE)) {
                return false;
            }
        } else if (oldPos.equals(AnchorManager.NINE)) {
            if (newPos.equals(AnchorManager.EIGHT)) {
                return false;
            }
        }
        return true;
    }
}
