package com.tssss.bysj.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

public class CircleLoadView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private boolean isLoading,
            threadStarted,
            isFirstRound;
    private int mWidth,
            mHeight,
            mSetX;

    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private Thread mLoadThread;
    private Circle mCircleOne,
            mCircleTwo,
            mCircleThree;


    public CircleLoadView(Context context) {
        super(context);
        init();
    }

    public CircleLoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mSetX = mWidth / 4 * 3;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isLoading = false;
    }

    @Override
    public void run() {


        while (isLoading) {
            try {
                mCanvas = mHolder.lockCanvas();
                draw();

            } finally {
                if (mCanvas != null) {
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }

    private void draw() {
        if (mCanvas != null) {
            if (isFirstRound)
                moveToLeft();
            else
                moveToRight();
        }
    }

    public void startAnimation() {
        isLoading = true;

        if (!threadStarted) {
            mLoadThread.start();
            threadStarted = true;
        }
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setZOrderOnTop(true);

        mLoadThread = new Thread(this);
        mCircleOne = new Circle();
        mCircleTwo = new Circle();
        mCircleThree = new Circle();

        mCircleOne.setCoordinateX(mWidth / 4);
        mCircleOne.setCoordinateY(mHeight / 2);
        mCircleTwo.setCoordinateX(mWidth / 4 * 2);
        mCircleTwo.setCoordinateY(mHeight / 2);
        mCircleThree.setCoordinateX(mWidth / 4 * 3);
        mCircleThree.setCoordinateY(mHeight / 2);

        isFirstRound = true;
    }

    public void stopAnimation() {
        isLoading = false;

        try {
            mLoadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveToLeft() {
        mSetX++;
        mCircleThree.setCoordinateX(mSetX);
        mCircleThree.draw(mCanvas);

        isFirstRound = false;
    }

    private void moveToRight() {
    }

    class Circle {
        private int mRadius = 10,
                mCoordinateX,
                mCoordinateY;

        private Paint mPaint;


        Circle() {
            initPaint();
        }

        Circle(int radius,
               int coordinateX,
               int coordinateY) {

            initPaint();

            this.mRadius = radius;
            this.mCoordinateX = coordinateX;
            this.mCoordinateY = coordinateY;
        }

        public int getRadius() {
            return mRadius;
        }

        public void setRadius(int mRadius) {
            this.mRadius = mRadius;
        }

        public int getCoordinateX() {
            return mCoordinateX;
        }

        public void setCoordinateX(int mCoordinateX) {
            this.mCoordinateX = mCoordinateX;
        }

        public int getCoordinateY() {
            return mCoordinateY;
        }

        public void setCoordinateY(int mCoordinateY) {
            this.mCoordinateY = mCoordinateY;
        }

        void draw(Canvas canvas) {
            canvas.drawCircle(mCoordinateX, mCoordinateY, mRadius, mPaint);
        }

        private void initPaint() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStyle(Paint.Style.FILL);
        }

    }
}
