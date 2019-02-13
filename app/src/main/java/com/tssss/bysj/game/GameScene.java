package com.tssss.bysj.game;

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
import android.view.View;

public class GameScene extends SurfaceView implements SurfaceHolder.Callback, Runnable,
        View.OnTouchListener {

    public static boolean isDrawing;
    public static boolean canTouch;
    private boolean threadStarted;

    private Canvas gameCanvas;
    private SurfaceHolder gameHolder;
    private GameProgress mGameProgress;
    private Thread mMainDrawingThread;


    public GameScene(Context context) {
        super(context);
        init();
    }

    public GameScene(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameScene(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredSurfaceSize = MeasureSpec.getSize(widthMeasureSpec);

        GameHelper.getGameHelper().setSurfaceSize(measuredSurfaceSize);
        setMeasuredDimension(measuredSurfaceSize, measuredSurfaceSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.wtf(getClass().getSimpleName(), "surface created");

        startup();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.wtf(getClass().getSimpleName(), "surface changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.wtf(getClass().getSimpleName(), "surface destroyed");
        isDrawing = false;

        /*if (threadStarted) {
            try {
                mMainDrawingThread.join();
                threadStarted = false;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void run() {
        mGameProgress.initialize();
        mGameProgress.prepare();

        while (isDrawing) {
            try {
                draw();
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        gameHolder = getHolder();
        gameHolder.addCallback(this);
        gameHolder.setFormat(PixelFormat.TRANSLUCENT);

        setOnTouchListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);

        mGameProgress = GameProgress.getGameProgress();
        mMainDrawingThread = new Thread(this);
        mMainDrawingThread.setPriority(Thread.MAX_PRIORITY);
    }

    /**
     * Main drawing.
     */
    private void draw() {
        try {
            gameCanvas = gameHolder.lockCanvas();

            if (gameCanvas != null) {
                clearGameCanvas(gameCanvas);

                mGameProgress.setGameCanvas(gameCanvas);
                mGameProgress.run();
            }

        } finally {
            if (gameCanvas != null) {
                gameHolder.unlockCanvasAndPost(gameCanvas);
            }
        }
    }

    /**
     * Clear canvas.
     */
    private void clearGameCanvas(Canvas gameCanvas) {
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

        Paint paint = new Paint();
        paint.setXfermode(xfermode);

        gameCanvas.drawPaint(paint);
    }

    /**
     * Tackle touch event.
     */
    public void doTouch(MotionEvent event) {
        mGameProgress.doTouch(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (canTouch)
            doTouch(event);

        return true;
    }

    /**
     * Start main drawing thread.
     */
    private void startup() {
        isDrawing = true;

        Log.wtf(getClass().getSimpleName(), mMainDrawingThread.getState().name());

        if (!threadStarted) {
            mMainDrawingThread.start();
            threadStarted = true;
        }
    }
}

