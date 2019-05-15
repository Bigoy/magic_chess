package com.tssss.bysj.game.core;

import android.os.Handler;

public class CountDownTimer implements Runnable {
    private int time;
    private int countTime;
    private ICountTime iCountTime;
    private Handler handler;

    private boolean isRun;


    public CountDownTimer(int time, ICountTime iCountTime) {
        handler = new Handler();
        this.time = time;
        this.countTime = time;
        this.iCountTime = iCountTime;
    }

    @Override
    public void run() {
        if (isRun) {
            if (iCountTime != null) {
                iCountTime.onTicker(countTime);
            }
            if (countTime == 0) {
                cancelTimer();
                if (iCountTime != null) {
                    iCountTime.onTimerFinish();

                }
            } else {
                countTime = time--;
                handler.postDelayed(this, 1000);
            }
        }

    }

    public void cancelTimer() {
        isRun = false;
        handler.removeCallbacks(this);
    }

    public void startTimer() {
        isRun = true;
        handler.post(this);
    }

    public void resetTimer() {
        time = 30;
        this.countTime = time;
        if (null != iCountTime) {
            iCountTime.onTicker(countTime);
        }
    }

    public interface ICountTime {
        void onTicker(int time);

        void onTimerFinish();
    }
}
