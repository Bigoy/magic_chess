package com.tssss.bysj.game.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tssss.bysj.game.core.GamePresenter;
import com.tssss.bysj.game.core.other.GameUtil;
import com.tssss.bysj.other.Logger;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public boolean isDrawing = false;

    private Canvas gameCanvas;
    private SurfaceHolder gameHolder;
    private boolean canTouch = false;
    private int measuredSurfaceSize;
    private GameUtil gameUtil;
    private GamePresenter gamePresenter;
    private Thread drawThread;

    public void setGamePresenter(GamePresenter gamePresenter) {
        this.gamePresenter = gamePresenter;

    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }

    public GameSurfaceView(Context context) {
        super(context);
        init();
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredSurfaceSize = MeasureSpec.getSize(widthMeasureSpec);
        gameUtil.setSurfaceSize(measuredSurfaceSize);
        setMeasuredDimension(measuredSurfaceSize, measuredSurfaceSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Logger.log("GameSurfaceView created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Logger.log("GameSurfaceView changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
        Logger.log("surfaceView destroyed");
    }

    public void stopGame() {
        isDrawing = false;
        canTouch = false;
    }

    public void touchTrue() {
        canTouch = true;
    }

    public void touchFalse() {
        canTouch = false;
    }

    @Override
    public void run() {
        // 绘制。
        while (isDrawing) {
            drawMainly();
        }
    }

    /**
     * 初始化GameSurfaceView
     */
    private void init() {
        gameHolder = getHolder();
        gameHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);
        gameHolder.setFormat(PixelFormat.TRANSLUCENT);
        gameUtil = GameUtil.getGameUtil();
        gameUtil.setContext(getContext());
    }

    /*
    总绘制方法。
     */
    private void drawMainly() {
        try {
            gameCanvas = gameHolder.lockCanvas();
            clear(gameCanvas);
            gamePresenter.drawGame(gameCanvas);
        } finally {
            if (gameCanvas != null) {
                gameHolder.unlockCanvasAndPost(gameCanvas);
            }
        }
    }

    /**
     * 清空画布
     */
    private void clear(Canvas gameCanvas) {
        if (null != gameCanvas) {
            Paint paint = new Paint();
            Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            paint.setXfermode(xfermode);
            gameCanvas.drawPaint(paint);
        }
    }

    /**
     * 处理用户的触摸事件
     */
    public void doTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (canTouch) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (gamePresenter.hasChessmanChecked()) {
                    if (gamePresenter.canMoveChessman(x, y)){
                        gamePresenter.moveChessman(x, y);
                        canTouch = false;
                    }
                } else {
                    gamePresenter.checkChessman(x, y);
                }
            }
        }
    }

    public void syncChessmen(String chessmanKey, String position) {
        gamePresenter.receivedSyncChessmanMessage(chessmanKey, position);
    }

    public void startDrawing() {
        isDrawing = true;
        drawThread = new Thread(this);
        drawThread.setPriority(Thread.MAX_PRIORITY);
        if (drawThread.getState() == Thread.State.NEW) {
            drawThread.start();
        }
    }
}

