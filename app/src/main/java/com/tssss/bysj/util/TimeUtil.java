package com.tssss.bysj.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 计时类
 *
 * @author tssss
 */
public class TimeUtil {
    private int m;
    private int s;
    private int count;

    public boolean isPlaying = false;//决定计时器是否工作

    public int getM() {
        return this.m;
    }

    public int getS() {
        return this.s;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsPlaying() {
        return this.isPlaying;
    }

    /**
     * 处理分，秒的显示逻辑
     */
    private void showTime() {
        try {
            Thread.sleep(1000);
            Log.wtf("wtf", Thread.currentThread().toString());
            count++;
            if (count == 60) {
                s = 0;
                count = 0;
                if (m >= 60) {
                    /*
                     最多支持一个小时,超过一个小时清零,重新开始计时
                    */
                    m = 0;
                    s = 0;
                } else {
                    m++;
                }
            } else {
                s = count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启计时器
     */
    public void startTimer(final Handler handler) {
        setCount(0);
        setIsPlaying(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time[] = new int[2];
                while (getIsPlaying()) {
                    Message msg = Message.obtain();
                    showTime();
                    time[0] = getM();
                    time[1] = getS();
                    msg.obj = time;
                    handler.sendMessage(msg);
                }

            }
        }).start();
    }

    /**
     * 关掉计时器
     */
    public void stopTimer() {
        setCount(-1);
        setIsPlaying(false);
    }

    public static void pause(long millisecond) {
        long startTime = System.currentTimeMillis();

        do {
            Log.wtf("TimeUtil", "pausing");
        } while (System.currentTimeMillis() - startTime == millisecond);
    }
}
