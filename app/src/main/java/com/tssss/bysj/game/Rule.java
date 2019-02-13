package com.tssss.bysj.game;

import com.tssss.bysj.R;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;
import com.tssss.bysj.util.ToastUtil;


public class Rule {
    private static String MOVE_NOT_BETWEEN_CIRCLES = "两个圆圈之间不能直接走棋。",
            MOVE_INCORRECT_DIRECTION = "请横向或竖向移动棋子，并且一次只能移动一格。",
            MOVE_POSITION_USED = "当前位置已经存在棋子，不能落子。",
            ANCHOR_NOT_IN_RANGE = "请点在棋盘网格的相交处。";


    boolean canSelect(int x, int y) {
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
    boolean canMove(int x, int y) {
        AnchorManager am = AnchorManager.getAnchorManager();
        PieceManager pm = PieceManager.getChessmanManager();
        GameHelper gameHelper = GameHelper.getGameHelper();

        if (am.inRange(x, y)) {
            if (!am.identifyAnchor(x, y).isUsed()) {
                int setX = Math.abs(pm.whoChecked().getAnchor().getX() - am.identifyAnchor(x, y).getX());
                int setY = Math.abs(pm.whoChecked().getAnchor().getY() - am.identifyAnchor(x, y).getY());

                if (Math.sqrt(setX * setX + setY * setY) == gameHelper.getSurfaceSize() / 4) {
                    if (canMoveToCircle(pm.whoChecked().getAnchor(),
                            am.identifyAnchor(x, y))) {
                        return true;

                    } else {
                        pm.resetChessmenCheckedState();
                        ToastUtil.showToast(gameHelper.getContext(), MOVE_NOT_BETWEEN_CIRCLES,
                                ToastUtil.TOAST_ERROR);
                    }

                } else {
                    pm.resetChessmenCheckedState();
                    ToastUtil.showToast(gameHelper.getContext(), MOVE_INCORRECT_DIRECTION, ToastUtil.TOAST_ERROR);
                }

            } else {
                pm.cancelSelection();
//                ToastUtil.showToast(gameHelper.getContext(), MOVE_POSITION_USED, ToastUtil.TOAST_ERROR);
            }

        } else {
            pm.resetChessmenCheckedState();
            ToastUtil.showToast(gameHelper.getContext(), ANCHOR_NOT_IN_RANGE, ToastUtil.TOAST_ERROR);
        }

        return false;
    }

    /**
     * Player can not go straight between the three circles at the bottom of chessboard.
     */
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
     * Player wins when all pieces of the opponent forced to open_enter three circles  at
     * the bottom of chessboard.
     *
     * @return winner
     */
    GameResult result() {
        AnchorManager anchorManager = AnchorManager.getAnchorManager();
        PieceManager pieceManager = PieceManager.getChessmanManager();
        GameRoleManager roleManager = GameRoleManager.getGameRoleManager();
        GameResult gameResult = null;

        int y1 = anchorManager.getAnchor(AnchorManager.FOUR).getY();
        int y2 = anchorManager.getAnchor(AnchorManager.SEVEN).getY();

        if (pieceManager.getPiece(PieceManager.SELF_A).getAnchor().getY() == y1
                && pieceManager.getPiece(PieceManager.SELF_B).getAnchor().getY() == y1
                && pieceManager.getPiece(PieceManager.SELF_C).getAnchor().getY() == y1) {

            if (pieceManager.getPiece(PieceManager.RIVAL_A).getAnchor().getY() == y2
                    && pieceManager.getPiece(PieceManager.RIVAL_B).getAnchor().getY() == y2
                    && pieceManager.getPiece(PieceManager.RIVAL_C).getAnchor().getY() == y2) {

                gameResult = new GameResult();
                gameResult.setWinnerName(roleManager.getRole(GameRoleManager.SELF).getRoleName());
                gameResult.setResultDescription(GameHelper.getGameHelper().getContext()
                        .getString(R.string.game_result_win));
                gameResult.setExpSettlement(String.valueOf(reward()));
            }


        } else if (pieceManager.getPiece(PieceManager.SELF_A).getAnchor().getY() == y2
                && pieceManager.getPiece(PieceManager.SELF_B).getAnchor().getY() == y2
                && pieceManager.getPiece(PieceManager.SELF_C).getAnchor().getY() == y2)

            if (pieceManager.getPiece(PieceManager.RIVAL_A).getAnchor().getY() == y1
                    && pieceManager.getPiece(PieceManager.RIVAL_B).getAnchor().getY() == y1
                    && pieceManager.getPiece(PieceManager.RIVAL_C).getAnchor().getY() == y1) {

                gameResult = new GameResult();
                gameResult.setWinnerName(roleManager.getRole(GameRoleManager.RIVAL).getRoleName());
                gameResult.setResultDescription(GameHelper.getGameHelper().getContext()
                        .getString(R.string.game_result_lose));
                gameResult.setExpSettlement(String.valueOf(punish()));
            }

        return gameResult;
    }

    /**
     * The experience of winner plus 50.
     */
    private int reward() {
        return 50;
    }

    /**
     * The experience of loser minus 30.
     */
    private int punish() {
        return -30;
    }

    /**
     * Range of experience value.
     *
     * <P>ROOKIE 250</P>
     * <P>POSITIVE 750</P>
     * <P>JUNIOR 1250</P>
     * <P>INTERMEDIATE 1750</P>
     * <P>SENIOR 2250</P>
     * <P>MASTER 2750</P>
     * <P>GURU ∞</P>
     */
    public int getExperienceRange(GameRole gameRole) {

        if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_ROOKIE))
            return 250;

        else if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_POSITIVE))
            return 750;

        else if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_JUNIOR))
            return 1250;

        else if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_INTERMEDIATE))
            return 1750;

        else if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_SENIOR))
            return 2250;

        else if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_MASTER))
            return 2750;

        else if (gameRole.getRoleLevel().equals(GameRole.ROLE_LEVEL_GURU))
            return 1000000000;

        return 0;
    }
}
