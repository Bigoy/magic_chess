package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.role.GameRoleManager;

/**
 * Responsible for monitoring the state of the pieces on the chessboard, and judging the
 * result of the game if the game end condition is met.
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Umpire {
    private static Umpire umpire;

    private Turn mTurn;
    private Rule mRule;
    private Mark mMark;
    private Handler mHandler;
    private Message mMsg;
    private GameResult mResult;

    private boolean umpireCompleted;


    private Umpire() {
        mTurn = Turn.getInstance();
        mRule = new Rule();
        mMark = new Mark();
        mMsg = Message.obtain();
    }

    public static synchronized Umpire getUmpire() {
        if (umpire == null)
            umpire = new Umpire();

        return umpire;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    /**
     * Judging game result.
     */
    void umpire(GameProgress gameProgress, PieceManager pm, Canvas canvas) {
        if (!umpireCompleted) {
            monitor(pm, canvas);

            mResult = mRule.result();

            if (mResult == null)
                noResult();

            if (mResult != null) {
                mMsg.obj = mResult;
                gameProgress.pauseGame();

                Log.wtf(getClass().getSimpleName(), mResult.getResultDescription());
                Log.wtf(getClass().getSimpleName(), mResult.getWinnerName());
            }
        }

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
    private void settlement() {
        if (mResult != null)
            try {
                GameRoleManager.getGameRoleManager().getRole(GameRoleManager.SELF)
                        .setRoleExperience(Integer.parseInt(mResult.getExpSettlement()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        // Upload the newest data of user.
        TaoHttpClient client = new TaoHttpClient(
                HttpConstant.BASE_URL,
                GameRoleManager.getGameRoleManager().getRole(GameRoleManager.SELF),
                new JsonHttpRequest(),
                new JsonHttpResponse(null)
        );
        client.request();
    }

    /**
     * Monitor user behavior.
     */
    private void monitor(PieceManager pm, Canvas gameCanvas) {
        if (!umpireCompleted) {
            if (pm.whoChecked() != null)
                mMark.mark(gameCanvas, pm.whoChecked());
        }
    }

    /**
     * Handle user's actions according to specified rule.
     */
    void handleUserActions(MotionEvent event, PieceManager pieceManager, AnchorManager anchorManager) {
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

    void startToUmpire() {
        this.umpireCompleted = false;
    }

    void stopToUmpire() {
        this.umpireCompleted = true;

        settlement();

        if (mHandler != null)
            mHandler.sendMessage(mMsg);
    }
}
