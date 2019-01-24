package com.tssss.bysj.game;

import android.os.Looper;

import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;
import com.tssss.bysj.util.ToastUtil;

/**
 * Responsible for monitoring the state of the pieces on the chessboard, and judging the
 * result of the game if the game end condition is met.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Umpire {
    private GameRole winner;
    private Turn mTurn;
    private Rule mRule;


    public Umpire() {
        mTurn = Turn.getInstance();
        mRule = new Rule();
    }

    /**
     * Judging game result.
     */
    public void umpire(PieceManager pieceManager,
                       GameRoleManager gameRoleManager,
                       AnchorManager anchorManager,
                       GameProgress gameProgress) {

        int y1 = anchorManager.getAnchor(AnchorManager.FOUR).getY();
        int y2 = anchorManager.getAnchor(AnchorManager.SEVEN).getY();

        if (pieceManager.getPiece(PieceManager.SELF_A).getAnchor().getY() == y1
                && pieceManager.getPiece(PieceManager.SELF_B).getAnchor().getY() == y1
                && pieceManager.getPiece(PieceManager.SELF_C).getAnchor().getY() == y1) {

            if (pieceManager.getPiece(PieceManager.RIVAL_A).getAnchor().getY() == y2
                    && pieceManager.getPiece(PieceManager.RIVAL_B).getAnchor().getY() == y2
                    && pieceManager.getPiece(PieceManager.RIVAL_C).getAnchor().getY() == y2) {

//                announce(gameRoleManager.getRole(GameRoleManager.SELF));
//                settle(gameRoleManager.getRole(GameRoleManager.SELF), gameRoleManager.getRole(GameRoleManager.OTHER));
                gameProgress.endGame();
                Looper.prepare();
                ToastUtil.showToast(GameHelper.getGameHelper().getContext(), "You win", ToastUtil.TOAST_DEFAULT);
                Looper.loop();
            }

        } else if (pieceManager.getPiece(PieceManager.SELF_A).getAnchor().getY() == y2
                && pieceManager.getPiece(PieceManager.SELF_B).getAnchor().getY() == y2
                && pieceManager.getPiece(PieceManager.SELF_C).getAnchor().getY() == y2) {

            if (pieceManager.getPiece(PieceManager.RIVAL_A).getAnchor().getY() == y1
                    && pieceManager.getPiece(PieceManager.RIVAL_B).getAnchor().getY() == y1
                    && pieceManager.getPiece(PieceManager.RIVAL_C).getAnchor().getY() == y1) {

//                announce(gameRoleManager.getRole(GameRoleManager.OTHER));
//                settle(gameRoleManager.getRole(GameRoleManager.OTHER), gameRoleManager.getRole(GameRoleManager.SELF));
                gameProgress.endGame();
                Looper.prepare();
                ToastUtil.showToast(GameHelper.getGameHelper().getContext(), "You lose", ToastUtil.TOAST_DEFAULT);
                Looper.loop();
            }
        }
    }

    /**
     * Announce game result.
     */
    private void announce(GameRole winner) {
        this.winner = winner;
    }

    public GameRole getWinner() {
        return winner;
    }

    /**
     * Lock permission of playing.
     */
    public void lockPlayPermission() {
        mTurn.setTurn(false);
    }

    /**
     * Unlock permission of playing.
     */
    public void unlockPlayPermission() {
        mTurn.setTurn(true);
    }

    /**
     * Settlement.
     */
    private void settle(GameRole winner, GameRole loser) {
        winner.setRoleExperience(mRule.reward());
        loser.setRoleExperience(mRule.punish());
    }
}
