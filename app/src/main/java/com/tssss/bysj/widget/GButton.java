package com.tssss.bysj.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class GButton extends AppCompatButton {
    private Context mContext;

    public GButton(Context context) {
        super(context);

        mContext = context;
        setFont();
    }

    public GButton(Context context, AttributeSet attrs) {
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
