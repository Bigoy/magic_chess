package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;

/**
 * Responsible for monitoring the state of the pieces on the chessboard, and judging the
 * result of the game if the game end condition is met.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Umpire {
    private Turn mTurn;
    private Rule mRule;
    private GameRole mWinner;
    private GameRole mLoser;
    private Mark mMark;

    private boolean haveResult;


    public Umpire() {
        mTurn = Turn.getInstance();
        mRule = new Rule();
        mMark = new Mark();
    }

    /**
     * Judging game result.
     */
    public void umpire(GameProgress gameProgress) {
        if (haveResult)
            return;

        GameRoleManager roleManager = GameRoleManager.getGameRoleManager();
        GameRole gameRole = mRule.result();

        if (gameRole == null)
            noResult();

        else if (gameRole.equals(roleManager.getRole(GameRoleManager.SELF))) {
            mWinner = roleManager.getRole(GameRoleManager.SELF);
            mLoser = roleManager.getRole(GameRoleManager.RIVAL);

            haveResult = true;
            gameProgress.pauseGame();

        } else if (gameRole.equals(roleManager.getRole(GameRoleManager.RIVAL))) {
            mWinner = roleManager.getRole(GameRoleManager.RIVAL);
            mLoser = roleManager.getRole(GameRoleManager.SELF);

            haveResult = true;
            gameProgress.pauseGame();
        }

    }

    public void setHaveResult(boolean haveResult) {
        this.haveResult = haveResult;
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
     * Settlement result.
     */
    public void settlement() {
        mWinner.setRoleExperience(mRule.reward());
        mLoser.setRoleExperience(mRule.punish());

        // Upload the newest data of winner and loser to data base via net.
        TaoHttpClient client = new TaoHttpClient(
                HttpConstant.BASE_URL,
                mWinner,
                new JsonHttpRequest(),
                new JsonHttpResponse(null)
        );
        client.request();

        TaoHttpClient client02 = new TaoHttpClient(
                HttpConstant.BASE_URL,
                mLoser,
                new JsonHttpRequest(),
                new JsonHttpResponse(null)
        );
        client02.request();

    }

    /**
     * Monitor user behavior.
     */
    public void monitor(PieceManager pm, Canvas gameCanvas) {
        if (pm.whoChecked() != null)
            mMark.mark(gameCanvas, pm.whoChecked());
    }

    /**
     * Handle user's actions according to specified rule.
     */
    public void handleUserActions(MotionEvent event, PieceManager pieceManager, AnchorManager anchorManager) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (pieceManager.hasChessmanChecked()) {
                    // User has selected a piece and ready to move.
                    if (mRule.canMove(x, y)) {
                        pieceManager.update(anchorManager.identifyAnchor(x, y));
                        pieceManager.resetChessmenCheckedState();
//                        mUmpire.lockPlayPermission();
                    }

                } else {
                    // User ready to select a piece.
                    if (mRule.canSelect(x, y)) {
                        pieceManager.checkChessman(x, y);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void noResult() {
    }
}
