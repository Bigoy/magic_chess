package com.tssss.bysj.game;

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
import android.view.View;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable,
        View.OnTouchListener {
    public static boolean isDrawing;
    public static boolean canTouch;

    private Canvas gameCanvas;
    private SurfaceHolder gameHolder;
    private int mSurfaceSize;

    private GameProgress mGameProgress;

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

        mSurfaceSize = measuredSurfaceSize;
        GameHelper.getGameHelper().setSurfaceSize(mSurfaceSize);
        setMeasuredDimension(measuredSurfaceSize, measuredSurfaceSize);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;

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
    }

    @Override
    public void run() {
        mGameProgress.prepare();

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
        gameHolder.setFormat(PixelFormat.TRANSLUCENT);

        setOnTouchListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);

        mGameProgress = GameProgress.getGameProgress();

        GameHelper.getGameHelper().setContext(getContext());
    }

    /**
     * Main drawing.
     */
    private void draw() {
        try {
            gameCanvas = gameHolder.lockCanvas();
            clearGameCanvas(gameCanvas);

            mGameProgress.setGameCanvas(gameCanvas);
            mGameProgress.start();

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
}

