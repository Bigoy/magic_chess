package com.tssss.bysj.componet;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class GTextView extends AppCompatTextView {
    private final Context mContext;

    public GTextView(Context context) {
        super(context);
        mContext = context;
        setFont();
    }

    public GTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFont();
    }

    private void setFont() {
        AssetManager am = mContext.getAssets();
        Typeface tf = Typeface.createFromAsset(am, "fonts/app.ttf");
        setTypeface(tf);
    }
}
