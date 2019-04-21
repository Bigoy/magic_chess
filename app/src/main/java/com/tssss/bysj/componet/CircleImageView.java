package com.tssss.bysj.componet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {
    private int width,
            height,
            radius;

    private Paint paint;
    private Matrix matrix;


    public CircleImageView(Context context) {
        super(context);

        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width, height) / 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getForeground();

        if (drawable == null) {
            super.onDraw(canvas);
            return;
        }

        if (drawable instanceof BitmapDrawable) {
            paint.setShader(getShader((BitmapDrawable) drawable));
            canvas.drawCircle((float) width / 2, (float) height / 2, (float) radius, paint);
            return;
        }

        super.onDraw(canvas);
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        matrix = new Matrix();
    }

    protected BitmapShader getShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(width / bitmap.getWidth(), height / bitmap.getHeight());
        matrix.setScale(scale, scale);
        shader.setLocalMatrix(matrix);

        return shader;
    }
}
