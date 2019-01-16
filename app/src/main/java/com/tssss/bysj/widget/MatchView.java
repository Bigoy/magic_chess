package com.tssss.bysj.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tssss.bysj.R;

public class MatchView extends View {
    private int radius = 0;
    private int selfSize;
    private Paint paint;

    public MatchView(Context context) {
        super(context);
        init();
    }

    public MatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
        selfSize = measuredWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(selfSize / 2, selfSize / 2, radius, paint);
    }

    private void init() {
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.brown_ffd4c49a));
    }
}
