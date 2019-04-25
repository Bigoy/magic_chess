package com.tssss.bysj.game.im;

import com.tssss.bysj.base.BaseActivity;

import cn.jpush.im.android.api.JMessageClient;

public class JMessageManager {
    public static void registerEvent(BaseActivity context) {
        JMessageClient.registerEventReceiver(context);
    }

    public static void unregisterEvent(BaseActivity context) {
        JMessageClient.unRegisterEventReceiver(context);
    }
}
