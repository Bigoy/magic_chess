package com.tssss.bysj.game;

import android.util.Log;

import com.tssss.bysj.util.ToastUtil;


public class Rule {
    private static String MOVE_NOT_BETWEEN_CIRCLES = "两个圆圈之间不能直接走棋。",
            MOVE_INCORRECT_DIRECTION = "请横向或竖向移动棋子，并且一次只能移动一格。",
            MOVE_POSITION_USED = "当前位置已经存在棋子，不能落子。",
            ANCHOR_NOT_IN_RANGE = "请点在棋盘网格的相交处。";


    public boolean canSelect(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        PieceManager pm = PieceManager.getChessmanManager();

        if (Turn.getInstance().getTurn()) {
            if (am.inRange(x, y)) {
                if (am.identifyAnchor(x, y).isUsed()) {
                    return true;
                   /* if (pm.identify(am.identifyAnchor(x, y)).getCamp().equals(Piece.PIECE_CAMP_SELF)) {
                        return true;

                    } else {
                        pm.resetChessmenCheckedState();
                        ToastUtil.showToast(GameHelper.getGameHelper().getContext(),
                                Constant.CHECK_NOT_SELF, ToastUtil.TOAST_ERROR);
                    }
*/
                } else {
                    pm.resetChessmenCheckedState();
                    ToastUtil.showToast(GameHelper.getGameHelper().getContext(), Constant.CHECK_NO_CHESSMAN,
                            ToastUtil.TOAST_ERROR);
                }

            } else {
                pm.resetChessmenCheckedState();
                ToastUtil.showToast(GameHelper.getGameHelper().getContext(), ANCHOR_NOT_IN_RANGE,
                        ToastUtil.TOAST_ERROR);
            }

        } else {
            pm.resetChessmenCheckedState();
            ToastUtil.showToast(GameHelper.getGameHelper().getContext(), Constant.CHECK_NOT_TURN,
                    ToastUtil.TOAST_ERROR);
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
    public boolean canMove(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        PieceManager pm = PieceManager.getChessmanManager();
        GameHelper gameHelper = GameHelper.getGameHelper();

        if (am.inRange(x, y)) {
            if (!am.identifyAnchor(x, y).isUsed()) {
                int setX = Math.abs(pm.whoChecked().getAnchor().getX() - am.identifyAnchor(x, y).getX());
                int setY = Math.abs(pm.whoChecked().getAnchor().getY() - am.identifyAnchor(x, y).getY());

                if (Math.sqrt(setX * setX + setY * setY) == gameHelper.getSurfaceSize() / 4) {
                    if (canMoveToCircle(am.identifyAnchor(x, y),
                            am.identifyAnchor(x, y))) {
                        return true;

                    } else {
                        pm.resetChessmenCheckedState();
                        Log.wtf(getClass().getSimpleName(), MOVE_NOT_BETWEEN_CIRCLES);
                        ToastUtil.showToast(gameHelper.getContext(), MOVE_NOT_BETWEEN_CIRCLES,
                                ToastUtil.TOAST_ERROR);
                    }

                } else {
                    pm.resetChessmenCheckedState();
                    ToastUtil.showToast(gameHelper.getContext(), MOVE_INCORRECT_DIRECTION, ToastUtil.TOAST_ERROR);
                }

            } else {
                pm.resetChessmenCheckedState();
                ToastUtil.showToast(gameHelper.getContext(), MOVE_POSITION_USED, ToastUtil.TOAST_ERROR);
            }

        } else {
            pm.resetChessmenCheckedState();
            ToastUtil.showToast(gameHelper.getContext(), ANCHOR_NOT_IN_RANGE, ToastUtil.TOAST_ERROR);
        }

        return false;
    }

    private boolean canMoveToCircle(Anchor old, Anchor current) {
        AnchorManager am = AnchorManager.getAnchorManager();

        if (old.equals(am.getAnchor(AnchorManager.SEVEN)) &&
                current.equals(am.getAnchor(AnchorManager.EIGHT)))
            return false;

        else if (old.equals(am.getAnchor(AnchorManager.EIGHT)) &&
                current.equals(am.getAnchor(AnchorManager.NINE)))
            return false;

        else if (old.equals(am.getAnchor(AnchorManager.EIGHT)) &&
                current.equals(am.getAnchor(AnchorManager.SEVEN)))
            return false;

        else return !old.equals(am.getAnchor(AnchorManager.NINE)) ||
                    !current.equals(am.getAnchor(AnchorManager.EIGHT));

    }

    /**
     * The experience of winner plus 50.
     */
    public int reward() {
        return 50;
    }

    /**
     * The experience of loser minus 30.
     */
    public int punish() {
        return 30;
    }
}
