package com.tssss.bysj.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.tssss.bysj.user.role.GameRoleManager;

/**
 * Game progress including game preparation, run and end of the three main stages.
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
    private Chessboard mChessboard;
    private Umpire mUmpire;
    private GameHelper mGameHelper;
    private GameResult mGameResult;
    private GameRoleManager mRoleManager;

    private boolean initialized;


    private GameProgress() {
        mAnchorManager = AnchorManager.getAnchorManager();
        mPieceManager = PieceManager.getChessmanManager();
        mRoleManager = GameRoleManager.getGameRoleManager();
        mGameHelper = GameHelper.getGameHelper();
        mChessboard = new SimpleChessboard();
        mGameResult = new GameResult();
        mUmpire = new Umpire();
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
     * Game initialization.
     */
    public void initialize() {
        Log.wtf(getClass().getSimpleName(), "<------ game initialization ------>");

        mAnchorManager.createAnchors(mGameHelper.getSurfaceSize());
        mPieceManager.initPiecesPosition();
    }

    /**
     * Game preparation.
     */
    public void prepare() {
        Log.wtf(getClass().getSimpleName(), "<------ game preparation ------>");

        if (!GameScene.canTouch)
            GameScene.canTouch = true;

        connectServer();
        prepareMusic();
    }

    /**
     * Play background music to create a game atmosphere.
     */
    private void prepareMusic() {
        Log.wtf(getClass().getSimpleName(), "playing music");
    }

    /**
     * Connect server.
     */
    private void connectServer() {
        Log.wtf(getClass().getSimpleName(), "connecting server...");
        Log.wtf(getClass().getSimpleName(), "connected successfully");
    }

    /**
     * Start game.
     */
    public void run() {
//        Log.wtf(getClass().getSimpleName(), "<------ game running ------>");

        runSceneDrawing();

        mUmpire.monitor(mPieceManager, mGameCanvas);
        mUmpire.umpire(this);
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
        mUmpire.handleUserActions(event, mPieceManager, mAnchorManager);
    }

    /**
     * Pause game.
     */
    public void pauseGame() {
        if (GameScene.canTouch)
            GameScene.canTouch = false;

        Effect effect = new Effect();
        effect.explosion(mGameCanvas);

        mUmpire.settlement();

        mGameResult.showGameResult(this);
    }

    /**
     * Resume game.
     */
    public void resumeGame() {
        if (!GameScene.canTouch)
            GameScene.canTouch = true;

        initialized = false;
        mUmpire.setHaveResult(false);

        Log.wtf(getClass().getSimpleName(), mPieceManager.getPiece(PieceManager.SELF_A).getAnchor().getName());
        Log.wtf(getClass().getSimpleName(), mPieceManager.getPiece(PieceManager.SELF_B).getAnchor().getName());
        Log.wtf(getClass().getSimpleName(), mPieceManager.getPiece(PieceManager.SELF_C).getAnchor().getName());
        Log.wtf(getClass().getSimpleName(), mPieceManager.getPiece(PieceManager.RIVAL_A).getAnchor().getName());
        Log.wtf(getClass().getSimpleName(), mPieceManager.getPiece(PieceManager.RIVAL_B).getAnchor().getName());
        Log.wtf(getClass().getSimpleName(), mPieceManager.getPiece(PieceManager.RIVAL_C).getAnchor().getName());
    }

    /**
     * End game.
     */
    public void endGame() {
        mRoleManager.removeRole(GameRoleManager.RIVAL);
        clear();
    }

    /**
     * Cleanup resource.
     */
    private void clear() {
        Log.wtf(getClass().getSimpleName(), "clear up");
    }

}
