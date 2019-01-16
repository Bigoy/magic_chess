package com.tssss.bysj.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tssss.bysj.util.SystemUtil;

/**
 * Used on MainActivity, it will start falling animation when activity is opened,
 * and attributes LayoutWidth / LayoutHeight should be MATCH_PARENT.
 *
 * @author Tssss
 * @date 2019-01-16
 */
public class GWelcomeTextView extends GTextView {
    private Context mContext;
    private Paint mPaint;
    private int mSpace = 50;

    private String[] mText = {
            "抵制不良游戏，拒绝盗版游戏",
            "注意自我保护，谨防受骗上当",
            "适度游戏益脑，沉迷游戏伤身"
    };

    public GWelcomeTextView(Context context) {
        super(context);
        mContext = context;
        mPaint = new Paint();
    }

    public GWelcomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
       /* int endY = 0;// Get size of text
        float textSize = getTextSize();
        // Draw text from top to bottom
        // Calculate coordination x and y
        int endX = 0;*/

        for (int i = 0; i < SystemUtil.getScreenSize(mContext)[1]; i++) {
            canvas.drawText(mText[0], i, 0, mPaint);
        }
    }
}
