package com.tssss.bysj.componet;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


/**
 * 进度条
 * 动态显示时间进度在游戏界面
 */
public class GProgress extends View {
    public GProgress(Context context) {
        super(context);
    }

    public GProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(0,0,200,0,new Paint());
    }
}
