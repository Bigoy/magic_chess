package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.tssss.bysj.user.role.GameRoleManager;

/**
 * Game progress including game preparation, start and end of the three main stages.
 *
 * <P>In the game preparation, progress perform some methods, such as Checking network state,
 * showing battle players information, drawing game scene (chessboard and pieces), initialize
 * timer and so on.</P>
 *
 * <p>At the beginning of game, it transfer game data in real time to server via internet.
 * And Continuously monitor the development of the game through the umpire{@link Umpire},
 * if there is a result,make a corresponding judgment and settlement and call the game end method
 * {@code endGame()}</p>
 *
 * <p>At the end of game, some resource cleanup and recycle operations will be performed.</p>
 */
public class GameProgress {
    private static GameProgress gameProgress;

    private Canvas mGameCanvas;
    private AnchorManager mAnchorManager;
    private PieceManager mPieceManager;
    private GameRoleManager mGameRoleManager;
    private Chessboard mChessboard;
    private Umpire mUmpire;
    private Mark mMark;
    private Rule mRule;
    private GameHelper mGameHelper;


    private GameProgress() {
        mAnchorManager = AnchorManager.getAnchorManager();
        mPieceManager = PieceManager.getChessmanManager();
        mGameRoleManager = GameRoleManager.getGameRoleManager();
        mGameHelper = GameHelper.getGameHelper();
        mChessboard = new SimpleChessboard();
        mUmpire = new Umpire();
        mMark = new Mark();
        mRule = new Rule();
    }

    public static GameProgress getGameProgress() {
        if (gameProgress == null)
            gameProgress = new GameProgress();

        return gameProgress;
    }

    /**
     * Get game canvas.
     */
    public void setGameCanvas(Canvas gameCanvas) {
        mGameCanvas = gameCanvas;
    }

    /**
     * Game preparation.
     */
    public void prepare() {
        // Create anchors.
        mAnchorManager.createAnchors(mGameHelper.getSurfaceSize());
        // Initialize position of pieces.
        mPieceManager.initPiecesPosition();

        if (!GameSurfaceView.canTouch)
            GameSurfaceView.canTouch = true;

        connectServer();
    }

    /**
     * Connect server.
     */
    private void connectServer() {
    }

    /**
     * Start game.
     */
    public void start() {
        runSceneDrawing();

        if (mPieceManager.whoChecked() != null)
            mMark.mark(mGameCanvas, mPieceManager.whoChecked());

        else
            Log.wtf(getClass().getSimpleName(), "no piece selected");

        mUmpire.umpire(mPieceManager, mGameRoleManager, mAnchorManager, this);
    }

    /**
     * Running game scene drawing program.
     */
    private void runSceneDrawing() {
        mChessboard.drawChessboard(mGameCanvas);
        mPieceManager.drawPieces(mGameCanvas);
    }

    /**
     * Take over touch event handling.
     */
    public void doTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (mPieceManager.hasChessmanChecked()) {
                    // User has selected a piece and ready to move.
                    Log.wtf(getClass().getSimpleName(), "ready to move a piece");
                    if (mRule.canMove(x, y)) {
                        mPieceManager.update(mAnchorManager.identifyAnchor(x, y));
                        mPieceManager.resetChessmenCheckedState();
//                        mUmpire.lockPlayPermission();
                    }

                } else {
                    // User ready to select a piece.
                    if (mRule.canSelect(x, y)) {
                        Log.wtf(getClass().getSimpleName(), "ready to select a piece");
                        mPieceManager.checkChessman(x, y);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * End game.
     */
    public void endGame() {
        stopMainDrawing();
        clear();
    }

    /**
     * Stop main drawing program.
     */
    private void stopMainDrawing() {
        if (GameSurfaceView.isDrawing)
            GameSurfaceView.isDrawing = false;

        if (GameSurfaceView.canTouch)
            GameSurfaceView.canTouch = false;
    }

    /**
     * Cleanup resource.
     */
    private void clear() {
    }
}
