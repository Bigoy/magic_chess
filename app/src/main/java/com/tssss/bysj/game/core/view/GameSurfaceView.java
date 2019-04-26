package com.tssss.bysj.game.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tssss.bysj.game.Chessboard;
import com.tssss.bysj.game.core.AnchorManager;
import com.tssss.bysj.game.core.ChessmanManager;
import com.tssss.bysj.game.core.GameManager;
import com.tssss.bysj.game.core.GameRoleManager;
import com.tssss.bysj.game.core.GameUtil;
import com.tssss.bysj.game.core.Rule;
import com.tssss.bysj.game.core.Umpire;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    public static boolean isDrawing = false;

    private Canvas gameCanvas;
    private SurfaceHolder gameHolder;
    private boolean canTouch = true;

    private AnchorManager am;
    private ChessmanManager cm;
    private Chessboard chessboard;
    private GameRoleManager pm;
    private Umpire umpire;
    private GameUtil gameUtil;
    private GameManager gm;

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
        int measuredSurfaceSize = MeasureSpec.getSize(widthMeasureSpec);

        gameUtil.setSurfaceSize(measuredSurfaceSize);
        am.createAnchors();

        setMeasuredDimension(measuredSurfaceSize, measuredSurfaceSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        // 开启绘制线程。
        Thread drawThread = new Thread(this);
        drawThread.setPriority(Thread.MAX_PRIORITY);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
        Log.wtf(getClass().getSimpleName(), "surfaceView destroyed");
    }

    @Override
    public void run() {
        gm.prepare(am, cm, pm);
        // 绘制。
        while (isDrawing) {
            try {
                draw();
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        gameHolder = getHolder();
        gameHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        // surfaceView背景透明。
        setZOrderOnTop(true);
        gameHolder.setFormat(PixelFormat.TRANSLUCENT);

        gameUtil = GameUtil.getGameUtil();
        am = AnchorManager.getAnchorManager();
        cm = ChessmanManager.getChessmanManager();
        pm = GameRoleManager.getGameRoleManager();
        umpire = new Umpire();
        chessboard = new Chessboard();
        gm = new GameManager();

        gameUtil.setContext(getContext());
    }

    /*
    总绘制方法。
     */
    private void draw() {
        try {
            // 获取游戏画布对象。
            gameCanvas = gameHolder.lockCanvas();
            // 重置画布，绘制下一帧。
            clear(gameCanvas);
            chessboard.draw(gameCanvas);
            cm.drawChessmen(gameCanvas);
            if (cm.whoChecked() != null) {
                cm.drawMark(gameCanvas, cm.whoChecked());
            }
//            umpire.umpire();
        } finally {
            if (gameCanvas != null) {
                // 释放画布并绘制内容。
                gameHolder.unlockCanvasAndPost(gameCanvas);
                canTouch = true;
            }
        }
    }

    /*
    Clear canvas.
     */
    private void clear(Canvas gameCanvas) {
        Paint paint = new Paint();
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        paint.setXfermode(xfermode);
        gameCanvas.drawPaint(paint);
    }

    /*
    tackle touch event.
     */
    public void doTouch(MotionEvent event) {
        ChessmanManager cm = ChessmanManager.getChessmanManager();
        AnchorManager am = AnchorManager.getAnchorManager();
        Rule rule = Rule.getRule();
        if (canTouch) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (cm.hasChessmanChecked()) {
                        // 用户选中了棋子，准备移动。
                        if (rule.canMoveChessman(event)) {
                            cm.update(event, cm.whoChecked(), am.identifyAnchor((int) event.getX(), (int) event.getY()));
                            cm.resetChessmenCheckedState();
                        }
                    } else {
                        // 用户准备选中某一个棋子。
                        cm.checkChessman(event);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

