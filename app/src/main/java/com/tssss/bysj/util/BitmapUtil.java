package com.tssss.bysj.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import java.nio.ByteBuffer;

public class BitmapUtil {
    /**
     * 对位图进行高斯模糊处理
     *
     * @return Bitmap
     */
    public Bitmap blurToBitmap(Context context, Bitmap bitmap, float radius) {
        // 复制图片
        Bitmap copyBitmap = bitmap.copy(bitmap.getConfig(), true);
        // 缩小图片
        copyBitmap = narrowBitmap(copyBitmap);
        // 高斯模糊处理
        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, copyBitmap);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur sib = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        sib.setRadius(radius);
        sib.setInput(input);
        sib.forEach(output);
        output.copyTo(copyBitmap);
        // 放大（还原）图片
        copyBitmap = enlargeBitmap(copyBitmap);
        // 释放资源
        rs.destroy();
        return copyBitmap;
    }

    /**
     * 高斯模糊
     *
     * @return BitmapDrawable
     */
    public BitmapDrawable blurToBitmapDrawable(Context context, Bitmap bitmap, float radius) {
        // 复制图片
        Bitmap copyBitmap = bitmap.copy(bitmap.getConfig(), true);
        // 缩小图片
        copyBitmap = narrowBitmap(copyBitmap);
        // 高斯模糊处理
        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, copyBitmap);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur sib = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        sib.setRadius(radius);
        sib.setInput(input);
        sib.forEach(output);
        output.copyTo(copyBitmap);
        // 放大（还原）图片
        copyBitmap = enlargeBitmap(copyBitmap);
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), copyBitmap);
        // 释放资源
        rs.destroy();
        return drawable;
    }

    private Bitmap narrowBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap enlargeBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(4.0f, 4.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * Bitmap convert to byte array.
     */
    public static byte[] toBytes(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();

        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bitmap.copyPixelsToBuffer(buf);

        return buf.array();
    }

    /**
     * Byte array convert to Bitmap.
     */
    public static Bitmap toBitmap(byte[] bytes) {
        Bitmap src = null;
        Bitmap stitchBmp = null;

        if (bytes != null && bytes.length > 0)
            src = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        else
            Log.wtf("bytes.length()", "" + bytes.length);

        if (src != null) {
            stitchBmp = Bitmap.createBitmap(src);
            stitchBmp.copyPixelsFromBuffer(ByteBuffer.wrap(bytes));
        } else
            Log.wtf("BitmapUtil", "src is null");

        return stitchBmp;
    }
}
